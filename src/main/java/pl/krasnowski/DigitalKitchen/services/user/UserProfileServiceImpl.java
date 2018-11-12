package pl.krasnowski.DigitalKitchen.services.user;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.DAO.UserDAO;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivity;
import pl.krasnowski.DigitalKitchen.model.domain.user.*;
import pl.krasnowski.DigitalKitchen.services.diet.DietManager;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    @NonNull
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @NonNull
    private final UserDAO userDAO;

    @Autowired
    @NonNull
    private final UserService uService;

    @Autowired
    @NonNull
    DietManager dietManager;


    public UserProfileServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserDAO userDAO, UserService uService, DietManager dietManager) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDAO = userDAO;
        this.uService = uService;
        this.dietManager = dietManager;
    }

    @Override
    public void addIntolerance(Intolerance intolerance) {
        uService.getCurrentUser().getIntolerances().add(intolerance);
    }

    @Override
    public void removeIntolerance(Intolerance intolerance) {
        uService.getCurrentUser().getIntolerances().remove(intolerance);
    }

    @Override
    public void updateNotifications(NotificationSetup notificationSetup) {

        if (uService.getCurrentUser().getNotificationSetup().isEmpty())
            uService.getCurrentUser().getNotificationSetup().add(notificationSetup);
        else
            for (NotificationSetup ns : uService.getCurrentUser().getNotificationSetup())
                if (ns.getNotificationSource().equals(notificationSetup.getNotificationSource())) {
                    uService.getCurrentUser().getNotificationSetup().remove(ns);
                    uService.getCurrentUser().getNotificationSetup().add(notificationSetup);
                    return;
                }
    }

    @Override
    public boolean changePassword(String oldPass, String newPass) {
        if (bCryptPasswordEncoder.matches(oldPass, uService.getCurrentUser().getPassword())) {
            uService.getCurrentUser().setPassword(bCryptPasswordEncoder.encode(newPass));
            userDAO.updateUser(uService.getCurrentUser());
            return true;
        } else
            return false;
    }

    @Override
    public void addFoodPreferention(FoodPreferention foodPreferention) {
        uService.getCurrentUser().addFoodPreferention(foodPreferention);
    }

    @Override
    public void removeFoodPreferention(FoodPreferention foodPreferention) {
        uService.getCurrentUser().removeFoodPreferention(foodPreferention);
    }

    @Override
    public HistoryDay getCurrentHistoryDay() {
        HistoryDay hd = uService.getCurrentUser().getHistoryDays().get(LocalDate.now());

        if (hd == null) {
            hd = new HistoryDay();
            DietDay dd = dietManager.initializeDietDay();
            hd.setDietDay(dd);

            uService.getCurrentUser().addHistoryDay(LocalDate.now(), hd);
        }
        return hd;
    }


    @Override
    public HistoryDay getHistoryDay(LocalDate date) {
        return uService.getCurrentUser().getHistoryDay(date);
    }

    @Override
    public Set<HistoryDay> getDietHistory() {
        return new HashSet<>(uService.getCurrentUser().getHistoryDays().values());
    }

    @Override
    public void updateHistoryDay(HistoryDay hd) {
        uService.getCurrentUser().getHistoryDays()
                .replace(hd.getDietDay().getDayDate().toLocalDateTime().toLocalDate(), hd);
    }

    @Override
    public void removeHistoryDay(LocalDate date) {
        uService.getCurrentUser().removeHistoryDay(date);
    }

    @Override
    public void addHistoryDay(HistoryDay hd) {
        uService.getCurrentUser().addHistoryDay(hd.getDietDay().getDayDate().toLocalDateTime().toLocalDate(), hd);
    }

    @Override
    public List<Meal> getSavedMeals() {
        return uService.getCurrentUser().getSavedMeals();
    }

    @Override
    public List<Recipe> getSavedRecipes() {
        return uService.getCurrentUser().getSavedRecipes();
    }

    @Override
    public List<PhysicalActivity> getSavedWorkouts() {
        return uService.getCurrentUser().getSavedWorkouts();
    }

    @Override
    public UserDTO toDTO(User u) {
        UserDTO dto = new UserDTO();
        dto.setUserID(u.getUserID());
        dto.setAge(u.getAge());
        dto.setBodyType(u.getBodyType());
        dto.setEmail(u.getEmail());
        dto.setHeight(u.getHeight());
        dto.setWeight(u.getWeight());
        dto.setSex(u.getSex());
        dto.setVisibleName(u.getVisibleName());
        return dto;
    }
}
