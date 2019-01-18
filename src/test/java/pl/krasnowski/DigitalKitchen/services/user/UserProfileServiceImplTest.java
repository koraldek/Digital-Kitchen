//package pl.krasnowski.DigitalKitchen.services.user;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import pl.krasnowski.DigitalKitchen.model.DAO.UserDAO;
//import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
//import pl.krasnowski.DigitalKitchen.model.domain.user.*;
//import pl.krasnowski.DigitalKitchen.model.events.NotificationDestination;
//import pl.krasnowski.DigitalKitchen.model.events.SystemEventType;
//import pl.krasnowski.DigitalKitchen.services.diet.DietManager;
//
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Set;
//
//import static junit.framework.TestCase.*;
//import static org.mockito.Mockito.when;
//
//class UserProfileServiceImplTest {
//
//    private UserProfileServiceImpl profileService;
//
//    @Mock
//    UserService uService;
//
//    @Mock
//    BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Mock
//    UserDAO userDAO;
//
//    @Mock
//    DietManager dietManager;
//
//    private User usr;
//    private final String encodedOldPass = "encoded_pass";
//
//    @BeforeEach
//    void setUp() {
//
//        usr = new User();
//        Set<Intolerance> intol = new HashSet<>();
//        intol.add(Intolerance.SESAME);
//        usr.setNotificationSetup(new HashSet<>());
//        usr.setHistoryDays(new HashMap<>());
//        usr.setIntolerances(intol);
//        usr.setPassword(encodedOldPass);
//
//        MockitoAnnotations.initMocks(this);
//        profileService = new UserProfileServiceImpl(bCryptPasswordEncoder, userDAO, uService, dietManager);
//
//        when(uService.getCurrentUser()).thenReturn(usr);
//
//        DietDay dd = new DietDay();
//        dd.setDayDate(Timestamp.valueOf((LocalDate.now().atStartOfDay())));
//        when(dietManager.initializeDietDay()).thenReturn(dd);
//    }
//
//    @Test
//    void addIntolerance() {
//        profileService.addIntolerance(Intolerance.GLUTEN);
//        assertTrue(uService.getCurrentUser().getIntolerances().contains(Intolerance.GLUTEN));
//    }
//
//    @Test
//    void removeIntolerance() {
//        profileService.removeIntolerance(Intolerance.SESAME);
//        assertFalse(uService.getCurrentUser().getIntolerances().contains(Intolerance.SESAME));
//    }
//
//    @Test
//    void updateNotifications() {
//        NotificationSetup ns = new NotificationSetup();
//        ns.setNotificationSource(SystemEventType.SystemEvent);
//        Set<NotificationDestination> nd = new HashSet<>();
//        nd.add(NotificationDestination.email);
//        nd.add(NotificationDestination.message);
//        ns.setNotificationDestination(nd);
//
//        profileService.updateNotifications(ns);
//
//        assertTrue(uService.getCurrentUser().getNotificationSetup().contains(ns));
//    }
//
//    @Test
//    void changePassword() {
//        String newPass = "newPass";
//        String encodedNewPass = "encNewPass";
//        String oldPass = "pazz";
//        when(bCryptPasswordEncoder.encode(newPass)).thenReturn(encodedNewPass);
//        when(bCryptPasswordEncoder.matches(oldPass, encodedOldPass)).thenReturn(true);
//
//        assertTrue(profileService.changePassword(oldPass, newPass));
//        assertEquals(usr.getPassword(), encodedNewPass);
//
//        assertFalse(profileService.changePassword("incorrect password", " new password"));
//    }
//
//    @Test
//    void addFoodPreferention() {
//        FoodPreferention newFoodPreferention = new FoodPreferention();
//
//        profileService.addFoodPreferention(newFoodPreferention);
//
//        assertTrue(uService.getCurrentUser().getFoodPreferentions().contains(newFoodPreferention));
//    }
//
//    @Test
//    void removeFoodPreferention() {
//        FoodPreferention fp1 = new FoodPreferention();
//        FoodPreferention fp2 = new FoodPreferention();
//        fp1.setFoodPreferentionID(1L);
//        fp2.setFoodPreferentionID(2L);
//
//        Set<FoodPreferention> setFP = new HashSet<>();
//        setFP.add(fp1);
//        setFP.add(fp2);
//
//        uService.getCurrentUser().setFoodPreferentions(setFP);
//        assertEquals(2, uService.getCurrentUser().getFoodPreferentions().size());
//
//
//        profileService.removeFoodPreferention(fp1);
//
//        assertEquals(1, uService.getCurrentUser().getFoodPreferentions().size());
//    }
//
//    @Test
//    void getCurrentHistoryDay() {
//        assertEquals(LocalDate.now(),
//                profileService.getCurrentHistoryDay().getFoodAndWorkoutDiary().getDayDate().toLocalDateTime().toLocalDate());
//    }
//
//    @Test
//    void getHistoryDay() {
//        HistoryDay newHD = profileService.getCurrentHistoryDay();
//        newHD.getFoodAndWorkoutDiary().setDayDate(Timestamp.valueOf(LocalDate.of(2018, 10, 25).atStartOfDay()));
//        profileService.addHistoryDay(newHD);
//        HistoryDay hd = profileService.getHistoryDay(LocalDate.of(2018, 10, 25));
//
//        assertEquals(hd.getFoodAndWorkoutDiary().getDayDate().toLocalDateTime().toLocalDate(),
//                LocalDate.of(2018, 10, 25));
//    }
//
//    @Test
//    void getDietHistory() {
//        //   uService.getCurrentUser().setHistoryDays();
//
//        //   assertEquals(3, profileService.getDietHistory().size());
//    }
//
//    @Test
//    void updateHistoryDay() {
//
//    }
//
//    @Test
//    void removeHistoryDay() {
//    }
//
//    @Test
//    void getSavedMeals() {
//    }
//
//    @Test
//    void getSavedRecipes() {
//    }
//
//    @Test
//    void getSavedWorkouts() {
//    }
//
//    @Test
//    void toDTO() {
//    }
//}