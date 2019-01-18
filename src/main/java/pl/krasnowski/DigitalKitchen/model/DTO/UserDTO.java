package pl.krasnowski.DigitalKitchen.model.DTO;

import lombok.Data;
import pl.krasnowski.DigitalKitchen.model.domain.user.BodyType;
import pl.krasnowski.DigitalKitchen.model.domain.user.Sex;

@Data
public class UserDTO {
    long userID;
    int age;
    double height, weight;
    String email, visibleName;
    Sex sex;
    BodyType bodyType;
}