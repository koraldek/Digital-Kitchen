package pl.krasnowski.DigitalKitchen.services.diet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.services.user.UserProfileService;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
@Transactional
public class MealSchedulerImpl implements MealScheduler {
    private final static String[] PREDEFINED_MEALS = new String[]{BREAKFAST, SNACK, LUNCH, DINNER, SUPPER};

    @Autowired
    private DietManager dietManager;

    @Autowired
    private UserProfileService userProfileService;

    @Override
    public List<Meal> initializeMealSchedule() {
        return new ArrayList<>();
    }

    @Override
    public Meal getNearestMeal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Meal getMealByName(String name, LocalDate date) {
        Meal meal = userProfileService.getHistoryDay(date).getFoodAndWorkoutDiary().getMeals().stream()
                .filter(m -> m.getName().equals(name)).findFirst()
                .orElse(null);

        if (meal == null) {
            if (Arrays.asList(PREDEFINED_MEALS).contains(name)) {
                log.trace("Meal of name:{} for date:{} not found. Creating predefined one.", name, date);
                Locale currentLocale = new Locale(LocaleContextHolder.getLocale().getLanguage());
                ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
                meal = dietManager.initializeMeal();
                meal.setName(messages.getString(name));
                userProfileService.getHistoryDay(date).getFoodAndWorkoutDiary().addMeal(meal);
            } else
                log.trace("Returning null for meal name:{}", name);
        } else
            log.trace("Returning meal:{}", meal.toString());
        return meal;
    }

    @Override
    public void addMeal(Meal meal, LocalDate date) {
        userProfileService.getHistoryDay(date).getFoodAndWorkoutDiary().getMeals().add(meal);
        log.debug("Added meal:" + meal.getName() + " to schedule.");
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
    public List<Meal> getFoodDiary(LocalDate date) {
        return userProfileService.getHistoryDay(date).getFoodAndWorkoutDiary().getMeals();
    }

    @Override
    public void removeMealScheduleOfDay(DietDay dietDay) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeMeal(Meal meal, DietDay dietDay) {
        throw new UnsupportedOperationException();
    }
}
