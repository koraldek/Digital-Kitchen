package pl.krasnowski.DigitalKitchen.model.domain.diet;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Component
public class DietDay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "diet_day_id")
    private long dietDayID;

    /**
     * Energy gained and burned by food and exercises/activity.
     */
    private int KcalGained = 0, KcalBurned = 0;

    /**
     * Value in grams.
     */
    private int proteins = 0, fat = 0, carbohydrates = 0;

    @Column(name = "day_name")
    private String dayName;

    @Column(name = "day_date")
    private Timestamp dayDate;

    @JoinTable(
            name = "diet_day_meals",
            joinColumns = {@JoinColumn(name = "diet_day_id")},
            inverseJoinColumns = {@JoinColumn(name = "meal_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL)
    private List<Meal> meals;

    @JoinTable(
            name = "diet_day_workout",
            joinColumns = {@JoinColumn(name = "diet_day_id")},
            inverseJoinColumns = {@JoinColumn(name = "physical_activity_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhysicalActivity> workout;


    public DietDay() {
        meals = new ArrayList<>();
        workout = new ArrayList<>();
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public void removeMeal(Meal meal) {
        meals.remove(meal);
    }

    public void addWorkout(PhysicalActivity pa) {
        workout.add(pa);
    }

    public void removeWorkout(PhysicalActivity pa) {
        workout.remove(pa);
    }

    public void addKcalBurned(int kcalBurned) {
        this.KcalBurned = this.KcalBurned + kcalBurned;
    }

    public void substractKcalBurned(int kcalBurned) {
        this.KcalBurned = this.KcalBurned - kcalBurned;
    }

    public void addKcalGained(int KcalGained) {
        this.KcalGained = this.KcalGained + KcalGained;
    }

    public void substractKcalGained(int KcalGained) {
        this.KcalGained = this.KcalGained - KcalGained;
    }
}