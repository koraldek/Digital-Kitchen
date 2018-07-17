package pl.krasnowski.DigitalKitchen;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.krasnowski.DigitalKitchen.config.AppConfig;
import pl.krasnowski.DigitalKitchen.model.domains.ApiRestriction;
import pl.krasnowski.DigitalKitchen.model.domains.FoodProxy;
import pl.krasnowski.DigitalKitchen.services.databaseManager.DatabaseManager;
import pl.krasnowski.DigitalKitchen.services.databaseManager.DatabaseManagerImpl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


@SpringBootApplication
//@PropertySources({
//        @PropertySource("classpath:extdb_keys.properties"),
//        @PropertySource("classpath:application.properties"),
//        @PropertySource("classpath:log4j.properties")
//})
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class DigitalKitchenApplication {
    private static final Logger log = LoggerFactory.getLogger(DigitalKitchenApplication.class);


    public static void main(String[] args) throws UnirestException, FileNotFoundException {
        String keyword;
        Scanner scanner = new Scanner(System.in);
        ApiRestriction restrictions = new ApiRestriction();
        DatabaseManager databaseManager = new DatabaseManagerImpl();

        SpringApplication.run(DigitalKitchenApplication.class, args);

        while (true) {
            System.out.println("Enter product name to find ( \"e\" - to exit):");
            keyword = scanner.nextLine();
            if (keyword.equals("e"))
                break;

            ArrayList<FoodProxy> foods = databaseManager.getAutocompleteFoodList(keyword, AppConfig.dummyParamMap, restrictions);
            for (FoodProxy f : foods) {
                //         System.out.println(f.toString());
            }

            System.out.println("=====================================");
        }
    }
}