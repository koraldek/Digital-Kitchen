package pl.krasnowski.DigitalKitchen.services.diet;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Service
public interface DietManager {
    String defaultDietSuffix = "'s personal diet"; //TODO: internationalize

    void addNewDiet(Diet diet);

    Diet getByID(long id);

    List<Diet> getDiets();

    void updateDiet(Diet diet);

    void removeDiet(Diet diet);


    void updateCurrentDiet(Diet diet);

    Diet getCurrentDiet();

    void addFoodToDiet(Meal food);


    List<Diet> initializeDiets(User user);

    Diet initializeDiet(User user);

    Set<DietDay> initializeDietDays();

    DietDay initializeDietDay();

    Meal initializeMeal();

    DietDay getCurrentDietDay();

    DietDay getDietDay(LocalDate date);


    int getKcalGained(LocalDate date);

    int getBurnedKcal(LocalDate date);
}