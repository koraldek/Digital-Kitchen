package pl.krasnowski.DigitalKitchen.services.physicalActivity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivity;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivityType;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.user.UserProfileService;

import java.time.LocalDate;
import java.util.*;


@Service
@Slf4j
public class PhysicalActivityServiceImpl implements PhysicalActivityService {


    @Autowired
    UserProfileService userProfileService;

    @Autowired
    Set<PhysicalActivityType> physicalActivityTypes;


    @Override
    public void addPhysicalActivity(PhysicalActivity pa) {
        userProfileService.getCurrentHistoryDay().getDietDay().getWorkouts().add(pa);
    }

    @Override
    public void addPhysicalActivity(PhysicalActivity pa, LocalDate date) {
        userProfileService.getHistoryDay(date).addPhysicalActivity(pa);
    }

    @Override
    public List<PhysicalActivity> getPhysicalActivityOfDay() {
        return userProfileService.getCurrentHistoryDay().getDietDay().getWorkouts();
    }

    @Override
    public List<PhysicalActivity> getPhysicalActivityOfDay(LocalDate date) {
        return userProfileService.getHistoryDay(date).getDietDay().getWorkouts();
    }

    @Override
    public Map<LocalDate, List<PhysicalActivity>> getPhysicalActivity(LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, List<PhysicalActivity>> result = new HashMap<>();
        LocalDate start = startDate.minusDays(1); // to include start day when using .isAfter()
        LocalDate end = endDate.plusDays(1);

        userProfileService.getDietHistory().forEach(
                (historyDay) -> {
                    LocalDate dayDate = historyDay.getDietDay().getDayDate().toLocalDateTime().toLocalDate();
                    if (dayDate.isAfter(start) && dayDate.isBefore(end))
                        result.put(historyDay.getDietDay().getDayDate().toLocalDateTime().toLocalDate(), historyDay.getDietDay().getWorkouts());
                });
        return result;
    }

    @Override
    public void updatePhysicalActivity(PhysicalActivity oldActivity, PhysicalActivity newActivity, LocalDate date) {
        userProfileService.getHistoryDay(date).getDietDay().getWorkouts().forEach(
                tempPA -> {
                    if (tempPA.equals(oldActivity))
                        BeanUtils.copyProperties(newActivity, tempPA);
                });
    }

    @Override
    public void removePhysicalActivity(PhysicalActivity pa, LocalDate date) {
        userProfileService.getHistoryDay(date).getDietDay().getWorkouts().remove(pa);
    }

    @Override
    public void addPartner(User partner, PhysicalActivity pa) {
        pa.addPartner(partner);
    }

    @Override
    public void removePartner(User partner, PhysicalActivity pa) {
        pa.removePartner(partner);
    }


    @Override
    public int getBurnedCaloriesToday() {
        return userProfileService.getCurrentHistoryDay().getDietDay().getKcalBurned();
    }

    @Override
    public int getBurnedCalories(LocalDate date) {
        return userProfileService.getHistoryDay(date).getDietDay().getKcalBurned();
    }

    @Override
    public Map<LocalDate, List<PhysicalActivity>> getActivitiesOfType(PhysicalActivityType pat) {
        Map<LocalDate, List<PhysicalActivity>> result = new HashMap<>();
        userProfileService.getDietHistory().forEach(
                (dietDay) -> {
                    LocalDate dayDate = dietDay.getDietDay().getDayDate().toLocalDateTime().toLocalDate();
                    dietDay.getDietDay().getWorkouts().forEach( // get workouts of each day
                            (physicalActivity -> {
                                if (physicalActivity.getActivityType().equals(pat)) {
                                    result.computeIfAbsent(dayDate, k -> result.put(k, new ArrayList<>()));
                                    result.get(dayDate).add(physicalActivity);
                                }
                            }));
                });
        return result;
    }


    @Override
    public Map<LocalDate, List<PhysicalActivity>> getActivitiesOfType(PhysicalActivityType pat, LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, List<PhysicalActivity>> result = new HashMap<>();
        LocalDate start = startDate.minusDays(1); // to include start day when using .isAfter()
        LocalDate end = endDate.plusDays(1);

        userProfileService.getDietHistory().forEach(
                (dietDay) -> {
                    LocalDate dayDate = dietDay.getDietDay().getDayDate().toLocalDateTime().toLocalDate();
                    if (dayDate.isAfter(start) && dayDate.isBefore(end))
                        dietDay.getDietDay().getWorkouts().forEach( // get workouts of each day
                                (physicalActivity -> {
                                    if (physicalActivity.getActivityType().equals(pat)) {
                                        result.computeIfAbsent(dayDate, k -> new ArrayList<>());
                                        result.get(dayDate).add(physicalActivity);
                                    }
                                }));
                });
        return result;
    }

    @Override
    public void addNewActivityType(PhysicalActivityType pat) {
        physicalActivityTypes.add(pat);
        log.debug("Added new activity type:{}", pat.getActivityName());
    }
}
