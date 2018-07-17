package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.config.AppConfig;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;


@Repository
public interface NutritionixManager {

    String NUTRITIONIX_DB_NAME = "Nutritionix";
    String BASE_URL = "https://trackapi.nutritionix.com/v2/search/";
    String DEFAULT_EXTERNAL_PHOTO_URL = "https://d2eawub7utcl6.cloudfront.net/images/nix-apple-grey.png";

    Logger log = LoggerFactory.getLogger(NutritionixParser.class);

    /**
     * Will be implemented in futher versions.
     *
     * @param barcode
     */
    static Food getFoodByBarcode(int barcode) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get detailed information about BRANDED food
     *
     * @param foodID   ID of branded food
     * @param paramMap required headers
     * @return parsed Food object
     */
    static Food getFoodByID(String foodID, Map<String, String> paramMap) throws UnirestException {

        paramMap.putAll(AppConfig.getNutritionixKeys());
        HttpResponse<SearchByCodeResult> searchByCodeHttpResponse = Unirest.get(BASE_URL + "item?nix_item_id={nix_item_id}")
                .headers(paramMap)
                .routeParam("nix_item_id", foodID)
                .asObject(SearchByCodeResult.class);
        log.debug("Request response status from Nutritionix:{}", searchByCodeHttpResponse.getStatus());

        return NutritionixParser.parseResult(searchByCodeHttpResponse.getBody().foods.get(0));
    }


    /**
     * Get detailed information about BRANDED food
     *
     * @param keyword  keyword of food
     * @param paramMap required headers
     * @return parsed Food object
     */
    static Food getFoodByKeyword(String keyword, Map<String, String> paramMap) throws UnirestException {

        paramMap.putAll(AppConfig.getNutritionixKeys());
        HttpResponse<SearchByCodeResult> searchByCodeHttpResponse = Unirest.get(BASE_URL + "instant?query={query}&detailed={detailed}")
                .headers(paramMap)
                .routeParam("nix_item_id", keyword)
                .asObject(SearchByCodeResult.class);

        log.debug("Request response status from Nutritionix:{}", searchByCodeHttpResponse.getStatus());
        return NutritionixParser.parseResult(searchByCodeHttpResponse.getBody().foods.get(0));
    }


    /**
     * @param keyword
     * @param paramMap
     */
    static ArrayList<Food> getFoodList(String keyword, Map<String, String> paramMap) throws UnirestException {
        log.debug("Running with parameters: \n keyword:{}\n paramMap:{}", keyword, paramMap);

        paramMap.putAll(AppConfig.getNutritionixKeys());
        HttpResponse<InstantSearchDetailedResult> instantSearchDetailedHttpResponse = Unirest.get(BASE_URL + "instant?query={query}&detailed={detailed}")
                .headers(paramMap)
                .routeParam("query", keyword)
                .routeParam("detailed", "true")
                .asObject(InstantSearchDetailedResult.class);

        log.debug("Request response status from Nutritionix:{}", instantSearchDetailedHttpResponse.getStatus());
        return NutritionixParser.parseDetailedList(instantSearchDetailedHttpResponse.getBody());
    }

    /**
     * @param keyword
     * @param paramMap
     */
    static ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap) throws UnirestException {

        paramMap.putAll(AppConfig.getNutritionixKeys());
        HttpResponse<InstantSearchResult> instantSearchResultHttpResponse = Unirest.get(BASE_URL + "instant?query={query}&detailed={detailed}")
                .headers(paramMap)
                .routeParam("query", keyword)
                .routeParam("detailed", "false")
                .asObject(InstantSearchResult.class);

        log.debug("Response from server:{}", instantSearchResultHttpResponse.getBody().toString());
        return NutritionixParser.parseInstantList(instantSearchResultHttpResponse.getBody());
    }

}