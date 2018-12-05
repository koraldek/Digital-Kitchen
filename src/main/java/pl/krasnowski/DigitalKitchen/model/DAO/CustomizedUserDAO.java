package pl.krasnowski.DigitalKitchen.model.DAO;

import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.model.domain.user.UserDTO;

@Repository
public interface CustomizedUserDAO {

    void updateUserDTO(UserDTO user);

    void updateUser(User user);
}
