
package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "food_name",
        "serving_unit",
        "serving_qty",
        "photo",
        "tag_id",
        "full_nutrients",
        "serving_weight_grams",
        "locale"
})
class Common {

    @JsonProperty("food_name")
    String foodName;
    @JsonProperty("serving_unit")
    String servingUnit;
    @JsonProperty("serving_qty")
    int servingQty;
    @JsonProperty("photo")
    Photo photo;
    @JsonProperty("tag_id")
    String tagId;
    @JsonProperty("full_nutrients")
    List<FullNutrient> fullNutrients = null;
    @JsonProperty("serving_weight_grams")
    int servingWeightGrams;
    @JsonProperty("locale")
    String locale;


    @Override
    public String toString() {
        return "Common{" +
                "foodName='" + foodName + '\'' +
                ", servingUnit='" + servingUnit + '\'' +
                ", servingQty=" + servingQty +
                ", photo=" + photo +
                ", tagId='" + tagId + '\'' +
                ", fullNutrients=" + fullNutrients +
                ", servingWeightGrams=" + servingWeightGrams +
                ", locale='" + locale + '\'' +
                '}';
    }
}
