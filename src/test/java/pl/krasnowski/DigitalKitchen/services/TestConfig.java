package pl.krasnowski.DigitalKitchen.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Random;

@Configuration
public class TestConfig {
    private static final Random r = new Random();
    private static final String[] sampleFoodNames = {
            "chicken", "potato", "tofu", "beef", "coconut", "cinnamon"
    };

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
}
