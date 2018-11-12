package pl.krasnowski.DigitalKitchen.services.foodDbManager.nutritionix;

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
    protected String thumb;
    @JsonProperty("highres")
    protected Object highres;
    @JsonProperty("is_user_uploaded")
    protected boolean isUserUploaded;

    @Override
    public String toString() {
        return "Photo{" +
                "thumb='" + thumb + '\'' +
                ", highres=" + highres +
                ", isUserUploaded=" + isUserUploaded +
                '}';
    }
}
