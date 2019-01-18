package pl.krasnowski.DigitalKitchen.services.foodDbManager;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.edemam.EdemamManager;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.local.LocalDatabaseManager;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix.NutritionixManager;

import static pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager.EDEMAM_DB_NAME;
import static pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager.NUTRITIONIX_DB_NAME;

/**
 * Basic implementation include only local and nx database.
 */
@Primary
@Service
@Slf4j
@AllArgsConstructor
public class StrategyProfileBasic implements StrategyProfile {

    @Autowired
    private final NutritionixManager nutritionixManager;
    @Autowired
    private final EdemamManager edemamManager;
    @Autowired
    private final LocalDatabaseManager localDatabaseManager;
    @Autowired
    private final ApiRestrictionService apiRestrictionService;

    @Override
    public FoodDispatcher getDbFoodManager(String dbName, int queryTypes[]) {
        FoodDispatcher fd;

        switch (dbName) {
            case DatabaseManager.LOCAL_DB_NAME:
                fd = localDatabaseManager;
                break;
            case DatabaseManager.NUTRITIONIX_DB_NAME:
                fd = nutritionixManager;
                break;
            case DatabaseManager.EDEMAM_DB_NAME:
                fd = edemamManager;
                break;
            default:
                throw new IllegalArgumentException("Unknown database name:" + dbName);
        }
        apiRestrictionService.increment(fd.getDbName(), Food.class, queryTypes);

        if (log.isDebugEnabled()) {
            log.debug("Choosed database:{}. Current counter status:", fd.getDbName());
            for (int qt : queryTypes) {
                log.debug("{}", apiRestrictionService.getCurrentValues(dbName, Food.class, qt));
            }
        }

        return fd;
    }

    @Override
    public FoodDispatcher getFoodDispatcher(int[] queryTypes) {
        FoodDispatcher fd;

        if (apiRestrictionService.isRestrictionReached(NUTRITIONIX_DB_NAME, Food.class, queryTypes))
            fd = localDatabaseManager;
        else {
            fd = nutritionixManager;
            apiRestrictionService.increment(fd.getDbName(), Food.class, queryTypes);
        }

        if (log.isDebugEnabled()) {
            log.debug("Choosed database:{}. Current counter status:", fd.getDbName());
            for (int qt : queryTypes) {
                log.debug("{}", apiRestrictionService.getCurrentValues(fd.getDbName(), Food.class, qt));
            }
        }

        return fd;
    }

    @Override
    public RecipesDispatcher getRecipesDispatcher(int[] queryTypes) {
        RecipesDispatcher rd;

        if (apiRestrictionService.isRestrictionReached(EDEMAM_DB_NAME, Recipe.class, queryTypes))
            rd = edemamManager;
        else {
            rd = localDatabaseManager;
            apiRestrictionService.increment(rd.getDbName(), Recipe.class, queryTypes);
        }

        if (log.isDebugEnabled()) {
            log.debug("Choosed database:{}. Current counter status:", rd.getDbName());
            for (int qt : queryTypes) {
                log.debug("{}", apiRestrictionService.getCurrentValues(rd.getDbName(), Recipe.class, qt));
            }
        }

        return rd;
    }

    @Override
    public RecipesDispatcher getDbRecipeManager(String dbName, int[] queryType) {
        RecipesDispatcher rd;

        switch (dbName) {
            case DatabaseManager.LOCAL_DB_NAME: {
                rd = localDatabaseManager;
                break;
            }
            case DatabaseManager.EDEMAM_DB_NAME: {
                rd = edemamManager;
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown database name. dbName argument:" + dbName);
        }

        if (log.isDebugEnabled()) {
            log.debug("Choosed database:{}. Current counter status:", rd.getDbName());
            for (int qt : queryType) {
                log.debug("{}", apiRestrictionService.getCurrentValues(rd.getDbName(), Food.class, qt));
            }
        }

        return rd;
    }
}
