package pl.krasnowski.DigitalKitchen.services.user;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.DTO.UserDTO;
import pl.krasnowski.DigitalKitchen.model.domain.user.Role;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public interface UserService extends Serializable {

    /**
     * Initializes User object with some predefined data.
     *
     * @return initialized user object without personalized data.
     */
    User initializeUser();

    User findByEmail(String email);

    User findByUsername(String nickname);

    /**
     * Method for searching user by other users.
     *
     * @param nickOrVisibleName Part of nick or visible name.
     * @return users with matching search criteria.
     */
    List<User> findByUsernameOrVisibleName(String nickOrVisibleName);

    User getCurrentUser();

    void createUser(User user);

    void updateUser(UserDTO userDTO);

    void updateUser(User user);

    User authenticateUser(String nickOrEmail, String password);

    /**
     * @param ID of user.
     * @throws pl.krasnowski.DigitalKitchen.model.exceptions.UserNotFoundException when user with ID is not present.
     */
    void deleteUser(long ID);

    void updateRoles(Set<Role> roles);

    Set<Role> getUserRoles();

    /**
     * Activates user account. For example via e-mail or SMS verification.
     */
    void activateUser();
}