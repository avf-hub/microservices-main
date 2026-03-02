

package store.laptop.inventory.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.laptop.inventory.domain.exception.InventoryOperationException;
import store.laptop.inventory.domain.model.InventoryItem;
import store.laptop.inventory.domain.model.InventoryItemRepository;
import store.laptop.inventory.domain.model.ItemReservationRecord;
import store.laptop.inventory.domain.model.ItemReservationRecordRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class InventoryKeeperService implements InventoryKeeper {
	private final InventoryItemRepository itemRepository;
	private final ItemReservationRecordRepository reservationLogRepository;

	@Autowired
	public InventoryKeeperService(InventoryItemRepository itemRepository,
	                              ItemReservationRecordRepository reservationLogRepository) {
		this.itemRepository = itemRepository;
		this.reservationLogRepository = reservationLogRepository;
	}

	@Transactional(readOnly = true)
	public List<InventoryItem> getItemAvailabilityByProductId(long productId) {
		return itemRepository.findByProductId(productId);
	}

	public ItemReservationRecord pickItem(long itemId, long requiredQty) {
		if (requiredQty <= 0) {
			throw new InventoryOperationException(
				"The required item qty for picking can not be less or equal to zero!",
				itemId
			);
		}
		try {
			InventoryItem item = itemRepository.findById(itemId).orElseThrow();
			if (item.getAvailableQty() < requiredQty) {
				throw new InventoryOperationException("Available item qty with id: " + itemId +
					" is not enough for picking.", itemId);
			}
			// decrement item available qty
			item.setAvailableQty(item.getAvailableQty() - requiredQty);
			itemRepository.save(item);
			return reservationLogRepository.save(ItemReservationRecord.builder()
				.inventoryItemId(item.getId())
				.productId(item.getProductId())
				.unitCost(item.getUnitCost())
				.requiredQty(requiredQty)
				.build());
		} catch (NoSuchElementException ex) {
			throw new InventoryOperationException("Can not pick inventory item with id: " + itemId + ".",
				itemId, ex);
		}
	}
}
