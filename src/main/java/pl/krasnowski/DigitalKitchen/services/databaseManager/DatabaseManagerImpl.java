package pl.krasnowski.DigitalKitchen.services.databaseManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domains.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domains.Recipe;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Entry point for gathering food/recipe(external/internal data)
 */
@Repository
public class DatabaseManagerImpl implements DatabaseManager, Serializable {

    private static final long serialVersionUID = 6922067532281982529L;

    @PostConstruct
    private void initialize() { // Initialize object mapper
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    @Override
    public Food getFoodByID(String FoodID, String dbName, Map<String, String> paramMap) {
        Food nFood = null;

        try {
            nFood = FoodDispatcher.getFoodByID(FoodID, dbName, paramMap);
        } catch (IllegalArgumentException | UnirestException e) {
            e.printStackTrace();
            DatabaseManagerImpl.log.warn(e.getMessage());
        }
        return nFood;
    }

    @Override
    public ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap, ApiRestriction apiRestriction) {
        ArrayList<FoodProxy> result = null;
        try {
            result = FoodDispatcher.getAutocompleteFoodList(keyword, paramMap, apiRestriction);
        } catch (IllegalArgumentException | UnirestException e) {
            e.printStackTrace();
            DatabaseManagerImpl.log.warn(e.getMessage());
        }
        return result;
    }

    public ArrayList<Food> getFoodList(String keyword, Map<String, String> paramMap, ApiRestriction apiRestriction) {
        ArrayList<Food> result = null;
        try {
            result = FoodDispatcher.getFoodList(keyword, paramMap, apiRestriction);
        } catch (IllegalArgumentException | UnirestException e) {
            e.printStackTrace();
            DatabaseManagerImpl.log.warn(e.getMessage());
        }
        return result;
    }

    /*
    RECIPE SECTION
     */

    @Override
    public List<Recipe> getAutocompleteRecipeList(String keyword, Map<String, String> paramMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Recipe getRecipeByBarcode(int barcode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Recipe getRecipeByID(long recipeID, String dbName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Recipe> getRecipeList(String keyword, Map<String, String> paramMap) {
        throw new UnsupportedOperationException();
    }

}