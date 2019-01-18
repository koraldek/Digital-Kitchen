package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
class AltMeasure {
    private int servingWeight, seq, qty;
    private String measure;

    AltMeasure(int servingWeight, String measure) {
        this.servingWeight = servingWeight;
        this.measure = measure;
    }
}
