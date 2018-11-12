package pl.krasnowski.DigitalKitchen.services.diet;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MealSchedulerImpl implements MealScheduler {

    @Override
    public List<Meal> initializeMealSchedule() {
        return new ArrayList<>();
    }

    @Override
    public Meal getNearestMeal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addMeal(Meal meal, DietDay dietDay) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateMealSchedule(Set<Meal> meals, DietDay dietDay) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Set<Meal> getMealScheduleOfDay(DietDay dietDay) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeMealScheduleofDay(DietDay dietDay) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeMeal(Meal meal, DietDay dietDay) {
        throw new UnsupportedOperationException();
    }

}
