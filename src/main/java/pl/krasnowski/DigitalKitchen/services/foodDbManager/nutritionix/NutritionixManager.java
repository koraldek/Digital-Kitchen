package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.food.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.FoodDispatcher;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Repository
@Slf4j
@NoArgsConstructor
public class NutritionixManager implements FoodDispatcher {

    private static final String BASE_URL = "https://trackapi.nutritionix.com/v2/";
    static final String DEFAULT_EXTERNAL_PHOTO_URL = "https://d2eawub7utcl6.cloudfront.net/images/nix-apple-grey.png";

    @Autowired
    private UserService uService;

    @Autowired
    private Map<String, String> nutritionixKeys;

    public NutritionixManager(UserService uService, Map<String, String> nutritionixKeys) {
        this.uService = uService;
        this.nutritionixKeys = nutritionixKeys;
    }

    /**
     * Get detailed information about BRANDED diet
     *
     * @param foodID ID of branded food
     * @return parsed Food object
     */
    @Override
    public Food getFoodByID(String foodID) {
        log.debug("Running with parameters: foodID:{}", foodID);
        HttpResponse<SearchByCodeResult> searchByCodeHttpResponse;

        Map<String, String> paramMap = new HashMap<>(nutritionixKeys);
        paramMap.put(DatabaseManager.NX_USER_KEY_ID, String.valueOf(uService.getCurrentUser().getNutritionix_id()));
        try {
            searchByCodeHttpResponse = Unirest.get(BASE_URL + "search/item?nix_item_id={nix_item_id}")
                    .headers(paramMap)
                    .routeParam("nix_item_id", foodID)
                    .asObject(SearchByCodeResult.class);
            log.debug("Request response status from Nutritionix:{}", searchByCodeHttpResponse.getStatus());
        } catch (UnirestException e) {
            e.printStackTrace();
            if (e.getCause() instanceof java.net.UnknownHostException) {
                log.error("Can't connect to database. Check connection.", e);
                return null;
            }
            if (e.getCause() instanceof java.lang.RuntimeException) {
                if (e.getMessage().contains(DatabaseManager.unauthorizedExceptionMessage))
                    log.error("Unauthorized call to API. Perhaps missing property file with keys.");
                else if (e.getMessage().contains(DatabaseManager.wrongIDorEmptyResultException))
                    throw new IllegalArgumentException("Passed wrong ID.");
                else if (e.getMessage().contains(DatabaseManager.emptyQueryExceptionMessage)) {
                    log.debug("Passed empty ID parameter.");
                    throw new IllegalArgumentException("Passed empty ID.");
                }
            }
            return null;
        }
        return NutritionixParser.parseSearchByKeywordOrID(searchByCodeHttpResponse.getBody()).get(0);
    }

    /**
     * Get detailed information about common type diet, or log diet using natural language method
     *
     * @param keyword keyword of diet
     * @return parsed Food object
     */
    public ArrayList<Food> getFoodByKeyword(String keyword) {
        log.debug("Running with parameter:keyword:{}", keyword);
        HttpResponse<SearchByCodeResult> searchByCodeHttpResponse;

        Map<String, String> paramMap = new HashMap<>(nutritionixKeys);
        paramMap.put(DatabaseManager.NX_USER_KEY_ID, String.valueOf(uService.getCurrentUser().getNutritionix_id()));
        try {
            searchByCodeHttpResponse = Unirest.post(BASE_URL + "natural/nutrients/")
                    .headers(paramMap)
                    .body(new JSONObject().put("query", keyword))
                    .asObject(SearchByCodeResult.class);
        } catch (UnirestException e) {
            e.printStackTrace();
            if (e.getCause() instanceof java.net.UnknownHostException)
                log.error("Can't connect to database. Check connection.", e);
            else if (e.getCause() instanceof java.lang.RuntimeException) {
                if (e.getMessage().contains(DatabaseManager.unauthorizedExceptionMessage))
                    log.error(DatabaseManager.unauthorizedExceptionMessage); // "Unauthorized call to API. Perhaps missing property file with keys."
                else if (e.getMessage().contains(DatabaseManager.notMatchingResultsException)) {
                    log.debug(DatabaseManager.notMatchingResultsException);
                    throw new IllegalArgumentException(DatabaseManager.notMatchingResultsException);
                } else if (e.getMessage().contains(DatabaseManager.emptyQueryExceptionMessage)) {
                    log.debug(DatabaseManager.emptyQueryExceptionMessage);
                    throw new IllegalArgumentException(DatabaseManager.emptyQueryExceptionMessage);
                }
            }
            return null;
        }
        log.debug("Request response status from Nutritionix:{}", searchByCodeHttpResponse.getStatus());
        return NutritionixParser.parseSearchByKeywordOrID(searchByCodeHttpResponse.getBody());
    }


