package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
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

    @JsonProperty("source")
    long source;
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
    public int getServingWeightGrams() {
        return servingWeightGrams;
    }

    @Override
    public AltMeasure getPrimaryServingMeasure() {
        AltMeasure pm = new AltMeasure();
        pm.setMeasure(servingUnit);
        pm.setQty((int) servingQty);
        pm.setServingWeight(servingWeightGrams);

        return pm;
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
        return nixItemId;
    }
}