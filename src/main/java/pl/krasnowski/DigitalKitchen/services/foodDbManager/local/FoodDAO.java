package pl.krasnowski.DigitalKitchen.services.foodDbManager.local;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;


/**
 * Access to food data is available only by DatabaseManager
 */
@Repository
interface FoodDAO extends JpaRepository<Food, Long> {
}
