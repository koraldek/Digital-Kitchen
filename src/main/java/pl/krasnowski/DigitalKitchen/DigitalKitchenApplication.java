package pl.krasnowski.DigitalKitchen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication(exclude = SessionAutoConfiguration.class)
public class DigitalKitchenApplication {

    public static void main(String[] args) {

        SpringApplication.run(DigitalKitchenApplication.class, args);

//        String keyword;
//        Scanner scanner = new Scanner(System.in);
//        DatabaseManager databaseManager = new DatabaseManagerImpl();
//        databaseManager.setStrategyProfile(new StrategyProfileBasic());
//            DatabaseManager databaseManager = (DatabaseManager) spac.getBean("databaseManager");
//        ApiRestrictionService myBean = (ApiRestrictionService) spac.getBean("apiRestrictionService");
//        System.out.println(myBean2.toString());
//        myBean.resetAll(DatabaseManager.NUTRITIONIX_DB_NAME);
//        while (true) {
//            System.out.println("Enter product name to find ( \"e\" - to exit):");
//            keyword = scanner.nextLine();
//            if (keyword.equals("e"))
//                break;
//
//            ArrayList<FoodProxy> foods = databaseManager.getAutocompleteFoodList(keyword, null);
//            for (FoodProxy f : foods) {
//                System.out.println(f.toString());
//            }
//
//            System.out.println("=====================================");
//        }
    }
}