
package pl.krasnowski.DigitalKitchen.services.databaseManager.nutritionix;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "thumb",
        "highres",
        "is_user_uploaded"
})
class Photo {

    @JsonProperty("thumb")
    String thumb;
    @JsonProperty("highres")
    Object highres;
    @JsonProperty("is_user_uploaded")
    boolean isUserUploaded;

    @Override
    public String toString() {
        return "Photo{" +
                "thumb='" + thumb + '\'' +
                ", highres=" + highres +
                ", isUserUploaded=" + isUserUploaded +
                '}';
    }
}
