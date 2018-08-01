package pl.krasnowski.DigitalKitchen.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.krasnowski.DigitalKitchen.model.domains.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domains.User;
import pl.krasnowski.DigitalKitchen.services.databaseManager.DatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping(value = "/food")
class FoodController {

    @Autowired
    private DatabaseManager databaseManager;

    @Autowired
    @Qualifier("nutritionixKeys")
    HashMap<String, String> nxKeys; // to niepotrzebne w sumie az tak

    @Autowired
    private User user;

    @Autowired
    @Qualifier("apiRestrictionNutritionix")
    private Set<ApiRestriction> apiRestrictions;

    @RequestMapping(value = "/autocomplete", method = RequestMethod.GET)
    public ArrayList<FoodProxy> getAutocompleteFoodList(@RequestParam("keyword") String keyword) {
        ArrayList<FoodProxy> autocompleteFoodList = databaseManager.getAutocompleteFoodList(keyword, nxKeys, apiRestrictions);
        ArrayList<FoodProxy> result = new ArrayList<>();
        if (autocompleteFoodList.size() > 12) {
            for (int i = 0; i < 12; i++)
                result.add(autocompleteFoodList.get(i));
        }
        return result;
    }

    @RequestMapping(value = "/detailed", method = RequestMethod.GET) //TODO: dodac parametry
    public Food getDetailedFood(@RequestParam("foodID") String foodID, @RequestParam("dbName") String dbName) throws UnirestException {
        return databaseManager.getFoodByID(foodID, dbName, nxKeys);
    }
}
