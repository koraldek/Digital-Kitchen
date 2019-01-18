package pl.krasnowski.DigitalKitchen.model.domain.kitchen;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.food.FoodWrapper;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Component
public class Kitchen implements Serializable {

    private static final long serialVersionUID = 7714638958398494852L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kitchen_ID")
    private long kitchenID;

    private String name;

    @JoinTable(name = "kitchen_users",
            joinColumns = {@JoinColumn(name = "kitchen_ID")},
            inverseJoinColumns = {@JoinColumn(name = "user_ID")})
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "master_chef_id")
    private User masterChef;

    @JoinTable(name = "kitchen_shopping_list",
            joinColumns = {@JoinColumn(name = "kitchen_ID")},
            inverseJoinColumns = {@JoinColumn(name = "shopping_list_item_ID")})
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ShoppingListItem> shoppingList;

    @JoinTable(name = "kitchen_equipment",
            joinColumns = {@JoinColumn(name = "kitchen_ID")},
            inverseJoinColumns = {@JoinColumn(name = "kitchen_tool_ID")})
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<KitchensTool> kitchenEquipment;

    @JoinTable(name = "kitchen_stock",
            joinColumns = {@JoinColumn(name = "kitchen_ID")},
            inverseJoinColumns = {@JoinColumn(name = "food_wrapper_ID")})
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FoodWrapper> stock;

//    /**
//     * Stores information of scheduled meals in kitchen: special days of year (birthday's, religion events) or meetings.
//     * "period distance" contains information of distance between occurs. 0 value means it will occur only one time.
//     */
//    @OneToMany(cascade = CascadeType.ALL)
//    @Column(name = "kitchen_planned_days")
//    @MapKeyTemporal(TemporalType.DATE)
//    private Map<DietDay, Integer> plannedDays; //TODO change to List<PlannedDay> class objects.


    public Kitchen() {
        kitchenEquipment = new HashSet<>();
        stock = new HashSet<>();
        users = new HashSet<>();
        shoppingList = new HashSet<>();
    }

    public void addItemToShoppingList(ShoppingListItem item) {
        shoppingList.add(item);
    }

    public void removeItemFromShoppingList(ShoppingListItem item) {
        shoppingList.remove(item);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void addKitchenEquipment(KitchensTool kitchensTool) {
        kitchenEquipment.add(kitchensTool);
    }

    public void removeKitchenEquipment(KitchensTool kitchensTool) {
        kitchenEquipment.remove(kitchensTool);
    }

    public void addItemToStock(FoodWrapper item) {
        stock.add(item);
    }

    public void removeItemFromStock(FoodWrapper item) {
        stock.remove(item);
    }
}
