package store.laptop.ordering.shared.model.web.api;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import store.laptop.ordering.shared.model.web.dto.ItemReservationRequest;
import store.laptop.ordering.shared.model.web.dto.ReservationRecordInfo;

public interface InventoryAPI {
    @PostMapping
    ReservationRecordInfo pickInventoryItem(@RequestBody @NonNull ItemReservationRequest request);
}
