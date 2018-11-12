package pl.krasnowski.DigitalKitchen.model.domain.user;

import lombok.Data;

@Data
public class UserDTO {
    long userID;
    int age;
    double height, weight;
    String email, visibleName;
    Sex sex;
    BodyType bodyType;
}
