package pl.krasnowski.DigitalKitchen.services.diet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.food.*;
import pl.krasnowski.DigitalKitchen.services.user.UserProfileService;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class FoodLoggerImpl implements FoodLogger {

    @Autowired
    DietManager dietManager;

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    UserService userService;

    @Autowired
    MealScheduler mealScheduler;


    @Override
    public void logFood(FoodWrapper wrapper, LocalDateTime date, String mealName) {
        if (!(wrapper.getFood() instanceof Product || wrapper.getFood() instanceof Dish))
            throw new IllegalArgumentException("Can't log abstract type of food. Input object of class: " +
                    wrapper.getClass().getCanonicalName() + " is not instance of Product or Dish.");

        Meal meal = mealScheduler.getMealByName(mealName, date.toLocalDate());
        if (meal == null) // when passed meal name other than typical (breakfast, dinner etc.) and custom meal was not found.
            throw new NoSuchElementException("Couldn't find meal for name:" + mealName + " and date:" + date + ".");

        boolean addedFood = false;
        for (FoodWrapper fw : meal.getDishes()) {
            Consumable existingFood = fw.getFood();

            if (existingFood instanceof Product && wrapper.getFood() instanceof Product &&
                    ((Product) existingFood).getBase().getConsumableID().equals(
                            ((Product) wrapper.getFood()).getBase().getConsumableID())) {
                fw.addQuantity(wrapper.getQuantity());
                log.trace("Added food to existing meal, added quantity:{}", wrapper.getQuantity());
                addedFood = true;
                break;
            } else if (existingFood instanceof Dish && wrapper.getFood() instanceof Dish &&
                    ((Dish) existingFood).getRecipe().getConsumableID().equals(
                            ((Dish) wrapper.getFood()).getRecipe().getConsumableID())) {
                fw.addQuantity(wrapper.getQuantity());
                log.trace("Added food to existing meal, added quantity:{}", wrapper.getQuantity());
                addedFood = true;
                break;
            }
        }
        if (!addedFood) {
            meal.addFood(wrapper);
            //    meal.setMealTime(Timestamp.valueOf(date));  //xdd
            meal.setMealTime(new Timestamp(ZonedDateTime.of(date, ZoneId.of("UTC")).toInstant().toEpochMilli()));
            meal.setTakePlace(true);
            log.trace("Added new food ({}) to meal.", wrapper.getFood());
        }

        int kcalGained;
        Optional<NutrientWrapper> caloriesWrapper = wrapper.getFood().getNutrients().stream()
                .filter(nutrientWrapper -> Objects.equals(nutrientWrapper.getNutrient(), Nutrient.ENERGY))
                .findFirst();
        if (!caloriesWrapper.isPresent())
            throw new NoSuchElementException("Can't find energy in nutrients list for item:" + wrapper.getFood().getName());

        if (wrapper.getUnit().equals(Unit.G)) {
            kcalGained = (int) (wrapper.getQuantity() * caloriesWrapper.get().getQuantity());
        } else {
            Integer altUnitWeightOrQuantity;
            if (wrapper.getFood() instanceof Product)
                altUnitWeightOrQuantity = ((Product) wrapper.getFood()).getBase().getServingSizes().get(wrapper.getUnit());
            else
                altUnitWeightOrQuantity = ((Dish) wrapper.getFood()).getRecipe().getServingSizes().get(wrapper.getUnit());
            kcalGained = (int) (wrapper.getQuantity() * caloriesWrapper.get().getQuantity()) * altUnitWeightOrQuantity;
        }
        userProfileService.getHistoryDay(date.toLocalDate()).getFoodAndWorkoutDiary().addKcalGained(kcalGained);

        log.debug("Successfully logged food to diary:{}", wrapper.getFood().getName());
    }

    @Override
    @Deprecated
    public void logMeal(List<FoodWrapper> dishes, LocalDateTime date, String mealName) {
        Meal meal = mealScheduler.getMealByName(mealName, date.toLocalDate());
        int kcalGained = 0;

        if (meal == null)
            throw new NoSuchElementException("Couldn't find meal.");

        for (FoodWrapper inputFoodWrapper : dishes) {
            boolean addedFood = false;

            for (FoodWrapper existingFoodWrapper : meal.getDishes()) {
                Consumable existingFood = existingFoodWrapper.getFood();

                if (existingFood instanceof Product && inputFoodWrapper.getFood() instanceof Product &&
                        ((Product) existingFood).getBase().getConsumableID().equals(
                                ((Product) inputFoodWrapper.getFood()).getBase().getConsumableID())) {
                    existingFoodWrapper.addQuantity(inputFoodWrapper.getQuantity());
                    log.trace("Added food to existing meal, added quantity:{}", inputFoodWrapper.getQuantity());
                    addedFood = true;
                    break;
                } else if (existingFood instanceof Dish && inputFoodWrapper.getFood() instanceof Dish &&
                        ((Dish) existingFood).getRecipe().getConsumableID().equals(
                                ((Dish) inputFoodWrapper.getFood()).getRecipe().getConsumableID())) {
                    existingFoodWrapper.addQuantity(inputFoodWrapper.getQuantity());
                    log.trace("Added food to existing meal, added quantity:{}", inputFoodWrapper.getQuantity());
                    addedFood = true;
                    break;
                }
            }

            if (!addedFood) {
                meal.addFood(inputFoodWrapper);
                log.trace("Added new food ({}) to meal.", inputFoodWrapper.getFood().getName());
            }

            Optional<NutrientWrapper> caloriesWrapper = inputFoodWrapper.getFood().getNutrients().stream()
                    .filter(nutrientWrapper -> Objects.equals(nutrientWrapper.getNutrient(), Nutrient.ENERGY))
                    .findFirst();
            if (!caloriesWrapper.isPresent())
                throw new NoSuchElementException("Can't find energy in nutrients list for item:" + inputFoodWrapper.getFood().getName());

            if (inputFoodWrapper.getUnit().equals(Unit.G)) {
                kcalGained = kcalGained + (int) (inputFoodWrapper.getQuantity() * caloriesWrapper.get().getQuantity());
            } else {
                Integer altUnit = ((Food) inputFoodWrapper.getFood()).getServingSizes().get(inputFoodWrapper.getUnit());
                kcalGained = kcalGained + (int) (inputFoodWrapper.getQuantity() * caloriesWrapper.get().getQuantity()) * altUnit;
            }
        }

        meal.setTakePlace(true);
        //  meal.setMealTime(Timestamp.valueOf(date)); //xdd
        meal.setMealTime(new Timestamp(ZonedDateTime.of(date, ZoneId.of("UTC")).toInstant().toEpochMilli()));
        userProfileService.getHistoryDay(date.toLocalDate()).getFoodAndWorkoutDiary().addKcalGained(kcalGained);

        log.debug("Successfully logged meal to diary:{}. {} elements added to meal.", mealName, dishes.size());
    }



    @Override
    public void updateMeal(String oldMealName, Meal updatedMeal) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateDish(Meal meal, Dish dish) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeFromLog(FoodWrapper food, LocalDate date, String mealName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeFromLog(LocalDate date, String mealName) {
        throw new UnsupportedOperationException();
    }
}
