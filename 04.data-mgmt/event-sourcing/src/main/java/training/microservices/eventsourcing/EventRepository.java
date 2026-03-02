package training.microservices.eventsourcing;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByAccountIdOrderByDate(UUID accountId);

}
