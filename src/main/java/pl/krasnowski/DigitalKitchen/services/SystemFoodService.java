package pl.krasnowski.DigitalKitchen.services;


import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.food.*;

@Service
public interface SystemFoodService {

    void updateDishExpirationTime(Dish dish);

    void updateProductExpirationTime(Product product);


    void specifyProduct(Food food, Object productData);

    void specifyDish(Recipe food, Object productData);

    void addNameToDictionary(String name, String language, Consumable food);

}
