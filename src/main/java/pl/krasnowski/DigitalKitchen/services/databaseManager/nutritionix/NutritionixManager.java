package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.config.AppConfig;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;

import java.util.ArrayList;
import java.util.Map;


@Repository
public interface NutritionixManager {

    String NUTRITIONIX_DB_NAME = "Nutritionix";
    String BASE_URL = "https://trackapi.nutritionix.com/v2/";
    String DEFAULT_EXTERNAL_PHOTO_URL = "https://d2eawub7utcl6.cloudfront.net/images/nix-apple-grey.png";
    String unauthorizedExceptionMessage = "message\":\"unauthorized";
    String emptyQueryExceptionMessage = " is not allowed to be empty";
    String wrongIDorEmptyResultException = "resource not found";
    String notMatchingResultsException = "We couldn't match any of your foods";
    Logger log = LoggerFactory.getLogger(NutritionixParser.class);


    /**
     * Get detailed information about BRANDED food
     *
     * @param foodID   ID of branded food
     * @param paramMap required headers
     * @return parsed Food object
     */
    static Food getFoodByID(String foodID, Map<String, String> paramMap) {
        log.debug("Running with parameters: \n foodID:{}\n paramMap:{}", foodID, paramMap);

        paramMap.putAll(AppConfig.nutritionixKeys());
        HttpResponse<SearchByCodeResult> searchByCodeHttpResponse = null;
        try {
            searchByCodeHttpResponse = Unirest.get(BASE_URL + "search/item?nix_item_id={nix_item_id}")
                    .headers(paramMap)
                    .routeParam("nix_item_id", foodID)
                    .asObject(SearchByCodeResult.class);
        } catch (UnirestException e) {
            e.printStackTrace();
            if (e.getCause() instanceof java.net.UnknownHostException)
                log.error("Can't connect to database. Check connection.", e);
            if (e.getCause() instanceof java.lang.RuntimeException) {
                if (e.getMessage().contains(unauthorizedExceptionMessage))
                    log.error("Unauthorized call to API. Perhaps missing property file with keys.");
                else if (e.getMessage().contains(wrongIDorEmptyResultException))
                    throw new IllegalArgumentException("Passed wrong ID.");
                else if (e.getMessage().contains(emptyQueryExceptionMessage)) {
                    log.debug("Passed empty ID parameter.");
                    throw new IllegalArgumentException("Passed empty ID.");
                }
            }
            return null;
        }
        log.debug("Request response status from Nutritionix:{}", searchByCodeHttpResponse.getStatus());
        return NutritionixParser.parseSearchByKeywordOrID(searchByCodeHttpResponse.getBody()).get(0);
    }


    /**
     * Get detailed information about common type food, or log food using natural language method
     *
     * @param keyword  keyword of food
     * @param paramMap required headers
     * @return parsed Food object
     */
    static ArrayList<Food> getFoodByKeyword(String keyword, Map<String, String> paramMap) {
        paramMap.putAll(AppConfig.nutritionixKeys());
        HttpResponse<SearchByCodeResult> searchByCodeHttpResponse;

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
                if (e.getMessage().contains(unauthorizedExceptionMessage))
                    log.error(unauthorizedExceptionMessage); // "Unauthorized call to API. Perhaps missing property file with keys."
                else if (e.getMessage().contains(notMatchingResultsException)) {
                    log.debug(notMatchingResultsException);
                    throw new IllegalArgumentException(notMatchingResultsException);
                } else if (e.getMessage().contains(emptyQueryExceptionMessage)) {
                    log.debug(emptyQueryExceptionMessage);
                    throw new IllegalArgumentException(emptyQueryExceptionMessage);
                }
            }
            return null;
        }
        log.debug("Request response status from Nutritionix:{}", searchByCodeHttpResponse.getStatus());
        return NutritionixParser.parseSearchByKeywordOrID(searchByCodeHttpResponse.getBody());
    }


    /**
     * @param keyword
     * @param paramMap
     */
    static ArrayList<Food> getFoodList(String keyword, Map<String, String> paramMap) {
        log.debug("Running with parameters: \n keyword:{}\n paramMap:{}", keyword, paramMap);

        paramMap.putAll(AppConfig.nutritionixKeys());
        HttpResponse<InstantSearchDetailedResult> instantSearchDetailedHttpResponse = null;
        try {
            instantSearchDetailedHttpResponse = Unirest.get(BASE_URL + "search/instant?query={query}&detailed={detailed}")
                    .headers(paramMap)
                    .routeParam("query", keyword)
                    .routeParam("detailed", "true")
                    .asObject(InstantSearchDetailedResult.class);
        } catch (UnirestException e) {
            if (e.getCause() instanceof java.lang.RuntimeException) {
                if (e.getMessage().contains(emptyQueryExceptionMessage)) {
                    log.debug(emptyQueryExceptionMessage);
                    throw new IllegalArgumentException(emptyQueryExceptionMessage);
                } else if (e.getMessage().contains(unauthorizedExceptionMessage))
                    log.error(unauthorizedExceptionMessage);
            } else if (e.getCause() instanceof java.net.UnknownHostException)
                log.error("Can't connect to database. Check connection.", e);
            return null;
        }
        log.debug("Request response status from Nutritionix:{}", instantSearchDetailedHttpResponse.getStatus());
        return NutritionixParser.parseDetailedList(instantSearchDetailedHttpResponse.getBody());
    }

    /**
     * @param keyword
     * @param paramMap
     */
    static ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap) {
        log.debug("Running with parameters: \n keyword:{}\n paramMap:{}", keyword, paramMap);

        paramMap.putAll(AppConfig.nutritionixKeys());
        HttpResponse<InstantSearchResult> instantSearchResultHttpResponse = null;
        try {
            instantSearchResultHttpResponse = Unirest.get(BASE_URL + "search/instant?query={query}&detailed={detailed}")
                    .headers(paramMap)
                    .routeParam("query", keyword)
                    .routeParam("detailed", "false")
                    .asObject(InstantSearchResult.class);
        } catch (UnirestException e) {
            if (e.getCause() instanceof java.lang.RuntimeException) {
                if (e.getMessage().contains(emptyQueryExceptionMessage)) {
                    log.debug(emptyQueryExceptionMessage);
                    throw new IllegalArgumentException(emptyQueryExceptionMessage);
                } else if (e.getMessage().contains(unauthorizedExceptionMessage))
                    log.error(unauthorizedExceptionMessage);
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
    static Food getFoodByBarcode(int barcode) {
        throw new UnsupportedOperationException();
    }
}