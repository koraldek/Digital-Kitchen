
package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "branded",
        "common"
})
class InstantSearchResult {

    @JsonProperty("branded")
    private List<Branded> branded = null;
    @JsonProperty("common")
    private List<Common> common = null;

    @JsonProperty("branded")
    List<Branded> getBranded() {
        return branded;
    }

    @JsonProperty("branded")
    void setBranded(List<Branded> branded) {
        this.branded = branded;
    }

    @JsonProperty("common")
    List<Common> getCommon() {
        return common;
    }

    @JsonProperty("common")
    void setCommon(List<Common> common) {
        this.common = common;
    }

    @Override
    public String toString() {
        return "InstantSearchResult{" +
                "branded=" + branded +
                ", common=" + common +
                '}';
    }

    List<IFood> getFoodInformation() {
        List<IFood> foodInformation = new ArrayList<>();
        foodInformation.addAll(branded);
        foodInformation.addAll(common);
        return foodInformation;
    }
}
