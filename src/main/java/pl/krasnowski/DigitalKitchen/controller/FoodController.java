package pl.krasnowski.DigitalKitchen.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.krasnowski.DigitalKitchen.config.AppConfig;
import pl.krasnowski.DigitalKitchen.model.domains.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;
import pl.krasnowski.DigitalKitchen.model.domains.User;
import pl.krasnowski.DigitalKitchen.services.databaseManager.DatabaseManager;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/food")
class FoodController {

    @Autowired
    DatabaseManager databaseManager;

    private User user;

    private ApiRestriction apiRestriction = new ApiRestriction();

    @RequestMapping(value = "/autocomplete", method = RequestMethod.GET)
    public ArrayList<FoodProxy> getAutocompleteFoodList(@RequestParam("keyword") String keyword) throws UnirestException {
        ArrayList<FoodProxy> autocompleteFoodList = databaseManager.getAutocompleteFoodList(keyword, AppConfig.dummyParamMap, apiRestriction);
        ArrayList<FoodProxy> result = new ArrayList<>();
        if (autocompleteFoodList.size() > 12) {
            for (int i = 0; i < 12; i++)
                result.add(autocompleteFoodList.get(i));
        }
        return result;
    }

    @RequestMapping(value = "/detailed", method = RequestMethod.GET) //TODO: dodac parametry
    public Food getDetailedFood(@RequestParam("foodID") String foodID, @RequestParam("dbName") String dbName) throws UnirestException {
        return databaseManager.getFoodByID(foodID, dbName, AppConfig.dummyParamMap);
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Food findOne(@PathVariable("id") Long id) {
//        return RestPreconditions.checkFound( DatabaseManagerImpl.findOne( id ));
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    @ResponseBody
//    public Long create(@RequestBody Foo resource) {
//        Preconditions.checkNotNull(resource);
//        return DatabaseManagerImpl.create(resource);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.OK)
//    public void update(@PathVariable( "id" ) Long id, @RequestBody Foo resource) {
//        Preconditions.checkNotNull(resource);
//        RestPreconditions.checkNotNull(DatabaseManagerImpl.getById( resource.getId()));
//        DatabaseManagerImpl.update(resource);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.OK)
//    public void delete(@PathVariable("id") Long id) {
//        DatabaseManagerImpl.deleteById(id);
//    }

}
