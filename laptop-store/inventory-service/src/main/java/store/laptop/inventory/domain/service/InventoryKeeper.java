

package store.laptop.inventory.domain.service;

import store.laptop.inventory.domain.model.InventoryItem;
import store.laptop.inventory.domain.model.ItemReservationRecord;

import java.util.List;

/**
 * This interface defines key operations of the inventory management process.
 */
public interface InventoryKeeper {

	/**
	 * Checks for inventory item availability.
	 *
	 * @param productId {@literal long} of product id
	 * @return {@literal List} of available {@link InventoryItem}
	 */
	List<InventoryItem> getItemAvailabilityByProductId(long productId);

	/**
	 * Picks inventory item.
	 *
	 * @param itemId      {@literal Long} of inventory item id
	 * @param requiredQty {@literal Long}
	 * @return {@link ItemReservationRecord}
	 */
	ItemReservationRecord pickItem(long itemId, long requiredQty);
}
