package pl.krasnowski.DigitalKitchen.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.dao.RoleDAO;
import pl.krasnowski.DigitalKitchen.dao.UserDAO;
import pl.krasnowski.DigitalKitchen.model.domains.Intolerance;
import pl.krasnowski.DigitalKitchen.model.domains.Role;
import pl.krasnowski.DigitalKitchen.model.domains.User;

import java.util.Arrays;
import java.util.HashSet;


@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public User findUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        Role userRole = roleDAO.findByroleName("ADMIN");

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        user.setActive(1);
        user.setIntolerances(
                new HashSet<Intolerance>() {
                    private static final long serialVersionUID = -9132687044006606279L;

                    {
                        add(Intolerance.dairy);
                        add(Intolerance.none);
                        add(Intolerance.peanut);
                        add(Intolerance.gluten);
                    }
                }
        );

        userDAO.save(user);

        user.toString();

    }

}
