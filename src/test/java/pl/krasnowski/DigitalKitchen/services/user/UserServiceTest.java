//package pl.krasnowski.DigitalKitchen.services.user;
//
//import org.apache.commons.lang3.StringUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.ContextConfiguration;
//import pl.krasnowski.DigitalKitchen.model.DAO.RoleDAO;
//import pl.krasnowski.DigitalKitchen.model.DAO.UserDAO;
//import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
//import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
//import pl.krasnowski.DigitalKitchen.model.domain.social.Folder;
//import pl.krasnowski.DigitalKitchen.model.domain.social.Message;
//import pl.krasnowski.DigitalKitchen.model.domain.user.Intolerance;
//import pl.krasnowski.DigitalKitchen.model.domain.user.Role;
//import pl.krasnowski.DigitalKitchen.model.domain.user.User;
//import pl.krasnowski.DigitalKitchen.services.SocialService;
//import pl.krasnowski.DigitalKitchen.services.TestConfig;
//import pl.krasnowski.DigitalKitchen.services.diet.DietManager;
//import pl.krasnowski.DigitalKitchen.services.physicalActivity.PhysicalActivityService;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//@ContextConfiguration(classes = {TestConfig.class})
//class UserServiceTest {
//    //<editor-fold> dsc="UserService mocked fields"
//    @Mock
//    private UserDAO userDAOMock;
//    @Mock
//    private RoleDAO roleDAO;
//    @Mock
//    private PhysicalActivityService physicalActivityService;
//    @Mock
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Mock
//    private DietManager dietService;
//    @Mock
//    private SocialService socialService;
//
////</editor-fold>
//
//    @InjectMocks
//    private UserServiceImpl userServiceMock;
//
//    private User usr_Andrzej85;
//
//    private ArrayList<User> usersList;
//
//    @Autowired
//    @Qualifier("adminUser")
//    private User adminUser;
//
//
//    private void initData() {
//        Set<Intolerance> intoleranceSet = new HashSet<>();
//        intoleranceSet.add(Intolerance.GLUTEN);
//        intoleranceSet.add(Intolerance.DAIRY);
//        intoleranceSet.add(Intolerance.SESAME);
//
//
//        usr_Andrzej85 = new User();
//        usr_Andrzej85.setEmail("myEmail@gmail.com");
//        usr_Andrzej85.setVisibleName("Andrzej");
//        usr_Andrzej85.setUsername("Andrzej85");
//        usr_Andrzej85.setPassword("myPony66");
//        usr_Andrzej85.setIntolerances(intoleranceSet);
//
//        User user2 = new User();
//        user2.setEmail("myEmail2@gmail.com");
//        user2.setVisibleName("Andrzej");
//        user2.setUsername("Andrzej77");
//        user2.setPassword("fan50");
//        user2.setIntolerances(new HashSet<>());
//
//        User wrongUser = new User();
//        wrongUser.setEmail("myEmail@gmail.com"); // have already used email
//        wrongUser.setVisibleName("Andrzej    "); // whitespaces should be trimmed
//        wrongUser.setUsername("Andrzej85"); // have already used username
//        wrongUser.setPassword("fan"); // input password is too short
//
//        usersList = new ArrayList<>();
//        usersList.add(usr_Andrzej85);
//        usersList.add(user2);
//    }
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        initData();
//    }
//
//    @Test
//    @DisplayName("SaveNewUser: Input user fields and possible duplicate are validated in view and controller.")
//    void initializeUserTest() {
//        ArrayList<Diet> diets = new ArrayList<>();
//        Diet diet = new Diet();
//        diets.add(diet);
//
//        String encodedPass = "encoded_pass";
//        Role role = new Role();
//        role.setRoleName("ADMIN");
//
//
//        DietDay foodAndWorkoutDiary = new DietDay();
//        Set<DietDay> dietDays = new HashSet<>();
//        dietDays.add(foodAndWorkoutDiary);
//
//
//        Message nMessage = new Message();
//        nMessage.setContent("Welcome to Digital Kitchen!");
//        nMessage.setFolder(Folder.UNREAD);
//        nMessage.setSender(adminUser);
//
//        when(roleDAO.findByRoleName("USER")).thenReturn(role);
//        when(bCryptPasswordEncoder.encode(any())).thenReturn(encodedPass);
//        when(dietService.initializeDietDay()).thenReturn(foodAndWorkoutDiary);
//        when(dietService.initializeDietDays()).thenReturn(dietDays);
//        when(dietService.initializeDiet(adminUser)).thenReturn(diet);
//        when(dietService.initializeDiets(adminUser)).thenReturn(diets);
//        when(socialService.createWelcomeMessage(any())).thenReturn(nMessage);
//
//
//        User savedUser = userServiceMock.createUser(usr_Andrzej85);
//        assertAll(
//                () -> assertEquals(encodedPass, savedUser.getPassword()),
//                () -> assertTrue(savedUser.getApiRestrictions().size() >= 2), // ApiRestrictions should contain at least two API restrictions.
//                () -> assertEquals(1, savedUser.getDiets().size()), // check if dependent service creates basic diet.
//                () -> assertTrue(savedUser.getRoles().size() > 0), // User should have at least one role: 'USER'
//                () -> assertFalse(StringUtils.isEmpty(savedUser.getMessages().iterator().next().getContent()))//dependent service creates welcome msg
//        );
//    }
//
//
//    @Test
//    void findUserByEmailTest() {
//        String nonExistingEmail = "nonExistingEmail@gmail.com";
//        String existingEmail = usr_Andrzej85.getEmail(); // "MYEMAIL@gmail.com"
//
//        assertAll(
//                () -> {
//                    when(userDAOMock.findByEmailIgnoreCase(nonExistingEmail)).thenReturn(null);
//                    assertNull(userServiceMock.findByEmail(nonExistingEmail));
//                },
//                () -> {
//                    when(userDAOMock.findByEmailIgnoreCase(existingEmail)).thenReturn(usr_Andrzej85);
//                    assertEquals(usr_Andrzej85.getEmail(), userServiceMock.findByEmail(existingEmail).getEmail());
//                });
//    }
//
//    @Test
//    void findByUsernameTest() {
//        when(userDAOMock.findByUsername("Andrzej85")).thenReturn(usr_Andrzej85);
//        assertEquals(usr_Andrzej85, userServiceMock.findByUsername("Andrzej85"));
//    }
//
//    @Test
//    void findByNickOrNameVisibleContainingTest() {
//        String partOfUsername = "Andr";
//        when(userDAOMock.findByUsernameOrVisibleNameContainingIgnoreCase(partOfUsername, partOfUsername)).thenReturn(usersList);
//
//        List<User> resultList = userServiceMock.findByUsernameOrVisibleName(partOfUsername);
//
//        assertEquals(resultList.size(), 2);
//        resultList.forEach(user -> assertTrue(
//                user.getVisibleName().contains(partOfUsername) || user.getUsername().contains(partOfUsername)));
//    }
//
//
//    @Test
//    @Disabled("Disabled functionality. It may be implemented in further releases.")
//    void authenticateUserTest() {
////        String goodPass = "myPony66";
////        String wrongPass = "wrong";
////        String username = "Andrzej85";
//
////        assertAll(
////                () -> {
////                    when(userDAOMock.authenticateUser(username, goodPass)).thenReturn(usr_Andrzej85);
////                    assertEquals(usr_Andrzej85, userServiceMock.authenticateUser(username, goodPass));
////                },
////                () -> {
////                    when(userDAOMock.authenticateUser(username, wrongPass)).thenReturn(null);
////                    assertEquals(null, userServiceMock.authenticateUser(username, wrongPass));
////                }
////        );
//    }
//
////    @Test //TODO: move to DietUtilities service
////    @DisplayName("isAllergicToTest: Used spied service, because mocked " +
////            "object can't hold state of inner field (in this case method needs: user.getIntolerances().")
////    void isAllergicToTest() throws NoSuchFieldException {
////        UserServiceImpl spyOnUserService = Mockito.spy(new UserServiceImpl());
////        FieldSetter.setField(spyOnUserService, UserServiceImpl.class.getDeclaredField("user"), usr_Andrzej85);
////
////        assertAll(
////                () -> assertTrue(spyOnUserService.isAllergicTo(Intolerance.GLUTEN)),
////                () -> assertFalse(spyOnUserService.isAllergicTo(Intolerance.SEAFOOD)));
////    }
//
//}