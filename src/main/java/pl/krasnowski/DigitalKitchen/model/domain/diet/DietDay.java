package pl.krasnowski.DigitalKitchen.model.domain.diet;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Component
public class DietDay implements Serializable {
    private static final long serialVersionUID = 7707336836949673574L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long dayDiet_ID;

    /**
     * Energy gained and burned by food and exercises/activity.
     */
    private int kCalGained, KcalBurned;

    /**
     * Value in grams.
     */
    private int proteins, fat, carbohydrates;

    private String dayName;

    private Timestamp dayDate;

    @JoinTable(
            name = "DietDay_meal_schedule",
            joinColumns = {@JoinColumn(name = "dayDietID")},
            inverseJoinColumns = {@JoinColumn(name = "mealID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Meal> meals;

    @JoinTable(
            name = "DietDay_workout_schedule",
            joinColumns = {@JoinColumn(name = "dayDietID")},
            inverseJoinColumns = {@JoinColumn(name = "physical_activity_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhysicalActivity> workouts;

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public void removeMeal(Meal meal) {
        meals.remove(meal);
    }
}