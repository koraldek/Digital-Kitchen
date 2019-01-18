package pl.krasnowski.DigitalKitchen.model.domain.diet;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Stores information about nutrients consumed by user and aggregates
 * DietDays which contains preferred daily meals and workout.
 */
@Entity
@Data
@Component
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_ID")
    private long dietID;

    private String dietName;

    /**
     * Current value of consumed nutrient/energy
     */
    private int kCal, carbohydrates, proteins, fat;

    @JoinTable(
            name = "diet_diet_days",
            joinColumns = {@JoinColumn(name = "diet_ID")},
            inverseJoinColumns = {@JoinColumn(name = "diet_day_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DietDay> dietDays;

    @JoinColumn(name = "author_id")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private User author;

    @JoinTable(
            name = "diet_nutrients",
            joinColumns = {@JoinColumn(name = "diet_ID")},
            inverseJoinColumns = {@JoinColumn(name = "diet_nutrient_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DietNutrient> nutrients;

    @Enumerated(EnumType.ORDINAL)
    private DietType dietType;

    @Override
    public String toString() {
        return "Diet{" +
                "dietID=" + dietID +
                ", dietName='" + dietName + '\'' +
                ", kCal=" + kCal +
                ", carbohydrates=" + carbohydrates +
                ", proteins=" + proteins +
                ", fat=" + fat +
                ", dietDays=" + dietDays +
                ", author=" + author.getVisibleName() +
                '}';
    }

}