package pl.krasnowski.DigitalKitchen.services.databaseManager.edemam;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Repository;
import pl.krasnowski.DigitalKitchen.model.domains.Food;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;

import java.util.ArrayList;
import java.util.Map;

@Repository
public interface EdemamManager {
    String EDEMAM_DB_NAME = "Edemam";


    /**
     * @param keyword
     * @param paramMap
     */
    static ArrayList<FoodProxy> getAutocompleteFoodList(String keyword, Map<String, String> paramMap) throws UnirestException {
        throw new UnsupportedOperationException();
    }


    /**
     * @param barcode
     */
    static Food getFoodByBarcode(int barcode) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param foodID
     */
    static Food getFoodByID(String foodID) {
        throw new UnsupportedOperationException();
    }

}