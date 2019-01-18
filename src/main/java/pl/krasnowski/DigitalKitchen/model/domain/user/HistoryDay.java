package pl.krasnowski.DigitalKitchen.model.domain.user;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivity;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Component
@Entity
@Data
@Table(name = "history_day")
public class HistoryDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_day_id")
    private long historyDayID;

    private double weight;

    /**
     * URL  to picture of silhouette
     */
    @Column(name = "picture_of_day")
    private String pictureOfDay;

    @JoinColumn(name = "diet_day_ID")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DietDay foodAndWorkoutDiary;

    public Timestamp getDayDate() {
        return foodAndWorkoutDiary.getDayDate();
    }

    public void addMeal(Meal meal) {
        foodAndWorkoutDiary.addMeal(meal);
    }

    public void removeMeal(Meal meal) {
        foodAndWorkoutDiary.removeMeal(meal);
    }

    public void addPhysicalActivity(PhysicalActivity pa) {
        this.foodAndWorkoutDiary.getWorkout().add(pa);
    }

    public void removePhysicalActivity(PhysicalActivity pa) {
        this.foodAndWorkoutDiary.getWorkout().remove(pa);
    }
}
