package pl.krasnowski.DigitalKitchen.model.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import pl.krasnowski.DigitalKitchen.model.domain.food.Nutrient;
import pl.krasnowski.DigitalKitchen.model.domain.food.NutrientWrapper;

import java.io.IOException;

public class NutrientWrapperDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        Nutrient nutrient = null;
        int tag = node.get("nutrient").get("tag").asInt();
        for (Nutrient n :
                Nutrient.values()) {
            if (n.getTag() == tag) {
                nutrient = n;
                break;
            }
        }
        double quantity = node.get("quantity").asDouble();
        return new NutrientWrapper(nutrient, quantity);
    }
}
