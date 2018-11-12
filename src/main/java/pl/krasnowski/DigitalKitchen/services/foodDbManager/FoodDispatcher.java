package pl.krasnowski.DigitalKitchen.services.foodDbManager;

import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.food.FoodProxy;

import java.util.ArrayList;
import java.util.Map;

@Component
public interface FoodDispatcher {

    ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap);

    ArrayList<Food> getFoodList(String keyword, Map<String, String> paramMap);

    Food getFoodByID(String foodID);

    Food getFoodByBarcode(int barcode);

    String getDbName();

}