package pl.krasnowski.DigitalKitchen.model.domain.physicalActivity;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

/**
 * Main container for physical activity data
 */
@Entity
@Data
@Component
public class PhysicalActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "physical_activity_ID")
    private long physicalActivityID;

    /**
     * Start time of activity. For example 11:15.
     */
    private LocalTime startTime;

    /**
     * Duration of activity in minutes.
     */
    private int duration;

    private int caloriesBurned;

    @JoinTable(
            name = "physical_activity_partners",
            joinColumns = {@JoinColumn(name = "physical_activity_id")},
            inverseJoinColumns = {@JoinColumn(name = "User_ID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> partners;

    @OneToOne
    private PhysicalActivityType activityType;

    /**
     * Additional data stored for activity. For example number of steps, push ups, kilometers.
     */
    @ElementCollection
    @MapKeyColumn(name = "additional_data_key")
    @Column(name = "additional_data_value")
    private Map<String, String> additionalData;

    public void addPartner(User partner) {
        partners.add(partner);
    }

    public void removePartner(User partner) {
        partners.remove(partner);
    }
}