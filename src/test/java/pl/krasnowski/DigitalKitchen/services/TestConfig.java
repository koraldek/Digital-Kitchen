package pl.krasnowski.DigitalKitchen.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import pl.krasnowski.DigitalKitchen.model.domain.user.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domain.user.Sex;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.ApiRestrictionServiceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

@Configuration
public class TestConfig {

    private static final Logger log = LoggerFactory.getLogger(TestConfig.class);
    private static final String extdb_keys_path = "src/main/resources/extdb_keys.properties";

    private static final Random r = new Random();
    private static final String[] sampleFoodNames = {
            "chicken", "potato", "tofu", "beef", "coconut", "cinnamon"
    };


    TestConfig() {
        initializeObjectMapper();
    }

    @Bean
    @Qualifier("adminUser")
    static User adminUser() {
        User adminUser = new User();
        adminUser.setVisibleName("Digital Kitchen community");
        adminUser.setUsername("Administration");
        adminUser.setPassword("123456");
        adminUser.setEmail("digital.kitchen.admin@gmail.com");
        adminUser.setSex(Sex.male);
        return adminUser;
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
            e.printStackTrace();
            log.error("Could not read extdb_keys.properties.");
        }
        if (nutritionixKeys.isEmpty())
            log.error("Can't read API keys from file:" + extdb_keys_path);

        return nutritionixKeys;
    }

    @Bean
    @Qualifier("nxSampleID")
    String nxSampleID() {
        return "55774782f564e5a4273a7885"; //Chips ID
    }

    @Bean
    @Qualifier("sampleFoodName")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    String sampleKeyword() {
        return sampleFoodNames[r.nextInt(sampleFoodNames.length - 1)];
    }


    @Bean
    @Primary
    public Set<ApiRestriction> apiRestrictionsDefault() {
        return ApiRestrictionServiceImpl.buildAPIRestrictions();
    }


    private void initializeObjectMapper() {
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
