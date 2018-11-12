package pl.krasnowski.DigitalKitchen.services.foodDbManager;

import org.springframework.stereotype.Service;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.food.Recipe;
import pl.krasnowski.DigitalKitchen.model.domain.user.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domain.user.RestrictionPiece;

import java.util.HashSet;
import java.util.Set;

@Service
public interface ApiRestrictionService {
    int MINUTE = 1;
    int HOUR = 60;
    int DAY = 24 * HOUR;
    int MONTH = 30 * DAY;
    int ALL_QUERY_TYPES = 0;
    int AUTOCOMPLETE_QUERY_TYPE = 1;
    int BARCODE_QUERY_TYPE = 2;
    int FOOD_QUERY_TYPE = 3;
    int RECIPE_QUERY_TYPE = 4;


    static ApiRestriction buildNutritionixApiRestriction() {
        ApiRestriction nApiRestriction = new ApiRestriction();
        nApiRestriction.setDbName(DatabaseManager.NUTRITIONIX_DB_NAME);

        Set<RestrictionPiece> restrictionPieces = new HashSet<>();
        restrictionPieces.add(new RestrictionPiece(DAY, 500, Food.class, ALL_QUERY_TYPES));
        nApiRestriction.setRestrictionsMap(restrictionPieces);
        return nApiRestriction;
    }

    static ApiRestriction buildEdemamApiRestriction() {
        ApiRestriction nApiRestriction = new ApiRestriction();
        nApiRestriction.setDbName(DatabaseManager.EDEMAM_DB_NAME);

        Set<RestrictionPiece> restrictionPieces = new HashSet<>();
        restrictionPieces.add(new RestrictionPiece(MINUTE, 25, Food.class, FOOD_QUERY_TYPE));
        restrictionPieces.add(new RestrictionPiece(MINUTE, 25, Food.class, BARCODE_QUERY_TYPE));
        restrictionPieces.add(new RestrictionPiece(MINUTE, 25, Food.class, AUTOCOMPLETE_QUERY_TYPE));
        restrictionPieces.add(new RestrictionPiece(MINUTE, 5, Recipe.class, RECIPE_QUERY_TYPE));
        restrictionPieces.add(new RestrictionPiece(MONTH, 5000, Recipe.class, RECIPE_QUERY_TYPE));
        nApiRestriction.setRestrictionsMap(restrictionPieces);
        return nApiRestriction;
    }

    Set<RestrictionPiece> getConcreteApiRestriction(String dbName);


    void addRestrictionPiece(String dbName, int period, int max, Class dataType, int queryType);

    RestrictionPiece getRestrictionPiece(String dbName, int period, Class dataType, int queryType);

    void removeRestrictionPiece(String dbName, RestrictionPiece rp);


    void increment(String dbName, Class dataType, int[] queryType);

    void resetDbRestrictions(ApiRestriction ar);


    void resetAll(String dbName);

    int getCurrentValue(String dbName, int period, Class dataType, int queryType);

    int getMaxValue(String dbName, int period, Class dataType, int queryType);

    boolean isRestrictionReached(String dbName, Class dataType, int[] queryType);


    /**
     * Returns string with restriction values
     *
     * @return Current/Max/Period string of values.
     */
    String getCurrentValues(String dbName, Class dataType, int queryType);

}
