package pl.krasnowski.DigitalKitchen.model.DAO.unused;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;

@Repository
public interface DietDayDAO extends JpaRepository<Diet, Long> {
}
