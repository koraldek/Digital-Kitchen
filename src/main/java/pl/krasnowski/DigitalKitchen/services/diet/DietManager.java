package pl.krasnowski.DigitalKitchen.services.diet;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;

import java.util.List;
import java.util.Set;


@Service
public interface DietManager {
    String defaultDietSuffix = "'s personal diet";

    void addNewDiet(Diet diet);

    Diet getByID(long id);

    List<Diet> getDiets();

    void updateDiet(Diet diet);

    void removeDiet(Diet diet);


    void updateCurrentDiet(Diet diet);

    Diet getCurrentDiet();


    List<Diet> initializeDiets();

    Diet initializeDiet();

    Set<DietDay> initializeDietDays();

    DietDay initializeDietDay();

    Meal initializeMeal();

    DietDay getCurrentDietDay();

}