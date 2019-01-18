package pl.krasnowski.DigitalKitchen.services.physicalActivity;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivity;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivityType;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public interface PhysicalActivityService {

    void addPhysicalActivity(PhysicalActivity pa, LocalDate date);

    List<PhysicalActivity> getPhysicalActivityOfDay(LocalDate date);

    /**
     * Gives list of physical activity from passed period.
     */
    Map<LocalDate, List<PhysicalActivity>> getPhysicalActivity(LocalDate startDate, LocalDate endDate);

    void updatePhysicalActivity(PhysicalActivity oldActivity, PhysicalActivity newActivity, LocalDate date);

    void removePhysicalActivity(PhysicalActivity pa, LocalDate date);

    void addPartner(User partner, PhysicalActivity pa);

    void removePartner(User partner, PhysicalActivity pa);

    /**
     * @param physicalActivityType type of activity
     * @return Activities of particular type
     */
    Map<LocalDate, List<PhysicalActivity>> getActivitiesOfType(PhysicalActivityType physicalActivityType);

    Map<LocalDate, List<PhysicalActivity>> getActivitiesOfType(PhysicalActivityType physicalActivityType, LocalDate startDate, LocalDate endDate);

    /**
     * Adds new type of activity to system.
     */
    void addNewActivityType(PhysicalActivityType pat);


}