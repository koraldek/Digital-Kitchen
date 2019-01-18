package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.user.Intolerance;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Primary
@Component
@ToString(callSuper = true)
@AllArgsConstructor
public class Food extends Consumable implements Serializable {
    private static final long serialVersionUID = 1298885643295984186L;

    /**
     * Map which contains IDs from external databases
     * Map<database_name,foreignID>
     */
    @ElementCollection
    @MapKeyColumn(name = "db_Name")
    @Column(name = "foreign_food_id")
    private Map<String, String> foreignIDs;

    private String photo;

    /**
     * Stores names in Map<Language,names> names are comma separated. For example <English,egg,eggs>
     */
    @ElementCollection
    @MapKeyColumn(name = "language")
    @Column(name = "name")
    private Map<String, String> names;

    @ElementCollection(targetClass = Origin.class)
    @JoinTable(name = "food_origin",
            joinColumns = @JoinColumn(name = "food_id"))
    @Enumerated(EnumType.ORDINAL)
    private Set<Origin> origin;

    /**
     * Array map serving in different units Map<unit_string,quantity>
     */
    @ElementCollection
    @MapKeyColumn(name = "unit")
    @Column(name = "quantity_serving")
    private Map<Unit, Integer> servingSizes;

    @ElementCollection(targetClass = Intolerance.class)
    @JoinTable(name = "food_intolerances",
            joinColumns = @JoinColumn(name = "consumable_ID"))
    @Enumerated(EnumType.ORDINAL)
    private Set<Intolerance> intolerances;

    public Food() {
        names = new HashMap<>();
        servingSizes = new HashMap<>();
        intolerances = new HashSet<>();
        origin = new HashSet<>();
        foreignIDs = new HashMap<>();
    }

    public void addServingSize(Unit unit, int weight) {
        servingSizes.put(unit, weight);
    }

    public String getForeignID(String dbName) {
        return foreignIDs.get(dbName);
    }

    public void addName(String lang, String names) {
        this.names.put(lang, names);
    }

    public void addForeignID(String dbName, String foodID) {
        foreignIDs.put(dbName, foodID);
    }

    public void addOrigin(Origin _origin) {
        origin.add(_origin);
    }

    @Override
    public String getName(String lang) {
        if (names.get(lang) != null)
            return names.get(lang);
        else
            return names.get("en");
    }
}