package pl.krasnowski.DigitalKitchen.services.kitchen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.kitchen.KitchensTool;
import pl.krasnowski.DigitalKitchen.model.domain.user.Role;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class KitchenManagerImpl implements KitchenManager {


    @Autowired
    KitchenService kitchenService;


    @Override
    public void addUser(User user, Set<Role> kitchenPrivileges) {

    }

    @Override
    public void removeUser(User user) {
        kitchenService.getUserKitchen().getUsers().remove(user);

    }

    @Override
    public void promoteMasterChef(User user) {

    }

    @Override
    public void changeKitchensName(String newName) {
        kitchenService.getUserKitchen().setName(newName);
    }

    @Override
    public void addPlannedDay(DietDay plannedDay, Integer periodDistance) {

    }

    @Override
    public DietDay getPlannedMealsOfDay(LocalDate date) {
        return null;
    }

    @Override
    public List<DietDay> getPlannedDays() {
        return null;
    }

    @Override
    public void removePlannedDay(DietDay plannedDay) {

    }

    @Override
    public void addKitchenEquipment(KitchensTool kitchensTool) {

    }

    @Override
    public void removeKitchenEquipment(KitchensTool kitchensTool) {

    }

    @Override
    public Set<KitchensTool> getKitchenEquipment() {
        return null;
    }

    @Override
    public void updateKitchenTool(KitchensTool kitchensTool) {

    }
}
