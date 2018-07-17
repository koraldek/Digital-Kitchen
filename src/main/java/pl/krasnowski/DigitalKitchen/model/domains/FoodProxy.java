package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FoodProxy {
    private String foodID, name, dbName, photo;
    private Origin origin;
}
