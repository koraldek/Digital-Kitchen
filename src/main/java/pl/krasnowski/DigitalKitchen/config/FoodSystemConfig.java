package pl.krasnowski.DigitalKitchen.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.krasnowski.DigitalKitchen.model.domain.food.Nutrient;
import pl.krasnowski.DigitalKitchen.model.domain.food.Unit;
import pl.krasnowski.DigitalKitchen.model.domain.physicalActivity.PhysicalActivityType;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Configuration
public class FoodSystemConfig {

    private static final Logger log = LoggerFactory.getLogger(FoodSystemConfig.class);
    private static final String extdb_keys_path = "src/main/resources/extdb_keys.properties";
    private static final String met_values_path = "src/main/resources/met values.data";
    private static final String nutrients_path = "src/main/resources/nutrients.txt";

    FoodSystemConfig() {
        InitializeObjectMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("TimeZone"));
    }

    @Bean
    public static HashSet<PhysicalActivityType> physicalActivityTypes() {
        HashSet<PhysicalActivityType> activityTypes = new HashSet<>();
        try (Scanner scanner = new Scanner(new File(met_values_path))) {
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split("\t");
                PhysicalActivityType pat = new PhysicalActivityType();
                pat.setCode(line[0]);
                pat.setMETValue(Double.valueOf(line[1]));
                pat.setMajorHeading(line[2]);
                pat.setActivityName(line[3]);
                activityTypes.add(pat);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("Could not read:" + met_values_path);
        }

        return activityTypes;
    }


    @Bean
    public static HashMap<String, String> nutritionixKeys() {
        HashMap<String, String> nutritionixKeys = new HashMap<>(2);
        try (Scanner scanner = new Scanner(new File(extdb_keys_path))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains("x-app-id"))
                    nutritionixKeys.put("x-app-id", line.split("=")[1]);
                if (line.contains("x-app-key"))
                    nutritionixKeys.put("x-app-key", line.split("=")[1]);
            }
        } catch (FileNotFoundException e) {
            log.error("Could not read " + extdb_keys_path);
            nutritionixKeys.put("x-app-id", System.getenv("x-app-id"));
            nutritionixKeys.put("x-app-key", System.getenv("x-app-key"));
        }
        if (nutritionixKeys.isEmpty())
            log.error("Can't read API keys from file:" + extdb_keys_path);

        return nutritionixKeys;
    }


    private void InitializeObjectMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

}