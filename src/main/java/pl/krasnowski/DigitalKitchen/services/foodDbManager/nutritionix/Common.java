package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
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
        "locale",
        "common_type"
})
class Common implements IFood {

    @JsonProperty("food_name")
    private String foodName;
    @JsonProperty("serving_unit")
    private String servingUnit;
    @JsonProperty("serving_qty")
    private int servingQty;
    @JsonProperty("photo")
    private Photo photo;
    @JsonProperty("tag_id")
    private String tagId;
    @JsonProperty("full_nutrients")
    private List<FullNutrient> fullNutrients = null;
    @JsonProperty("serving_weight_grams")
    private int servingWeightGrams;
    @JsonProperty("locale")
    private String locale;

    @JsonProperty("common_type")
    String common_type;


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
