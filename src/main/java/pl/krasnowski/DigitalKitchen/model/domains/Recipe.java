package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@ToString
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long recipeID;

    @JoinTable(
            name = "recipe_ingredients",
            joinColumns = {@JoinColumn(name = "recipeID")},
            inverseJoinColumns = {@JoinColumn(name = "ingredientsID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Ingredient> ingredients;

    @JoinTable(
            name = "recipe_tools",
            joinColumns = {@JoinColumn(name = "mealID")},
            inverseJoinColumns = {@JoinColumn(name = "requiredToolID")}
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RequiredTool> requiredTools;

    /**
     * Estimated prepare time in minutes.
     */
    @Column
    private int PrepareTime,totalTime,servings;

    @Column
    private String Instruction;

}