package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "value",
        "attr_id"
})
@ToString
class FullNutrient {

    @JsonProperty("value")
    private double value;
    @JsonProperty("attr_id")
    private long attrId;


}
