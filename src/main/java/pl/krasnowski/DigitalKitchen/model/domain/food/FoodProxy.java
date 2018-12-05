package pl.krasnowski.DigitalKitchen.model.domain.food;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
public class FoodProxy {
    private String foodID, name, dbName, photo;
    private Origin origin;

    public FoodProxy() {
    }

    public FoodProxy(String foodID, String name, String dbName, String photo, Origin origin) {
        this.foodID = foodID;
        this.name = name;
        this.dbName = dbName;
        this.photo = photo;
        this.origin = origin;
    }
}