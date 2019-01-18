package pl.krasnowski.DigitalKitchen.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class LoggedMealDTO {
    String entityDbName, entityID;
    String serving_unit;
    String serving_size;
    String time, date, meal, foodType;
}