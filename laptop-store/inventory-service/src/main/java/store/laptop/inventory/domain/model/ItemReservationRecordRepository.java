

package store.laptop.inventory.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemReservationRecordRepository extends JpaRepository<ItemReservationRecord, Long> {
}
