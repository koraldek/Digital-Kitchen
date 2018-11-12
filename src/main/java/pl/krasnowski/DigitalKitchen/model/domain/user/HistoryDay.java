package pl.krasnowski.DigitalKitchen.model.domain.user;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Meal;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivity;

import javax.persistence.*;

@Component
@Entity
@Data
@Table(name = "history_days")
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
    private DietDay dietDay;


    public void addMeal(Meal meal) {
        dietDay.addMeal(meal);
    }

    public void removeMeal(Meal meal) {
        dietDay.removeMeal(meal);
    }

    public void addPhysicalActivity(PhysicalActivity pa) {
        this.dietDay.getWorkouts().add(pa);
    }

    public void removePhysicalActivity(PhysicalActivity pa) {
        this.dietDay.getWorkouts().remove(pa);
    }
}
