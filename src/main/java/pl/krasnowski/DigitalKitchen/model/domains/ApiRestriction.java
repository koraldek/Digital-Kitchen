package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
public class ApiRestriction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long apiRestrictionID;
    @Column
    private String dbName;
    @Column
    private int currPerMinute = 0;
    @Column
    private int currPerDay = 0;
    @Column
    private int currPerMonth = 0;
    @Column
    private int maxPerMinute = 0;
    @Column
    private int maxPerHour = 0;
    @Column
    private int maxPerMonth = 0;

}