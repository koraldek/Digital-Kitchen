package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class FoodProxy {
    private static final long serialVersionUID = -1562241636104989459L;
    private String foodID, name, dbName, photo;
    private Origin origin;
}