package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.user.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domain.user.RestrictionPiece;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.TestConfig;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.ApiRestrictionService;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.ApiRestrictionServiceImpl;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class ApiRestrictionServiceTest {

    @InjectMocks
    private ApiRestrictionServiceImpl ars;

    @Mock
    private User user;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        initData();
    }

    private void initData() {
        Set<ApiRestriction> restrictions = new HashSet<>();
        restrictions.add(ApiRestrictionService.buildEdemamApiRestriction());
        restrictions.add(ApiRestrictionService.buildNutritionixApiRestriction());

        when(user.getApiRestrictions()).thenReturn(restrictions);
    }

    @Test
    void createApiRestriction() {
        Set<RestrictionPiece> rpSetNx = ars.getConcreteApiRestriction(DatabaseManager.EDEMAM_DB_NAME);
        Set<RestrictionPiece> rpSetEdem = ars.getConcreteApiRestriction(DatabaseManager.NUTRITIONIX_DB_NAME);

        assertAll(
                () -> rpSetNx.forEach(rp -> assertEquals(rp.getCurrent(), 0)),
                () -> rpSetEdem.forEach(rp -> assertEquals(rp.getCurrent(), 0))
        );
    }

    @Test
    void IncrementTest() {

        RestrictionPiece rp = ars.getRestrictionPiece(DatabaseManager.NUTRITIONIX_DB_NAME, ApiRestrictionService.DAY, pl.krasnowski.DigitalKitchen.model.domain.food.Food.class, ApiRestrictionService.ALL_QUERY_TYPES);
        ars.increment(DatabaseManager.NUTRITIONIX_DB_NAME, Food.class, new int[]{ApiRestrictionService.ALL_QUERY_TYPES});
        ars.increment(DatabaseManager.NUTRITIONIX_DB_NAME, Food.class, new int[]{ApiRestrictionService.ALL_QUERY_TYPES});

        assertEquals(2, rp.getCurrent());
    }
}