

package store.laptop.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import store.laptop.inventory.controller.dto.mapping.ProductAvailabilityInfoMapper;
import store.laptop.inventory.controller.dto.mapping.ReservationRecordInfoMapper;
import store.laptop.inventory.domain.model.ItemReservationRecord;
import store.laptop.inventory.domain.service.InventoryKeeper;
import store.laptop.inventory.shared.model.web.dto.ItemReservationRequest;
import store.laptop.inventory.shared.model.web.dto.ProductAvailabilityInfo;
import store.laptop.inventory.shared.model.web.dto.ReservationRecordInfo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
	private final InventoryKeeper inventoryKeeper;
	private final ProductAvailabilityInfoMapper availabilityInfoMapper;
	private final ReservationRecordInfoMapper reservationRecordInfoMapper;

	@Autowired
	public InventoryController(InventoryKeeper inventoryKeeper,
	                           ProductAvailabilityInfoMapper mapper,
	                           ReservationRecordInfoMapper reservationRecordInfoMapper) {
		this.inventoryKeeper = inventoryKeeper;
		this.availabilityInfoMapper = mapper;
		this.reservationRecordInfoMapper = reservationRecordInfoMapper;
	}

	@GetMapping("byProduct/{productId}")
	public List<ProductAvailabilityInfo> getProductAvailabilityInfo(@PathVariable long productId) {
		return inventoryKeeper.getItemAvailabilityByProductId(productId).stream()
			.map(availabilityInfoMapper::map)
			.collect(Collectors.toList());
	}

	@PostMapping
	public ReservationRecordInfo pickInventoryItem(@RequestBody @NonNull ItemReservationRequest request) {
		ItemReservationRecord reservationRecord = inventoryKeeper.pickItem(request.getInventoryItemId(),
			request.getRequiredQty());
		return reservationRecordInfoMapper.map(reservationRecord);
	}
}
