package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "foods"
})
class SearchByCodeResult {

    @JsonProperty("foods")
    private List<Food> foods = null;

    @Override
    public String toString() {
        return "SearchByCodeResult{" +
                "foods=" + foods +
                '}';
    }


    List<IFood> getFoodInformation() {
        return new ArrayList<>(foods);
    }
}