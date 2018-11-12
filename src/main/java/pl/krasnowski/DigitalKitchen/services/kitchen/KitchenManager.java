package pl.krasnowski.DigitalKitchen.services.kitchen;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.KitchensTool;
import pl.krasnowski.DigitalKitchen.model.domain.user.Role;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public interface KitchenManager {

    void addUser(User user, Set<Role> kitchenPrivileges);

    void removeUser(User user);

    void promoteMasterChef(User user);

    void changeKitchensName(String newName);


    /**
     * @param plannedDay     contains date and planned meals
     * @param periodDistance period in days. 0 - plan just once, -1 every month, -2 every year
     */
    void addPlannedDay(DietDay plannedDay, Integer periodDistance);

    DietDay getPlannedMealsOfDay(LocalDate date);

    List<DietDay> getPlannedDays();

    void removePlannedDay(DietDay plannedDay);


    void addKitchenEquipment(KitchensTool kitchensTool);

    void removeKitchenEquipment(KitchensTool kitchensTool);

    Set<KitchensTool> getKitchenEquipment();
    /**
     * Updates kitchen tool.
     *
     * @param kitchensTool updated data
     */
    void updateKitchenTool(KitchensTool kitchensTool);
}
