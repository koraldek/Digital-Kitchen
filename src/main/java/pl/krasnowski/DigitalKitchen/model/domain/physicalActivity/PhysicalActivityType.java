package pl.krasnowski.DigitalKitchen.model.domain.physicalActivity;


import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Entity
@Component
public class PhysicalActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "physical_activity_type_ID")
    private long physicalActivityTypeID;

    private String code, majorHeading, activityName;
    private double METValue;
}
