package pl.krasnowski.DigitalKitchen.services.diet;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.food.Consumable;
import pl.krasnowski.DigitalKitchen.model.domain.food.Dish;
import pl.krasnowski.DigitalKitchen.model.domain.food.FoodWrapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface FoodLogger {

    /**
     * Log food to user's diary and add food to existing meal.
     *
     * @param food     consumed food
     * @param date     date and time of meal
     * @param mealName meal to bind food with
     */
    void logFood(FoodWrapper food, LocalDateTime date, String mealName);

    /**
     * Logs food to existing meal or create new one.
     *
     * @param dishes   ingredients of meal
     * @param date     date and time of meal
     * @param mealName meal to bind food with
     */
    void logMeal(List<FoodWrapper> dishes, LocalDateTime date, String mealName);

    void updateMeal(String oldMealName, Meal updatedMeal);

    void updateDish(Meal meal, Dish dish);

    /**
     * @param food     to remove
     * @param date     date of diary day
     * @param mealName where particular food occurs
     */
    void removeFromLog(FoodWrapper food, LocalDate date, String mealName);

    /**
     * Removes meal from log.
     */
    void removeFromLog(LocalDate date, String mealName);
}