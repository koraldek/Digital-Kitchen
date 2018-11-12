package pl.krasnowski.DigitalKitchen.model.domain.kitchen;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Component
public class KitchensTool implements Serializable {

    private static final long serialVersionUID = -5048527591567285906L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long kitchensTool_ID;

    private String name;

    /**
     * Power of tool in Watts. If tool dont use electricity power=0.
     */
    private int power = 0;

    /**
     * Describes estimated conservation time in minutes of use. For example estimate conservation time for knife is 120 minutes.
     */
    private int conservationTime;

    /**
     * Brand of tool. Can faster complainment process.
     */
    private String brand;

    /**
     * Describes how many minutes tool was already used.
     */
    @Column
    private int minUsed;

    /**
     * Sets to disable when tool is indisposed - crashed or conservationTime is reached.
     */
    private boolean isProficient = true;

}