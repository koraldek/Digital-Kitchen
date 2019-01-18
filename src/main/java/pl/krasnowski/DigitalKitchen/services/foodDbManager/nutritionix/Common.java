package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@ToString
class Common implements IFood {

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

    @JsonProperty("common_type")
    String common_type;


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
        return new AltMeasure(servingQty, servingUnit);
    }

    @Override
    public List<AltMeasure> getAltMeasures() {
        ArrayList<AltMeasure> measures = new ArrayList<>();
        measures.add(new AltMeasure(servingQty, servingUnit));
        return measures;
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
