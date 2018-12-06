package pl.krasnowski.DigitalKitchen.services.user;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krasnowski.DigitalKitchen.model.DAO.RoleDAO;
import pl.krasnowski.DigitalKitchen.model.DAO.UserDAO;
import pl.krasnowski.DigitalKitchen.model.domain.user.*;
import pl.krasnowski.DigitalKitchen.services.SocialService;
import pl.krasnowski.DigitalKitchen.services.diet.DietManager;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.ApiRestrictionServiceImpl;

import java.util.*;


@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

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

    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO,@Lazy BCryptPasswordEncoder bCryptPasswordEncoder, @Lazy DietManager dietService, @Lazy SocialService socialService) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.dietService = dietService;
        this.socialService = socialService;
    }


    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        User user = userDAO.findByUsername(username);
        List<GrantedAuthority> authorities =
                buildUserAuthority(user.getRoles());

        return buildUserForAuthentication(user, authorities);
    }

    // Converts pl.krasnowski.DigitalKitchen.model.domain.user.User user to
    // org.springframework.security.core.userdetails.User
    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user,
                                            List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                (user.getActive() != 0), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> setAuths = new HashSet<>();

        // Build user's authorities
        for (Role userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRoleName()));
        }
        return new ArrayList<>(setAuths);
    }

    @Override
    public User initializeUser() {
        User nUser = new User();

        Role userRole = roleDAO.findByRoleName("USER");
        nUser.setRoles(new HashSet<>(Arrays.asList(userRole)));

        nUser.setApiRestrictions(ApiRestrictionServiceImpl.buildAPIRestrictions());
        nUser.setLanguage(LocaleContextHolder.getLocale().getLanguage());

        // TODO: Initialize physical activity
        nUser.setBodyType(BodyType.Mesomorph);
        nUser.setHeight(180);
        nUser.setWeight(80);
        nUser.setSex(Sex.male);
        nUser.setCurrentSilhouette(Silhouette.NORMAL);
        nUser.setDesiredSilhouette(Silhouette.STRONGMAN);

        log.debug("Successfully initialized user:{}", nUser);
        return nUser;
    }

    @Override
    public User preCreateUser(User inpUser) {

        User nUser = initializeUser();
        nUser.setUsername(inpUser.getUsername());
        nUser.setVisibleName(inpUser.getVisibleName());
        nUser.setEmail(inpUser.getEmail());
        nUser.setPassword(bCryptPasswordEncoder.encode(inpUser.getPassword()));
        nUser.setActive(1); //TODO: Move to accounts activation service
        userDAO.save(nUser);
        log.debug("Created new user: username:{}, email:{}", nUser.getUsername(), nUser.getEmail());
        return nUser;
    }

    @Override
    public User postCreateUser(User usr) {
        usr.getMessages().add(socialService.createWelcomeMessage("Dear " + usr.getVisibleName() + ", \nwelcome to Digital Kitchen!"));
        usr.setDiets(dietService.initializeDiets());
        usr.setCurrentDiet(usr.getDiets().get(0));
        userDAO.updateUser(usr);

        return usr;
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
        log.debug("Updated user profile to database. User ID:{}", userDTO.getUserID());
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
        log.debug("Updated user profile to database. User ID:{}", user.getUserID());
    }


    @Override
    public User authenticateUser(String nickOrEmail, String password) {
        return null;
    }

    @Override
    public void deleteByID(long id) {
        User u = userDAO.findById(id).get();
        userDAO.delete(u);
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