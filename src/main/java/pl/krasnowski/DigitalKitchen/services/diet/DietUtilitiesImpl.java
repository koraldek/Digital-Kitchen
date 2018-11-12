package pl.krasnowski.DigitalKitchen.services.diet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietType;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.food.Consumable;
import pl.krasnowski.DigitalKitchen.model.domain.food.Dish;
import pl.krasnowski.DigitalKitchen.model.domain.food.Nutrient;
import pl.krasnowski.DigitalKitchen.model.domain.user.Intolerance;
import pl.krasnowski.DigitalKitchen.model.domain.user.Sex;
import pl.krasnowski.DigitalKitchen.model.domain.user.Silhouette;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.util.List;

@Service
public class DietUtilitiesImpl implements DietUtilities {

    @Autowired
    private User user;


    /**
     * Implementation of Basal Metabolic Rate (BMR) calculator, Harris-Benedict's method
     *
     * @return calculated diet, without planned meals.
     */
    @Override
    public Diet computeDiet(DietType dietType) {
        Diet calculatedDiet = new Diet();
        calculatedDiet.setDietType(dietType);
        double BMR, CPM, dailyKcal;

        CPM = 1.2 + 0.01 * 50; // 50 - percentages of user physical activity during week

        if (user.getSex().equals(Sex.female)) {
            BMR = 655
                    + 9.6 * user.getWeight()
                    + 1.8 * user.getHeight()
                    - 4.7 * user.getAge();
        } else if (user.getSex().equals(Sex.male)) {
            BMR = 66
                    + 13.7 * user.getWeight()
                    + 5 * user.getHeight()
                    - 6.8 * user.getAge();
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
        for (Intolerance in : user.getIntolerances()) {
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
        double heightMeters = height*0.01;
        return (weight / heightMeters) /heightMeters;
    }

}
