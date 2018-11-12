package pl.krasnowski.DigitalKitchen.model.domain.diet;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.food.Consumable;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@Component
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private long mealID;
    private String name;
    private Timestamp mealTime;
    private boolean takePlace;

    @JoinTable(
            name = "meal_partners",
            joinColumns = {@JoinColumn(name = "meal_ID")},
            inverseJoinColumns = {@JoinColumn(name = "User_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> partners;

    @JoinTable(
            name = "meal_dishes",
            joinColumns = {@JoinColumn(name = "meal_ID")},
            inverseJoinColumns = {@JoinColumn(name = "consumable_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Consumable> dishes;


    public void addFood(Consumable food) {
        dishes.add(food);
    }

    public void removeFood(Consumable food) {
        dishes.remove(food);
    }

    public void addPartner(User partner) {
        partners.add(partner);
    }

    public void removePartner(User partner) {
        partners.remove(partner);
    }
}