package pl.krasnowski.DigitalKitchen.services.diet;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * CRUD for Meal object and interface to manage schedules.
 */
@Service
public interface MealScheduler {
    String BREAKFAST = "breakfast";
    String SNACK = "snack";
    String LUNCH = "lunch";
    String DINNER = "dinner";
    String SUPPER = "supper";


    List<Meal> initializeMealSchedule();

    Meal getNearestMeal();

    /**
     * @param name of meal.
     * @return existing or predefined meal (breakfast, dinner etc) from HistoryDay.
     */
    Meal getMealByName(String name, LocalDate date);

    void addMeal(Meal meal, LocalDate date);

    void updateMealSchedule(Set<Meal> meals, DietDay dietDay);

    Set<Meal> getMealScheduleOfDay(DietDay dietDay);

    List<Meal> getFoodDiary(LocalDate date);

    void removeMealScheduleOfDay(DietDay dietDay);

    void removeMeal(Meal meal, DietDay dietDay);

}