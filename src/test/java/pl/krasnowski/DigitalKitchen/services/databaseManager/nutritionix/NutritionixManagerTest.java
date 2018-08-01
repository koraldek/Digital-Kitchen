package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.krasnowski.DigitalKitchen.config.AppConfig;
import pl.krasnowski.DigitalKitchen.config.DeveloperBeansConfig;
import pl.krasnowski.DigitalKitchen.model.domains.ApiRestriction;
import pl.krasnowski.DigitalKitchen.services.TestConfig;
import pl.krasnowski.DigitalKitchen.services.databaseManager.DatabaseManager;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, DeveloperBeansConfig.class, TestConfig.class})
class NutritionixManagerTest {

    @Autowired
    private DatabaseManager dbManager;

    @Autowired
    @Qualifier("apiRestrictionNutritionix")
    private Set<ApiRestriction> apiRestrictions;

    @Autowired
    @Qualifier("nutritionixKeys")
    private Map<String, String> nXkeys;

    @Autowired
    @Qualifier("nxSampleID")
    private String nXSampleID;

    @Autowired
    @Qualifier("sampleFoodName")
    private String sampleFoodName;

    @Test
    @DisplayName("Check behavior when external database not responding. IMPORTANT: When executing test disable internet connection.")
    void noConnectionWithExternalDb() {
        assertAll(
                () -> assertNull(dbManager.getFoodByID(nXSampleID, NutritionixManager.NUTRITIONIX_DB_NAME, nXkeys)),
                () -> assertNull(dbManager.getFoodList(sampleFoodName, nXkeys, apiRestrictions)),
                () -> assertNull(dbManager.getAutocompleteFoodList(sampleFoodName, nXkeys, apiRestrictions)
                ));
    }

    @Test
    @DisplayName("Test behavior when attached wrong API keys to request. In this case, temporary block access to keys file.")
    void AccessToDatabaseDenied() {
        assertAll(
                () -> assertNull(dbManager.getFoodByID(nXSampleID, NutritionixManager.NUTRITIONIX_DB_NAME, nXkeys)),
                () -> assertNull(dbManager.getFoodList(sampleFoodName, nXkeys, apiRestrictions)),
                () -> assertNull(dbManager.getAutocompleteFoodList(sampleFoodName, nXkeys, apiRestrictions)
                ));
    }

    @Test
    @DisplayName("Test behavior when passed wrong input keyword/ID.")
    void ResourceNotFoundTest() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> dbManager.getFoodByID("This is not valid ID", NutritionixManager.NUTRITIONIX_DB_NAME, nXkeys)),
                () -> assertEquals(dbManager.getFoodList("This is not valid keyword", nXkeys, apiRestrictions).size(), 0),
                () -> assertEquals(dbManager.getAutocompleteFoodList("This is not valid keyword", nXkeys, apiRestrictions).size(), 0)
        );
    }

    @Test
    @DisplayName("Test behavior when passed empty input keyword/ID.")
    void emptyInputString() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> dbManager.getFoodByID("", NutritionixManager.NUTRITIONIX_DB_NAME, nXkeys)),
                () -> assertThrows(IllegalArgumentException.class, () -> dbManager.getFoodList("", nXkeys, apiRestrictions).size()),
                () -> assertThrows(IllegalArgumentException.class, () -> dbManager.getAutocompleteFoodList("", nXkeys, apiRestrictions).size())
        );
    }
}