package pl.krasnowski.DigitalKitchen.services.diet;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;

import java.util.List;
import java.util.Set;

/**
 * CRUD for Meal object and interface to manage schedules.
 */
@Service
public interface MealScheduler {

    List<Meal> initializeMealSchedule();


    Meal getNearestMeal();


    void addMeal(Meal meal, DietDay dietDay);

    void updateMealSchedule(Set<Meal> meals, DietDay dietDay);


    Set<Meal> getMealScheduleOfDay(DietDay dietDay);


    void removeMealScheduleofDay(DietDay dietDay);

    void removeMeal(Meal meal, DietDay dietDay);

}
