
package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "food_name",
        "brand_name",
        "serving_qty",
        "serving_unit",
        "serving_weight_grams",
        "nf_calories",
        "nf_total_fat",
        "nf_saturated_fat",
        "nf_cholesterol",
        "nf_sodium",
        "nf_total_carbohydrate",
        "nf_dietary_fiber",
        "nf_sugars",
        "nf_protein",
        "nf_potassium",
        "nf_p",
        "full_nutrients",
        "nix_brand_name",
        "nix_brand_id",
        "nix_item_name",
        "nix_item_id",
        "metadata",
        "source",
        "ndb_no",
        "tags",
        "alt_measures",
        "photo",
        "updated_at",
        "nf_ingredient_statement"
})
class Food implements IFood {
    @JsonProperty("food_name")
    String foodName;
    @JsonProperty("brand_name")
    String brandName;
    @JsonProperty("serving_qty")
    double servingQty;
    @JsonProperty("serving_unit")
    String servingUnit;
    @JsonProperty("serving_weight_grams")
    int servingWeightGrams;
    @JsonProperty("nf_calories")
    long nfCalories;
    @JsonProperty("nf_total_fat")
    long nfTotalFat;
    @JsonProperty("nf_saturated_fat")
    long nfSaturatedFat;
    @JsonProperty("nf_cholesterol")
    long nfCholesterol;
    @JsonProperty("nf_sodium")
    long nfSodium;
    @JsonProperty("nf_total_carbohydrate")
    long nfTotalCarbohydrate;
    @JsonProperty("nf_dietary_fiber")
    long nfDietaryFiber;
    @JsonProperty("nf_sugars")
    long nfSugars;
    @JsonProperty("nf_protein")
    long nfProtein;
    @JsonProperty("nf_potassium")
    String nfPotassium;
    @JsonProperty("nf_p")
    String nfP;
    @JsonProperty("full_nutrients")
    List<FullNutrient> fullNutrients = null;
    @JsonProperty("nix_brand_name")
    String nixBrandName;
    @JsonProperty("nix_brand_id")
    String nixBrandId;
    @JsonProperty("nix_item_name")
    String nixItemName;
    @JsonProperty("nix_item_id")
    String nixItemId;
    @JsonProperty("metadata")
    Metadata metadata;
    @JsonProperty("source")
    long source;
    @JsonProperty("ndb_no")
    String ndbNo;
    @JsonProperty("tags")
    Object tags;
    @JsonProperty("alt_measures")
    List<AltMeasure> altMeasures;
    @JsonProperty("photo")
    Photo photo;
    @JsonProperty("updated_at")
    String updatedAt;
    @JsonProperty("nf_ingredient_statement")
    Object nfIngredientStatement;

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
        return altMeasures;
    }

    @Override
    public List<FullNutrient> getFullNutrients() {
        return fullNutrients;
    }

    @Override
    public String getFoodID() {
        return foodName;
    }
}