
package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "value",
        "attr_id"
})
class FullNutrient {

    @JsonProperty("value")
    double value;
    @JsonProperty("attr_id")
    long attrId;


}
