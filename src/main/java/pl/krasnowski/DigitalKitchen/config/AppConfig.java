package pl.krasnowski.DigitalKitchen.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.krasnowski.DigitalKitchen.model.domains.Nutrient;
import pl.krasnowski.DigitalKitchen.model.domains.Unit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TimeZone;

@Configuration
public class AppConfig {
    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);
    private static final String extdb_keys_path = "src/main/resources/extdb_keys.properties";

    AppConfig() {
        InitializeObjectMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("TimeZone"));
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


    private static ArrayList<Nutrient> nutrientList = new ArrayList<Nutrient>() {
        private static final long serialVersionUID = 1487572709383579344L;

        {
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "309");
            }}, "ZN", "Zinc, Zn", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "309");
            }}, "ZN", "Zinc, Zn", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "255");
            }}, "WATER", "Water", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "430");
            }}, "VITK1", "Vitamin K (phylloquinone)", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "573");
            }}, "NULL", "Vitamin E, added", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "323");
            }}, "TOCPHA", "Vitamin E (alpha-tocopherol)", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "326");
            }}, "CHOCAL", "Vitamin D3 (cholecalciferol)", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "325");
            }}, "ERGCAL", "Vitamin D2 (ergocalciferol)", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "328");
            }}, "VITD", "Vitamin D (D2 + D3)", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "324");
            }}, "VITD", "Vitamin D", Unit.valueOf("IU"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "401");
            }}, "VITC", "Vitamin C, total ascorbic acid", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "415");
            }}, "VITB6A", "Vitamin B-6", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "578");
            }}, "NULL", "Vitamin B-12, added", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "418");
            }}, "VITB12", "Vitamin B-12", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "320");
            }}, "VITA_RAE", "Vitamin A, RAE", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "318");
            }}, "VITA_IU", "Vitamin A, IU", Unit.valueOf("IU"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "510");
            }}, "VAL_G", "Valine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "509");
            }}, "TYR_G", "Tyrosine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "501");
            }}, "TRP_G", "Tryptophan", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "204");
            }}, "FAT", "Total lipid (fat)", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "342");
            }}, "TOCPHG", "Tocopherol, gamma", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "343");
            }}, "TOCPHD", "Tocopherol, delta", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "341");
            }}, "TOCPHB", "Tocopherol, beta", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "502");
            }}, "THR_G", "Threonine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "404");
            }}, "THIA", "Thiamin", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "263");
            }}, "THEBRN", "Theobromine", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "269");
            }}, "SUGAR", "Sugars, total", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "210");
            }}, "SUCS", "Sucrose", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "638");
            }}, "STID7", "Stigmasterol", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "209");
            }}, "STARCH", "Starch", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "307");
            }}, "NA", "Sodium, Na", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "518");
            }}, "SER_G", "Serine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "317");
            }}, "SE", "Selenium, Se", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "405");
            }}, "RIBF", "Riboflavin", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "319");
            }}, "RETOL", "Retinol", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "203");
            }}, "PROCNT", "Protein", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "517");
            }}, "PRO_G", "Proline", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "306");
            }}, "K", "Potassium, K", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "636");
            }}, "PHYSTR", "Phytosterols", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "305");
            }}, "P", "Phosphorus, P", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "508");
            }}, "PHE_G", "Phenylalanine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "410");
            }}, "PANTAC", "Pantothenic acid", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "406");
            }}, "NIA", "Niacin", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "506");
            }}, "MET_G", "Methionine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "428");
            }}, "MK4", "Menaquinone-4", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "315");
            }}, "MN", "Manganese, Mn", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "214");
            }}, "MALS", "Maltose", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "304");
            }}, "MG", "Magnesium, Mg", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "505");
            }}, "LYS_G", "Lysine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "337");
            }}, "LYCPN", "Lycopene", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "338");
            }}, "LUT+ZEA", "Lutein + zeaxanthin", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "504");
            }}, "LEU_G", "Leucine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "213");
            }}, "LACS", "Lactose", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "503");
            }}, "ILE_G", "Isoleucine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "303");
            }}, "FE", "Iron, Fe", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "521");
            }}, "HYP", "Hydroxyproline", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "512");
            }}, "HISTN_G", "Histidine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "516");
            }}, "GLY_G", "Glycine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "515");
            }}, "GLU_G", "Glutamic acid", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "211");
            }}, "GLUS", "Glucose (dextrose)", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "287");
            }}, "GALS", "Galactose", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "212");
            }}, "FRUS", "Fructose", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "431");
            }}, "FOLAC", "Folic acid", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "417");
            }}, "FOL", "Folate, total", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "432");
            }}, "FOLFD", "Folate, food", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "435");
            }}, "FOLDFE", "Folate, DFE", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "313");
            }}, "FLD", "Fluoride, F", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "291");
            }}, "FIBTG", "Fiber, total dietary", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "695");
            }}, "FATRNP", "Fatty acids, total trans-polyenoic", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "693");
            }}, "FATRNM", "Fatty acids, total trans-monoenoic", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "605");
            }}, "FATRN", "Fatty acids, total trans", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "606");
            }}, "FASAT", "Fatty acids, total saturated", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "646");
            }}, "FAPU", "Fatty acids, total polyunsaturated", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "645");
            }}, "FAMS", "Fatty acids, total monounsaturated", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "208");
            }}, "ENERC_KCAL", "Energy", Unit.valueOf("kcal"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "268");
            }}, "ENERC_KJ", "Energy", Unit.valueOf("kJ"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "429");
            }}, "VITK1D", "Dihydrophylloquinone", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "507");
            }}, "CYS_G", "Cystine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "334");
            }}, "CRYPX", "Cryptoxanthin, beta", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "312");
            }}, "CU", "Copper, Cu", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "421");
            }}, "CHOLN", "Choline, total", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "601");
            }}, "CHOLE", "Cholesterol", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "321");
            }}, "CARTB", "Carotene, beta", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "322");
            }}, "CARTA", "Carotene, alpha", Unit.valueOf("µg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "205");
            }}, "CHOCDF", "Carbohydrate, by difference", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "639");
            }}, "CAMD5", "Campesterol", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "301");
            }}, "CA", "Calcium, Ca", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "262");
            }}, "CAFFN", "Caffeine", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "454");
            }}, "BETN", "Betaine", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "641");
            }}, "SITSTR", "Beta-sitosterol", Unit.valueOf("mg"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "514");
            }}, "ASP_G", "Aspartic acid", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "207");
            }}, "ASH", "Ash", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "511");
            }}, "ARG_G", "Arginine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "221");
            }}, "ALC", "Alcohol, ethyl", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "513");
            }}, "ALA_G", "Alanine", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "257");
            }}, "NULL", "Adjusted Protein", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "671");
            }}, "F24D1C", "24:1 c", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "621");
            }}, "F22D6", "22:6 n-3 (DHA)", Unit.valueOf("g"), "omega-3 docosahexaenoic acid "));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "631");
            }}, "F22D5", "22:5 n-3 (DPA)", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "630");
            }}, "F22D1", "22:1 undifferentiated", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "664");
            }}, "NULL", "22:1 t", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "676");
            }}, "NULL", "22:1 c", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "629");
            }}, "F20D5", "20:5 n-3 (EPA)", Unit.valueOf("g"), "omega-3 eicosapentaenoic acid"));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "620");
            }}, "F20D4", "20:4 undifferentiated", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "855");
            }}, "F20D4N6", "20:4 n-6", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "689");
            }}, "F20D3", "20:3 undifferentiated", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "853");
            }}, "F20D3N6", "20:3 n-6", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "852");
            }}, "F20D3N3", "20:3 n-3", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "672");
            }}, "F20D2CN6", "20:2 n-6 c,c", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "856");
            }}, "NULL", "18:3i", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "619");
            }}, "F18D3", "18:3 undifferentiated", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "685");
            }}, "F18D3CN6", "18:3 n-6 c,c,c", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "851");
            }}, "F18D3CN3", "18:3 n-3 c,c,c (ALA)", Unit.valueOf("g"), "omega-3 alpha-linolenic acid"));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "618");
            }}, "F18D2", "18:2 undifferentiated", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "669");
            }}, "F18D2TT", "18:2 t,t", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "665");
            }}, "NULL", "18:2 t not further defined", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "675");
            }}, "F18D2CN6", "18:2 n-6 c,c", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "666");
            }}, "NULL", "18:2 i", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "670");
            }}, "F18D2CLA", "18:2 CLAs", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "859");
            }}, "F18D1TN7", "18:1-11t (18:1t n-7)", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "617");
            }}, "F18D1", "18:1 undifferentiated", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "663");
            }}, "F18D1T", "18:1 t", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "674");
            }}, "F18D1C", "18:1 c", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "626");
            }}, "F16D1", "16:1 undifferentiated", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "662");
            }}, "F16D1T", "16:1 t", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "673");
            }}, "F16D1C", "16:1 c", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "654");
            }}, "F24D0", "24:00:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "858");
            }}, "F22D4", "22:04", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "624");
            }}, "F22D0", "22:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "857");
            }}, "F21D5", "21:05", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "628");
            }}, "F20D1", "20:01", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "615");
            }}, "F20D0", "20:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "627");
            }}, "F18D4", "18:04", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "614");
            }}, "F18D0", "18:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "687");
            }}, "F17D1", "17:01", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "653");
            }}, "F17D0", "17:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "613");
            }}, "F16D0", "16:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "697");
            }}, "F15D1", "15:01", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "652");
            }}, "F15D0", "15:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "625");
            }}, "F14D1", "14:01", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "612");
            }}, "F14D0", "14:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "696");
            }}, "F13D0", "13:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "611");
            }}, "F12D0", "12:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "610");
            }}, "F10D0", "10:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "609");
            }}, "F8D0", "8:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "608");
            }}, "F6D0", "6:00", Unit.valueOf("g"), ""));
            add(new Nutrient(new HashMap<String, String>() {{
                put("Nutritionix", "607");
            }}, "F4D0", "4:00", Unit.valueOf("g"), ""));

        }
    };


    public static ArrayList<Nutrient> getNutrientsList() {
        return nutrientList;
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