package pl.krasnowski.DigitalKitchen.model.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivity;

@Repository
public interface PhysicalActivityDAO extends JpaRepository<PhysicalActivity, Long> {
}
