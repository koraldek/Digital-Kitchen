package pl.krasnowski.DigitalKitchen.services.user;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.user.Role;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.model.domain.user.UserDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Service
public interface UserService extends Serializable {

    User initializeUser();

    User findByEmail(String email);

    User findByUsername(String nickname);

    List<User> findByUsernameOrVisibleName(String nickOrVisibleName);

    User getCurrentUser();

    User preCreateUser(User user);

    /**
     * Perform creating user after earlier stage of initialization.
     */
    User postCreateUser(User usr);



    void updateUser(UserDTO userDTO);

    void updateUser(User user);

    User authenticateUser(String nickOrEmail, String password);

    void deleteByID(long id);



    void updateRoles(Set<Role> roles);

    Set<Role> getUserRoles();


    void activateUser();
}