package pl.krasnowski.DigitalKitchen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.model.domain.food.FoodProxy;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/food")
class FoodController {

    @Autowired
    private DatabaseManager databaseManager;


    @RequestMapping(value = "/autocomplete", method = RequestMethod.GET)
    public ArrayList<FoodProxy> getAutocompleteFoodList(@RequestParam("keyword") String keyword) {

        ArrayList<FoodProxy> autocompleteFoodList;
        autocompleteFoodList = databaseManager.getAutocompleteFoodList(keyword);
        ArrayList<FoodProxy> result = new ArrayList<>();
        if (autocompleteFoodList.size() > 12)
            for (int i = 0; i < 12; i++)
                result.add(autocompleteFoodList.get(i));
        return result;
    }

    @RequestMapping(value = "/detailed", method = RequestMethod.GET)
    public Food getDetailedFood(@RequestParam("foodID") String foodID, @RequestParam("dbName") String dbName) {
        return databaseManager.getFoodByID(foodID, dbName);
    }
}
