package pl.krasnowski.DigitalKitchen.model.domains;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
@EqualsAndHashCode
public class RecipeInstruction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long instructionID;

    @Column
    int number;

    @Column
    String description;
}
