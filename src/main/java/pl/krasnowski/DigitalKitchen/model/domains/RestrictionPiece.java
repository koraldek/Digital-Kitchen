package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
@EqualsAndHashCode
public class RestrictionPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int restrictionPieceID;
    @Column
    private int period, current, max;
}
