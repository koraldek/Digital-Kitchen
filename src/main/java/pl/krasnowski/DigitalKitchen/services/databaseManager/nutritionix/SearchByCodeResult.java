
package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "foods"
})
class SearchByCodeResult {

    @JsonProperty("foods")
    List<Food> foods = null;


    @Override
    public String toString() {
        return "SearchByCodeResult{" +
                "foods=" + foods +
                '}';
    }
}