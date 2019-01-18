package pl.krasnowski.DigitalKitchen.services.diet;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietType;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.food.Consumable;
import pl.krasnowski.DigitalKitchen.model.domain.food.Dish;
import pl.krasnowski.DigitalKitchen.model.domain.food.FoodWrapper;
import pl.krasnowski.DigitalKitchen.model.domain.food.Nutrient;
import pl.krasnowski.DigitalKitchen.model.domain.user.Intolerance;
import pl.krasnowski.DigitalKitchen.model.domain.user.Sex;
import pl.krasnowski.DigitalKitchen.model.domain.user.Silhouette;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface DietUtilities {

    /**
     * Method for normal users.
     *
     * @return diet for desired silhouette for current user.
     */
    Diet computeDiet(DietType dietType, int physicalActivity, Sex sex, int height, int weight, int age);

    /**
     * Method used by dietetics to generate diet.
     *
     * @param user data to compute diet
     * @param desiredSilhouette to achieve with this diet
     * @return diet for desired silhouette.
     */
    Diet computeDiet(User user, Silhouette desiredSilhouette);

    /**
     * Calculate BMI - Body Mass Index
     *
     * @param weight in metres
     * @param height in kilograms
     * @return calculated value of BMI
     */
    double calculateBMI(double weight, double height);

    boolean isAllergicTo(Intolerance intolerance);

    /**
     * Used to propose meal using kitchen's stock items.
     *
     * @return proposed meal
     */
    Meal proposeMeal();

    /**
     * Used to propose meal which tries to supplement missing nutrients.
     *
     * @return proposed meal
     */
    Meal proposeMeal(List<Nutrient> missingNutrientList);

    /**
     * Finds dishes which have ingredients from argument.
     *
     * @param partsOfDish - list of ingredients that should be in dish
     * @return list of dishes
     */
    List<Dish> proposeDish(List<Consumable> partsOfDish);

    /**
     * @return meal which contains closest nutrients values, but don't have user's intolerances.
     */
    Meal proposeSubstitute(Consumable foodToSubstitute);

    FoodWrapper toFoodWrapper(String servingUnit, String servingSize, Consumable food);

    /**
     * Converts strings of date and time to LocalDateTime object.
     *
     * @param _date date in format day-month-year dd-mm-yyyy
     * @param _time hour and minute in format hour-minute hh-mm
     * @return object representing time and date
     */
    LocalDateTime toDateTime(String _date, String _time);
}