

package store.laptop.inventory.controller.dto.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import store.laptop.inventory.domain.model.ItemReservationRecord;
import store.laptop.inventory.shared.model.web.dto.ReservationRecordInfo;

@Mapper(componentModel = "spring")
public interface ReservationRecordInfoMapper {
	@Mapping(target = "recordId", source = "id")
	ReservationRecordInfo map(ItemReservationRecord reservationRecord);
}
