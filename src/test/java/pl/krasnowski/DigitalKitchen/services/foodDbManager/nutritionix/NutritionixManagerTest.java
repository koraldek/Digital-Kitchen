package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.DisabledIf;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.TestConfig;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager;
import org.junit.jupiter.api.Disabled;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class NutritionixManagerTest {

    @Autowired
    private HashMap<String, String> nutritionixKeys;

   private NutritionixManager nutritionixManager;

    @Mock
    User user;

    @Mock
    UserService uService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(uService.getCurrentUser()).thenReturn(user);
        when(user.getNutritionix_id()).thenReturn(0);

        nutritionixManager = new NutritionixManager(uService,nutritionixKeys);
    }

    @Autowired
    @Qualifier("nxSampleID")
    private String nXSampleID;

    @Autowired
    @Qualifier("sampleFoodName")
    private String sampleFoodName;

    @Test
    @DisplayName("Check behavior when external database not responding. IMPORTANT: When executing test disable internet connection.")
    @Disabled("Needs to be executed with disabled connection to Nx server.")
    void noConnectionWithExternalDb() {
        assertAll(
                () -> assertNull(nutritionixManager.getFoodByID(nXSampleID)),
                () -> assertNull(nutritionixManager.getFoodList(sampleFoodName,null)),
                () -> assertNull(nutritionixManager.getAutocompleteFoodList(sampleFoodName,null)
                ));
    }

    @Test
    @DisplayName("Test behavior when attached wrong API keys to request. In this case, temporary block access to keys file.")
    @Disabled("Test require block access to file")
    void AccessToDatabaseDenied() {
        assertAll(
                () -> assertNull(nutritionixManager.getFoodByID(nXSampleID)),
                () -> assertNull(nutritionixManager.getFoodList(sampleFoodName,null)),
                () -> assertNull(nutritionixManager.getAutocompleteFoodList(sampleFoodName,null)
                ));
    }

    @Test
    @DisplayName("Test behavior when passed wrong input keyword/ID.")
    void ResourceNotFoundTest() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> nutritionixManager.getFoodByID("This is not valid ID")),
                () -> assertEquals(nutritionixManager.getFoodList("This is not valid keyword",null).size(), 0),
                () -> assertEquals(nutritionixManager.getAutocompleteFoodList("This is not valid keyword",null).size(), 0)
        );
    }

    @Test
    @DisplayName("Test behavior when passed empty input keyword/ID.")
    void emptyInputString() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> nutritionixManager.getFoodByID("")),
                () -> assertThrows(IllegalArgumentException.class, () -> nutritionixManager.getFoodList("",null)),
                () -> assertThrows(IllegalArgumentException.class, () -> nutritionixManager.getAutocompleteFoodList("",null))
        );
    }
}