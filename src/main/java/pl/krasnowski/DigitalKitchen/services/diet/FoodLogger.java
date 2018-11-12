package pl.krasnowski.DigitalKitchen.services.diet;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.food.Consumable;
import pl.krasnowski.DigitalKitchen.model.domain.food.Dish;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.sql.Timestamp;

@Service
public interface FoodLogger {

    /**
     * Log food to current user diary. Creates new meal.
     *
     * @param food consumed food
     * @param date date of dietDay
     */
    void logFood(Consumable food, Timestamp date, User user);

    /**
     * Log food to user's diary and add food to existing meal.
     *
     * @param food consumed food
     * @param meal meal to bind food with
     */
    void logFood(Consumable food, DietDay dietDay, Meal meal);

    /**
     * Log meal to user dairy.
     *
     * @param meal created meal
     */
    void logMeal(Meal meal);


    void updateMeal(Meal oldMeal, Meal updatedMeal);

    void updateDish(Meal meal, Dish dish);


    /**
     * @param food to remove
     * @param meal where particular food occurs
     */
    void removeFromLog(Consumable food, Meal meal);


    /**
     * Removes whole meal from log.
     */
    void removeFromLog(Meal meal);
}
