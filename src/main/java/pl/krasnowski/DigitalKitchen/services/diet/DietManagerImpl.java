package pl.krasnowski.DigitalKitchen.services.diet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krasnowski.DigitalKitchen.model.DAO.DietDAO;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.user.UserProfileService;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import java.sql.Timestamp;
import java.time.*;
import java.util.*;

@Slf4j
@Service
@Transactional
public class DietManagerImpl implements DietManager {

    @Autowired
    private DietUtilities dietUtilities;

    @Autowired
    private DietDAO dietDAO;

    @Autowired
    private UserProfileService userProfileService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public List<Diet> initializeDiets(User user) {
        List<Diet> newDiets = new ArrayList<>();
        newDiets.add(initializeDiet(user));
        log.debug("Successfully initialized diets:{}", newDiets);
        return newDiets;
    }

    @Override
    public Diet initializeDiet(User user) {
        Diet nDiet = new Diet();
        nDiet.setDietName(user.getVisibleName() + defaultDietSuffix);
        nDiet.setDietDays(this.initializeDietDays());
        nDiet.setAuthor(user);
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
    public void addFoodToDiet(Meal food) {
        throw new UnsupportedOperationException();
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
        Locale currentLocale = new Locale(LocaleContextHolder.getLocale().getLanguage());
        ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
        String dayName = Calendar.getInstance()
                .getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) // Keys are written in english
                .toLowerCase();
        DietDay dd = new DietDay();
        dd.setDayName(messages.getString(dayName));
        //   dd.setDayDate(Timestamp.valueOf(LocalDate.now().atStartOfDay())); // xdd
        dd.setDayDate(new Timestamp(ZonedDateTime.of(LocalDate.now().atStartOfDay(), ZoneId.of("UTC")).toInstant().toEpochMilli()));

        dd.setMeals(new ArrayList<>());
        dd.setWorkout(new ArrayList<>());
        return dd;
    }

    @Override
    public Meal initializeMeal() {
        Locale currentLocale = new Locale(LocaleContextHolder.getLocale().getLanguage());
        ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
        Meal nMeal = new Meal();
        nMeal.setDishes(new HashSet<>());
        nMeal.setPartners(new HashSet<>());

        int hour = LocalTime.now().getHour();
        if ((hour >= 0 && hour < 4) || (hour >= 19))
            nMeal.setName(messages.getString("supper"));
        else if (hour >= 4 && hour < 12)
            nMeal.setName(messages.getString("breakfast"));
        else if (hour >= 12 && hour < 15)
            nMeal.setName(messages.getString("lunch"));
        else
            nMeal.setName(messages.getString("dinner"));

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
                .orElse(initializeDietDay());
    }

    @Override
    public DietDay getDietDay(LocalDate date) {
        return userService.getCurrentUser().getCurrentDiet().getDietDays().stream()
                .filter(dietDay ->
                        dietDay.getDayDate().toLocalDateTime().toLocalDate().equals(date))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int getKcalGained(LocalDate date) {
        return userProfileService.getHistoryDay(date).getFoodAndWorkoutDiary().getKcalGained();
    }

    @Override
    public int getBurnedKcal(LocalDate date) {
        return userProfileService.getHistoryDay(date).getFoodAndWorkoutDiary().getKcalBurned();
    }

}