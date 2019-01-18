package pl.krasnowski.DigitalKitchen.model.serialization;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.krasnowski.DigitalKitchen.model.domain.food.Nutrient;

import java.io.IOException;

public class NutrientSerializer extends StdSerializer<Nutrient> {

    public NutrientSerializer() {
        super(Nutrient.class);
    }

    public NutrientSerializer(Class t) {
        super(t);
    }


    @Override
    public void serialize(Nutrient nut, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("tag");
        gen.writeNumber(nut.getTag());
        gen.writeFieldName("usda_tag");
        gen.writeString(nut.getUSDA_TAG());
        gen.writeFieldName("name");
        gen.writeString(nut.getName());
        gen.writeFieldName("unit");
        gen.writeString(nut.getUnit().getName());
        gen.writeEndObject();
    }
}
