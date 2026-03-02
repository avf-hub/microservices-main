

package store.laptop.template.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StubDataRepository extends JpaRepository<StubData, Long> {
}
