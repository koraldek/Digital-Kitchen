package pl.krasnowski.DigitalKitchen.model.domain.diet;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.food.FoodWrapper;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Wrapper for food and time when it was eaten.
 */
@Entity
@Data
@Component
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private Long mealID;
    private String name;

    @Column(name = "meal_time")
    private Timestamp mealTime;
    @Column(name = "take_place")
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
            inverseJoinColumns = {@JoinColumn(name = "food_wrapper_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<FoodWrapper> dishes;

    public Meal() {
    }


    public void addFood(FoodWrapper food) {
        dishes.add(food);
    }

    public void removeFood(FoodWrapper food) {
        dishes.remove(food);
    }

    public void addPartner(User partner) {
        partners.add(partner);
    }

    public void removePartner(User partner) {
        partners.remove(partner);
    }
}