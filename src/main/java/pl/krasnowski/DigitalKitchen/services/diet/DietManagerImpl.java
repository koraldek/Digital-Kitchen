package pl.krasnowski.DigitalKitchen.services.diet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.DAO.DietDAO;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietType;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class DietManagerImpl implements DietManager {

    @Autowired
    private DietUtilities dietUtilities;

    @Autowired
    private DietDAO dietDAO;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<Diet> initializeDiets() {
        List<Diet> newDiets = new ArrayList<>();
        newDiets.add(initializeDiet());
        log.debug("Successfully initialized diets:{}", newDiets);
        return newDiets;
    }

    @Override
    public Diet initializeDiet() {
        Diet nDiet = dietUtilities.computeDiet(DietType.balanced, 50);
        nDiet.setDietName(userService.getCurrentUser().getVisibleName() + defaultDietSuffix);
        nDiet.setDietDays(this.initializeDietDays());
        nDiet.setAuthor(userService.getCurrentUser());
        return nDiet;
    }

    @Override
    public Diet getByID(long id) {
        return dietDAO.getOne(id);
    }

    @Override
    public void addNewDiet(Diet diet) {
        userService.getCurrentUser().getDiets().add(diet);
    }

    @Override
    public void updateDiet(Diet diet) {
        userService.getCurrentUser().getDiets().forEach(d -> {
            if (diet.getDietID() == diet.getDietID())
                BeanUtils.copyProperties(diet, d);
        });
    }

    @Override
    public List<Diet> getDiets() {
        return userService.getCurrentUser().getDiets();
    }

    @Override
    public void removeDiet(Diet diet) {
        userService.getCurrentUser().getDiets().remove(diet);
    }

    @Override
    public Diet getCurrentDiet() {
        return userService.getCurrentUser().getCurrentDiet();
    }

    @Override
    public void updateCurrentDiet(Diet diet) {
        userService.getCurrentUser().setCurrentDiet(diet);
    }

    @Override
    public Set<DietDay> initializeDietDays() {
        Set<DietDay> dds = new HashSet<>();
        dds.add(initializeDietDay());
        return dds;
    }

    @Override
    public DietDay initializeDietDay() {
        DietDay dd = new DietDay();
        dd.setDayName(Calendar.getInstance()
                .getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, LocaleContextHolder.getLocale())
                .toLowerCase());
        dd.setCarbohydrates(0);
        dd.setFat(0);
        dd.setProteins(0);
        dd.setKCalGained(0);
        dd.setDayDate(Timestamp.from(Instant.now()));
        return dd;
    }

    @Override
    public Meal initializeMeal() {
        Meal nMeal = new Meal();
        nMeal.setMealTime(new Timestamp(Instant.now().toEpochMilli()));
        nMeal.setDishes(new HashSet<>());
        nMeal.setPartners(new HashSet<>());

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        String mealName;
        int hour = Integer.valueOf(cal.getDisplayName(Calendar.HOUR_OF_DAY, Calendar.LONG, LocaleContextHolder.getLocale()));
        if (hour > 5 && hour < 12)
            mealName = "breakfast";
        else if (hour >= 12 && hour < 18)
            mealName = "lunch";
        else
            mealName = "dinner";
        nMeal.setName(mealName);
        return nMeal;
    }

    @Override
    public DietDay getCurrentDietDay() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

        return userService.getCurrentUser().getCurrentDiet().getDietDays().stream()
                .filter(dietDay ->
                        dietDay.getDayDate().toLocalDateTime().toLocalDate().getDayOfWeek()
                                .equals(dayOfWeek))
                .findFirst()
                .orElse(null);
    }


}
