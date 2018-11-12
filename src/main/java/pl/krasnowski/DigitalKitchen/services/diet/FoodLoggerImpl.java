package pl.krasnowski.DigitalKitchen.services.diet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.food.Consumable;
import pl.krasnowski.DigitalKitchen.model.domain.food.Dish;
import pl.krasnowski.DigitalKitchen.model.domain.food.Product;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.user.UserProfileService;

import java.sql.Timestamp;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class FoodLoggerImpl implements FoodLogger {

    @Autowired
    DietManager dietManager;

    @Autowired
    UserProfileService userProfileService;


    @Override
    public void logFood(Consumable food, Timestamp date, User user) {
        if (!(food instanceof Product || food instanceof Dish)) // if it's not instance of real object
            throw new IllegalArgumentException("Input object of class: " + food.getClass().getCanonicalName()
                    + " is not instance of Product or Dish.");

        Meal newMeal = dietManager.initializeMeal();
        newMeal.setMealTime(date);
        newMeal.addFood(food);
        dietManager.getCurrentDietDay().addMeal(newMeal);
        log.debug("Successfully logged food:{}", food.getName());
    }

    @Override
    public void logFood(Consumable food, DietDay dietDay, Meal meal) {
        for (Meal m : dietDay.getMeals())
            if (m.equals(meal)) {
                m.addFood(food);
                return;
            }
        throw new NoSuchElementException("Couldn't find meal.");
    }

    @Override
    public void logMeal(Meal meal) {
        userProfileService.getCurrentHistoryDay().addMeal(meal);
    }

    @Override
    public void removeFromLog(Consumable food, Meal meal) {

    }

    @Override
    public void updateMeal(Meal oldMeal, Meal updatedMeal) {
        BeanUtils.copyProperties(updatedMeal, oldMeal);
    }

    @Override
    public void updateDish(Meal meal, Dish dish) {

    }


    @Override
    public void removeFromLog(Meal meal) {

    }
}
