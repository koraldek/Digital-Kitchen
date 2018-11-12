package pl.krasnowski.DigitalKitchen.services.kitchen;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.food.Consumable;
import pl.krasnowski.DigitalKitchen.model.domain.food.Dish;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.Kitchen;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.ShoppingListItem;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.util.Set;

@Service
public interface KitchenService {

    Set<Consumable> getStock();

    Consumable findInStock(Consumable consumable);

    /**
     * @param consumable Product or Dish
     */
    void addItemToStock(Consumable consumable);

    void removeItemFromStock(Consumable consumable);


    Kitchen getUserKitchen();

    Kitchen getUserKitchen(User user);


    void changeKitchen(Kitchen kitchen, User user);


    Set<ShoppingListItem> getShoppingList();

    void addItemToShoppingList(ShoppingListItem item);

    void removeItemFromShoppingList(ShoppingListItem item);

    void addOrder(Dish dish);

    Dish getOrder(long id);

    Set<Dish> getOrders();

    void removeOrder(Dish dish);


}