package pl.krasnowski.DigitalKitchen.services.kitchen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.food.Consumable;
import pl.krasnowski.DigitalKitchen.model.domain.food.Dish;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.Kitchen;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.ShoppingListItem;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.model.events.NewShoppingListItem;
import pl.krasnowski.DigitalKitchen.model.events.ShoppingListItemChangedStatus;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import java.util.Set;

@Service
public class KitchenServiceImpl implements KitchenService {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private UserService userService;

    @Override
    public Set<Consumable> getStock() {
        return null;
    }

    @Override
    public Consumable findInStock(Consumable consumable) {
        return null;
    }

    @Override
    public void addItemToStock(Consumable consumable) {

    }

    @Override
    public void removeItemFromStock(Consumable consumable) {
    }


    @Override
    public Kitchen getUserKitchen() {
        return userService.getCurrentUser().getKitchen();
    }

    @Override
    public Kitchen getUserKitchen(User user) {
        return userService.findByUsername(user.getUsername()).getKitchen();
    }

    @Override
    public void changeKitchen(Kitchen kitchen, User user) {

    }


    @Override
    public Set<ShoppingListItem> getShoppingList() {
        return null;
    }

    @Override
    public void addItemToShoppingList(ShoppingListItem item) {
        Kitchen kitchen = userService.getCurrentUser().getKitchen();
        kitchen.addItemToShoppingList(item);
        publisher.publishEvent(new NewShoppingListItem(item, kitchen.getUsers(), item.getAdditionalMessage()));
    }

    @Override
    public void removeItemFromShoppingList(ShoppingListItem item) {
        Kitchen kitchen = userService.getCurrentUser().getKitchen();
        kitchen.removeItemFromShoppingList(item);
        publisher.publishEvent(new ShoppingListItemChangedStatus(item, kitchen.getUsers(), item.getAdditionalMessage()));
    }

    @Override
    public void addOrder(Dish dish) {

    }

    @Override
    public Dish getOrder(long id) {
        return null;
    }

    @Override
    public Set<Dish> getOrders() {
        return null;
    }

    @Override
    public void removeOrder(Dish dish) {

    }
}