    public ArrayList<Food> getFoodList(String keyword, Map<String, String> paramMap) {
        log.debug("Running with parameters: keyword:{}, paramMap:{}", keyword, paramMap);
        HttpResponse<InstantSearchDetailedResult> instantSearchDetailedHttpResponse;
        if (paramMap == null)
            paramMap = new HashMap<>();
        paramMap.putAll(nutritionixKeys);
        paramMap.put(DatabaseManager.NX_USER_KEY_ID, String.valueOf(uService.getCurrentUser().getNutritionix_id()));
        try {
            instantSearchDetailedHttpResponse = Unirest.get(BASE_URL + "search/instant?query={query}&detailed={detailed}")
                    .headers(paramMap)
                    .routeParam("query", keyword)
                    .routeParam("detailed", "true")
                    .asObject(InstantSearchDetailedResult.class);
        } catch (UnirestException e) {
            if (e.getCause() instanceof java.lang.RuntimeException) {
                if (e.getMessage().contains(DatabaseManager.emptyQueryExceptionMessage)) {
                    log.debug(DatabaseManager.emptyQueryExceptionMessage);
                    throw new IllegalArgumentException(DatabaseManager.emptyQueryExceptionMessage);
                } else if (e.getMessage().contains(DatabaseManager.unauthorizedExceptionMessage))
                    log.error(DatabaseManager.unauthorizedExceptionMessage);
            } else if (e.getCause() instanceof java.net.UnknownHostException)
                log.error("Can't connect to database. Check connection.", e);
            return null;
        }
        log.debug("Request response status from Nutritionix:{}", instantSearchDetailedHttpResponse.getStatus());
        return NutritionixParser.parseDetailedList(instantSearchDetailedHttpResponse.getBody());
    }


    @Override
    public ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap) {
        log.debug("Running with parameters: keyword:{}, paramMap:{}", keyword, paramMap);
        HttpResponse<InstantSearchResult> instantSearchResultHttpResponse;
        if (paramMap == null)
            paramMap = new HashMap<>();
        paramMap.put(DatabaseManager.NX_USER_KEY_ID, String.valueOf(uService.getCurrentUser().getNutritionix_id()));
        paramMap.putAll(nutritionixKeys);

        try {
            instantSearchResultHttpResponse = Unirest.get(BASE_URL + "search/instant?query={query}&detailed={detailed}")
                    .headers(paramMap)
                    .routeParam("query", keyword)
                    .routeParam("detailed", "false")
                    .asObject(InstantSearchResult.class);
        } catch (UnirestException e) {
            if (e.getCause() instanceof java.lang.RuntimeException) {
                if (e.getMessage().contains(DatabaseManager.emptyQueryExceptionMessage)) {
                    log.debug(DatabaseManager.emptyQueryExceptionMessage);
                    throw new IllegalArgumentException(DatabaseManager.emptyQueryExceptionMessage);
                } else if (e.getMessage().contains(DatabaseManager.unauthorizedExceptionMessage))
                    log.error(DatabaseManager.unauthorizedExceptionMessage);
                else {
                    log.error("Unrecognized runtime exception:", e);
                }
            } else if (e.getCause() instanceof java.net.UnknownHostException)
                log.error("Can't connect to database. Check connection.", e);

            return null;
        }
        log.debug("Response from server:{}", instantSearchResultHttpResponse.getBody().toString());

        return NutritionixParser.parseInstantList(instantSearchResultHttpResponse.getBody());
    }

    /**
     * Will be implemented in future versions.
     *
     * @param barcode
     */
    public Food getFoodByBarcode(int barcode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDbName() {
        return DatabaseManager.NUTRITIONIX_DB_NAME;
    }
}