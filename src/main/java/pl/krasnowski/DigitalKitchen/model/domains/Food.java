package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@ToString
@Data
@EqualsAndHashCode
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long food_id;

    /**
     * Map which contains IDs from external databases
     * Map<database_name,foreignID>
     */
    @ElementCollection
    @MapKeyColumn(name = "db_Name")
    @Column(name = "food_id")
    private Map<String, String> foreignIDs = new HashMap<>();

    private String photo;
    /**
     * Stores names in Map<Language,names> names are comma separated. For example <English,egg,eggs>
     */
    @ElementCollection
    @MapKeyColumn(name = "language")
    @Column(name = "name")
    private Map<String, String> names = new HashMap<>();

    @ElementCollection(targetClass = Origin.class)
    @JoinTable(name = "food_origin",
            joinColumns = @JoinColumn(name = "food_id"))
    @Enumerated(EnumType.ORDINAL)
    private Set<Origin> origin = new HashSet<>();

    /**
     * Expiration time after food is opened/created in hours (when hold in proper conditions)
     */
    @Column
    private int expTimeAfterOpen;

    public void addName(String lang, String names) {
        this.names.put(lang, names);
    }

    public void addForeignID(String dbName, String foodID) {
        foreignIDs.put(dbName, foodID);
    }

    public void addOrigin(Origin _origin) {
        origin.add(_origin);
    }

    /**
     * Array map serving in different units Map<unit_string,int>
     */
    @ElementCollection
    @MapKeyColumn(name = "unit")
    @Column(name = "quantity_serving")
    private Map<String, Integer> servingSizes = new HashMap<>();

    @ElementCollection(targetClass = Intolerance.class)
    @JoinTable(name = "food_intolerances",
            joinColumns = @JoinColumn(name = "foodid"))
    @Enumerated(EnumType.ORDINAL)
    private Set<Intolerance> intolerances = new HashSet<>();

    @ElementCollection
    @MapKeyColumn(name = "Nutrient_id")
    @Column(name = "quantity")
    private Map<Nutrient, Double> nutrients = new HashMap<>();


    public void addServingSize(String unit, int size) {
        servingSizes.put(unit, size);
    }

    public void addNutrient(Nutrient nutrient, double value) {
        nutrients.put(nutrient, value);
    }

    public String getForeignID(String dbName) {
        return foreignIDs.get(dbName);
    }
}