
package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "branded",
    "common"
})
 class InstantSearchDetailedResult {

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
        return "InstantSearchDetailedResult{" +
                "branded=" + branded +
                ", common=" + common +
                '}';
    }
}
