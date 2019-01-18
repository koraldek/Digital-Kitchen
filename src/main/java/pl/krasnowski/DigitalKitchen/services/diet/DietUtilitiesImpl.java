package pl.krasnowski.DigitalKitchen.services.diet;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietType;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.food.*;
import pl.krasnowski.DigitalKitchen.model.domain.user.Intolerance;
import pl.krasnowski.DigitalKitchen.model.domain.user.Sex;
import pl.krasnowski.DigitalKitchen.model.domain.user.Silhouette;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class DietUtilitiesImpl implements DietUtilities {


    @Autowired
    UserService userService;

    /**
     * Implementation of Basal Metabolic Rate (BMR) calculator, Harris-Benedict's method
     *
     * @param physicalActivity percentages of user physical activity during week
     * @return calculated diet, without planned meals.
     */
    @Override
    public Diet computeDiet(DietType dietType, int physicalActivity, Sex sex, int height, int weight, int age) {
        Diet calculatedDiet = new Diet();
        calculatedDiet.setDietType(dietType);
        double BMR, CPM, dailyKcal;

        CPM = 1.2 + 0.01 * physicalActivity;

        if (sex.equals(Sex.FEMALE)) {
            BMR = 655
                    + 9.6 * weight
                    + 1.8 * height
                    - 4.7 * age;
        } else if (sex.equals(Sex.MALE)) {
            BMR = 66
                    + 13.7 * weight
                    + 5 * height
                    - 6.8 * age;
        } else
            throw new IllegalArgumentException("Unknown sex.");
        dailyKcal = BMR * CPM;

        calculatedDiet.setKCal((int) Math.round(dailyKcal));
        calculatedDiet.setCarbohydrates((int) Math.round(dietType.getCarbohydrates() * dailyKcal));
        calculatedDiet.setProteins((int) Math.round(dietType.getProteins() * dailyKcal));
        calculatedDiet.setFat((int) Math.round(dietType.getFat() * dailyKcal));
        return calculatedDiet;
    }

    @Override
    public Diet computeDiet(User user, Silhouette desiredSilhouette) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAllergicTo(Intolerance intolerance) {
        for (Intolerance in : userService.getCurrentUser().getIntolerances()) {
            if (in.equals(intolerance))
                return true;
        }
        return false;
    }

    @Override
    public Meal proposeMeal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Meal proposeMeal(List<Nutrient> missingNutrientList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Dish> proposeDish(List<Consumable> partsOfDish) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Meal proposeSubstitute(Consumable foodToSubstitute) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double calculateBMI(double weight, double height) {
        double heightMeters = height * 0.01;
        return (weight / heightMeters) / heightMeters;
    }

    @Override
    public FoodWrapper toFoodWrapper(String servingUnit, String servingSize, Consumable food) {
        FoodWrapper wrapper = new FoodWrapper();

        wrapper.setUnit(Unit.getEnum(servingUnit));
        wrapper.setQuantity(Double.parseDouble(servingSize));
        Product product = new Product();
        if (food instanceof Food) {
            product.setBase((Food) food);
            wrapper.setFood(product);
        } else {
            //TODO: implement custom Product and Dish creation
            log.warn("Wrapping non Food class object.");
            wrapper.setFood(food);
        }
        return wrapper;
    }

    @Override
    public LocalDateTime toDateTime(String _date, String _time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm");
        return LocalDateTime.parse(_date + " " + _time, formatter);
    }
}