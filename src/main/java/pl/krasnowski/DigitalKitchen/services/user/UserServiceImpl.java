package pl.krasnowski.DigitalKitchen.services.user;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krasnowski.DigitalKitchen.model.DAO.RoleDAO;
import pl.krasnowski.DigitalKitchen.model.DAO.UserDAO;
import pl.krasnowski.DigitalKitchen.model.DTO.UserDTO;
import pl.krasnowski.DigitalKitchen.model.domain.user.*;
import pl.krasnowski.DigitalKitchen.model.exceptions.UserNotFoundException;
import pl.krasnowski.DigitalKitchen.services.SocialService;
import pl.krasnowski.DigitalKitchen.services.diet.DietManager;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.ApiRestrictionServiceImpl;

import javax.persistence.EntityManager;
import java.util.*;


@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    @NonNull
    private final UserDAO userDAO;

    @Autowired
    @NonNull
    private final RoleDAO roleDAO;

    @Autowired
    @NonNull
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @NonNull
    private final DietManager dietService;

    @Autowired
    @NonNull
    private final SocialService socialService;

    @Autowired
    EntityManager em;

    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, BCryptPasswordEncoder bCryptPasswordEncoder, @Lazy DietManager dietService, @Lazy SocialService socialService, @Lazy EntityManager em) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.dietService = dietService;
        this.socialService = socialService;
        this.em = em;
    }

    @Override
    public User initializeUser() {
        User nUser = new User();

        Role userRole = roleDAO.findByRoleName("USER");
        nUser.setRoles(new HashSet<>(Arrays.asList(userRole)));

        nUser.setApiRestrictions(ApiRestrictionServiceImpl.buildAPIRestrictions());
        nUser.setLanguage(LocaleContextHolder.getLocale().getLanguage());
        nUser.setBodyType(BodyType.MESOMORPH);
        nUser.setHeight(180);
        nUser.setWeight(80);
        nUser.setSex(Sex.MALE);
        nUser.setCurrentSilhouette(Silhouette.NORMAL);
        nUser.setDesiredSilhouette(Silhouette.STRONGMAN);
        nUser.setHistoryDays(new HashMap<>());

        log.debug("Successfully initialized user:{}", nUser);
        return nUser;
    }

    @Override
    public void createUser(User inpUser) {

        User nUser = initializeUser();
        nUser.setUsername(inpUser.getUsername());
        nUser.setVisibleName(inpUser.getVisibleName());
        nUser.setEmail(inpUser.getEmail());
        nUser.setPassword(bCryptPasswordEncoder.encode(inpUser.getPassword()));
        nUser.setActive(1); //TODO: Move to accounts activation service
        nUser.addMessage(socialService.createWelcomeMessage(
                "Dear " + nUser.getVisibleName() + ", \nwelcome to Digital Kitchen!")); // TODO: Internationalize welcome message

        // TODO: Diets are not implemented yet, but if - should be initialized and added to newly created user.
        //    nUser.setDiets(dietService.initializeDiets(nUser));
        //      nUser.setCurrentDiet(nUser.getDiets().get(0));

        nUser = userDAO.save(nUser);
        log.debug("Created new user: username:{}, email:{}", nUser.getUsername(), nUser.getEmail());
    }


    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmailIgnoreCase(email);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public List<User> findByUsernameOrVisibleName(String usernameOrVisibleName) {
        return userDAO.findByUsernameOrVisibleNameContainingIgnoreCase(usernameOrVisibleName, usernameOrVisibleName);
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByUsername(auth.getName());
    }


    @Override
    public void updateUser(UserDTO userDTO) {
        userDAO.updateUserDTO(userDTO);
        log.debug("Updated user to database. User ID:{}", userDTO.getUserID());
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
        log.debug("Updated user to database. User ID:{}", user.getUserID());
    }

    @Override
    public User authenticateUser(String nickOrEmail, String password) {
        return null;
    }

    @Override
    public void deleteUser(long ID) {

        Optional<User> user = userDAO.findById(ID);
        if (user.isPresent())
            userDAO.delete(user.get());
        else
            throw new UserNotFoundException(ID);
    }

    @Override
    public void updateRoles(Set<Role> roles) {
        getCurrentUser().setRoles(roles);
    }

    @Override
    public Set<Role> getUserRoles() {
        return getCurrentUser().getRoles();
    }

    @Override
    public void activateUser() {
        getCurrentUser().setActive(1);
        log.info("User:{} activated.", getCurrentUser().getUsername());
    }

}