package pl.krasnowski.DigitalKitchen.model.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.krasnowski.DigitalKitchen.model.domain.food.Nutrient;

import java.io.IOException;

public class NutrientDeserializer extends StdDeserializer<Nutrient> {

    public NutrientDeserializer() {
        this(null);
    }

    public NutrientDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Nutrient deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        JsonNode node = mapper.readTree(jp);

        int tag = node.get("tag").asInt();
        for (Nutrient n : Nutrient.values()) {
            if (n.getTag() == tag)
                return n;
        }
        throw new IllegalArgumentException("Unknown tag code for nutrient: " + tag);
    }
}
