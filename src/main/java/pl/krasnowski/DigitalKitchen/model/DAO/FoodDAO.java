package pl.krasnowski.DigitalKitchen.model.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;

@Repository
public interface FoodDAO extends JpaRepository<Food, Long>, CustomizedFoodDAO {
}
