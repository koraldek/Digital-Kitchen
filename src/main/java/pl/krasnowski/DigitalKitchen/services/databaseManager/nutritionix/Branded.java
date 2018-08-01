
package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "food_name",
        "nix_brand_id",
        "photo",
        "brand_name",
        "full_nutrients",
        "serving_weight_grams",
        "nix_item_id",
        "serving_unit",
        "brand_name_item_name",
        "serving_qty",
        "nf_calories",
        "region",
        "brand_type",
        "locale"
})
class Branded implements IFood {

    @JsonProperty("food_name")
    String foodName;
    @JsonProperty("nix_brand_id")
    String nixBrandId;
    @JsonProperty("photo")
    Photo photo;
    @JsonProperty("brand_name")
    String brandName;
    @JsonProperty("full_nutrients")
    List<FullNutrient> fullNutrients = null;
    @JsonProperty("serving_weight_grams")
    int servingWeightGrams;
    @JsonProperty("nix_item_id")
    String nixItemId;
    @JsonProperty("serving_unit")
    String servingUnit;
    @JsonProperty("brand_name_item_name")
    String brandNameItemName;
    @JsonProperty("serving_qty")
    int servingQty;
    @JsonProperty("nf_calories")
    double nfCalories;
    @JsonProperty("region")
    long region;
    @JsonProperty("brand_type")
    long brandType;
    @JsonProperty("locale")
    String locale;


    @Override
    public String getName() {
        return foodName;
    }

    @Override
    public String getPhoto() {
        return photo.thumb;
    }

    @Override
    public int getServingGramsWeight() {
        return servingWeightGrams;
    }

    @Override
    public List<AltMeasure> getAltMeasures() {
        return new ArrayList<>();
    }

    @Override
    public List<FullNutrient> getFullNutrients() {
        return new ArrayList<>();
    }

    @Override
    public String getFoodID() {
        return nixItemId;
    }
}
