package pl.krasnowski.DigitalKitchen.services.user;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivity;
import pl.krasnowski.DigitalKitchen.model.domain.user.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public interface UserProfileService {


    boolean changePassword(String oldPass, String newPass);

    /**
     * Adds preferention of diet,dish or even product.
     * It declares items to be on top of searching list and proposing dishes.
     */
    void addFoodPreferention(FoodPreferention foodPreferention);

    /**
     * Removes preferention of diet,dish or even product.
     * It declares items to be on top of searching list and proposing dishes.
     */
    void removeFoodPreferention(FoodPreferention foodPreferention);

    void addIntolerance(Intolerance intolerance);

    void removeIntolerance(Intolerance intolerance);


    void updateNotifications(NotificationSetup notificationSetup);


    HistoryDay getCurrentHistoryDay();

    /**
     * @param date of day to return
     * @return day with user's consumed food diary
     */
    HistoryDay getHistoryDay(LocalDate date);


    /**
     * @return list of user dietDays diary
     */
    Set<HistoryDay> getDietHistory();


    void updateHistoryDay(HistoryDay historyDay);

    void removeHistoryDay(LocalDate date);

    void addHistoryDay(HistoryDay hd);

    List<Meal> getSavedMeals();

    List<Recipe> getSavedRecipes();

    List<PhysicalActivity> getSavedWorkouts();

    UserDTO toDTO(User user);
}
