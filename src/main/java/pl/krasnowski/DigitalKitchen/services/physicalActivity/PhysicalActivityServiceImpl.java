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
    public void addPhysicalActivity(PhysicalActivity pa, LocalDate date) {
        userProfileService.getHistoryDay(date).addPhysicalActivity(pa);
    }

    @Override
    public List<PhysicalActivity> getPhysicalActivityOfDay(LocalDate date) {
        return userProfileService.getHistoryDay(date).getFoodAndWorkoutDiary().getWorkout();
    }

    @Override
    public Map<LocalDate, List<PhysicalActivity>> getPhysicalActivity(LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, List<PhysicalActivity>> result = new HashMap<>();
        LocalDate start = startDate.minusDays(1); // to include start day when using .isAfter()
        LocalDate end = endDate.plusDays(1);

        userProfileService.getDietHistory().forEach(
                (historyDay) -> {
                    LocalDate dayDate = historyDay.getFoodAndWorkoutDiary().getDayDate().toLocalDateTime().toLocalDate();
                    if (dayDate.isAfter(start) && dayDate.isBefore(end))
                        result.put(historyDay.getFoodAndWorkoutDiary().getDayDate().toLocalDateTime().toLocalDate(), historyDay.getFoodAndWorkoutDiary().getWorkout());
                });
        return result;
    }

    @Override
    public void updatePhysicalActivity(PhysicalActivity oldActivity, PhysicalActivity newActivity, LocalDate date) {
        userProfileService.getHistoryDay(date).getFoodAndWorkoutDiary().getWorkout().forEach(
                tempPA -> {
                    if (tempPA.equals(oldActivity))
                        BeanUtils.copyProperties(newActivity, tempPA);
                });
    }

    @Override
    public void removePhysicalActivity(PhysicalActivity pa, LocalDate date) {
        userProfileService.getHistoryDay(date).getFoodAndWorkoutDiary().getWorkout().remove(pa);
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
    public Map<LocalDate, List<PhysicalActivity>> getActivitiesOfType(PhysicalActivityType pat) {
        Map<LocalDate, List<PhysicalActivity>> result = new HashMap<>();
        userProfileService.getDietHistory().forEach(
                (dietDay) -> {
                    LocalDate dayDate = dietDay.getFoodAndWorkoutDiary().getDayDate().toLocalDateTime().toLocalDate();
                    dietDay.getFoodAndWorkoutDiary().getWorkout().forEach( // get workout of each day
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
        LocalDate end = endDate.plusDays(1); // to include start day when using .isBefore()

        userProfileService.getDietHistory().forEach(
                (dietDay) -> {
                    LocalDate dayDate = dietDay.getFoodAndWorkoutDiary().getDayDate().toLocalDateTime().toLocalDate();
                    if (dayDate.isAfter(start) && dayDate.isBefore(end))
                        dietDay.getFoodAndWorkoutDiary().getWorkout().forEach( // get workout of each day
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