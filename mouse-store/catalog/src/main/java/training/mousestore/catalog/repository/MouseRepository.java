package training.mousestore.catalog.repository;

import training.mousestore.catalog.model.Mouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MouseRepository extends JpaRepository<Mouse, Long> {

}
