package pl.krasnowski.DigitalKitchen.model.DAO;

import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;

@Repository
public interface CustomizedFoodDAO {
    Food findByForeignID(String dbName, String foodID);
}