package pl.krasnowski.DigitalKitchen.model.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.Kitchen;

@Repository
public interface KitchenDAO extends JpaRepository<Kitchen, Long> {
}
