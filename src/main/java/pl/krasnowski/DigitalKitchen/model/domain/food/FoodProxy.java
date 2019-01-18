package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Proxy of Food and other descendants of Consumable. <br>
 * {@link FoodProxy#foodType} determines concrete implementation of Consumable. <br>
 * {@link FoodProxy#foodID} and {@link FoodProxy#dbName} intercepts to real food object.
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodProxy {
    private String foodID, name, dbName, photo, foodType;
    private Origin origin;
}