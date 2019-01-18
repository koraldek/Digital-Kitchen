package pl.krasnowski.DigitalKitchen.model.domain.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.krasnowski.DigitalKitchen.model.serialization.NutrientDeserializer;
import pl.krasnowski.DigitalKitchen.model.serialization.NutrientSerializer;

import static pl.krasnowski.DigitalKitchen.model.domain.food.Unit.*;

@JsonSerialize(using = NutrientSerializer.class)
@JsonDeserialize(using = NutrientDeserializer.class)
public enum Nutrient {
    CALCIUM(301, "CA", "Calcium, Ca", MG),
    CARBOHYDRATE(205, "CHOCDF", "Carbohydrate, by difference", G),
    CHOLESTEROL(601, "CHOLE", "Cholesterol", MG),
    ENERGY(208, "ENERC_KCAL", "Energy", KCAL),
    FAT_SATURATED(606, "FASAT", "Fatty acids, total saturated", G),
    FAT(204, "FAT", "Total lipid (fat)", G),
    FAT_TRANS(605, "FATRN", "Fatty acids, total trans", G),
    IRON(303, "FE", "Iron, Fe", MG),
    FIBER(291, "FIBTG", "Fiber, total dietary", G),
    POTASSIUM(306, "K", "Potassium, K", MG),
    SODIUM(307, "NA", "Sodium, Na", MG),
    PROTEIN(203, "PROCNT", "Protein", G),
    SUGAR(269, "SUGAR", "Sugars, total", G),
    SUGAR_ADDED(539, "SUGAR_ADD", "Sugars, added", G),
    VITAMIN_D(324, "VITD", "Vitamin D", IU),
    ALANINE_G(513, "ALA_G", "Alanine", G),
    ALCOHOL(221, "ALC", "Alcohol, ethyl", G),
    ARGININE(511, "ARG_G", "Arginine", G),
    ASH(207, "ASH", "Ash", G),
    ASPARTIC_ACID(514, "ASP_G", "Aspartic acid", G),
    BETAINE(454, "BETN", "Betaine", MG),
    CAFFEINE(262, "CAFFN", "Caffeine", MG),
    CAMPESTEROL(639, "CAMD5", "Campesterol", MG),
    CAROTENEA(322, "CARTA", "Carotene, alpha", µg),
    CAROTENEB(321, "CARTB", "Carotene, beta", µg),
    VITAMIN_D3(326, "CHOCAL", "Vitamin D3 (cholecalciferol)", µg),
    CHOLINE(421, "CHOLN", "Choline, total", MG),
    CRYPTOXANTHIN(334, "CRYPX", "Cryptoxanthin, beta", µg),
    COPPER(312, "CU", "Copper, Cu", MG),
    CYSTINE(507, "CYS_G", "Cystine", G),
    ENERGY_KJ(268, "ENERC_KJ", "Energy", KJ),
    VITAMIN_D2(325, "ERGCAL", "Vitamin D2 (ergocalciferol)", µg),
    F10D0(610, "F10D0", "10:00", G),
    F12D0(611, "F12D0", "12:00", G),
    F13D0(696, "F13D0", "13:00", G),
    F14D0(612, "F14D0", "14:00", G),
    F14D1(625, "F14D1", "14:01", G),
    F15D0(652, "F15D0", "15:00", G),
    F15D1(697, "F15D1", "15:01", G),
    F16D0(613, "F16D0", "16:00", G),
    F16D1(626, "F16D1", "16:1 undifferentiated", G),
    F16D1C(673, "F16D1C", "16:1c", G),
    F16D1T(662, "F16D1T", "16:1t", G),
    F17D0(653, "F17D0", "17:00", G),
    F17D1(687, "F17D1", "17:01", G),
    F18D0(614, "F18D0", "18:00", G),
    F18D1(617, "F18D1", "18:1 undifferentiated", G),
    F18D1C(674, "F18D1C", "18:1 c", G),
    F18D1T(663, "F18D1T", "18:1 t", G),
    F18D1TN7(859, "F18D1TN7", "18:1-11t (18:1t n-7)", G),
    F18D2(618, "F18D2", "18:2 undifferentiated", G),
    F18D2CLA(670, "F18D2CLA", "18:2 CLAs", G),
    F18D2CN6(675, "F18D2CN6", "18:2 n-6 c,c", G),
    F18D2TT(669, "F18D2TT", "18:2 t,t", G),
    F18D3(619, "F18D3", "18:3 undifferentiated", G),
    F18D3CN3(851, "F18D3CN3", "18:3 n-3 c,c,c (ALA),omega-3 alpha-linolenic acid", G),
    F18D3CN6(685, "F18D3CN6", "18:3 n-6 c,c,c", G),
    F18D4(627, "F18D4", "18:04", G),
    F20D0(615, "F20D0", "20:00", G),
    F20D1(628, "F20D1", "20:01", G),
    F20D2CN6(672, "F20D2CN6", "20:2 n-6 c,c", G),
    F20D3(689, "F20D3", "20:3 undifferentiated", G),
    F20D3N3(852, "F20D3N3", "20:3 n-3", G),
    F20D3N6(853, "F20D3N6", "20:3 n-6", G),
    F20D4(620, "F20D4", "20:4 undifferentiated", G),
    F20D4N6(855, "F20D4N6", "20:4 n-6", G),
    F20D5(629, "F20D5", "20:5 n-3 (EPA),omega-3 eicosapentaenoic acid", G),
    F21D5(857, "F21D5", "21:05", G),
    F22D0(624, "F22D0", "22:00", G),
    F22D1(630, "F22D1", "22:1 undifferentiated", G),
    F22D4(858, "F22D4", "22:04", G),
    F22D5(631, "F22D5", "22:5 n-3 (DPA)", G),
    F22D6(621, "F22D6", "22:6 n-3 (DHA),omega-3 docosahexaenoic acid", G),
    F24D0(654, "F24D0", "24:00:00", G),
    F24D1C(671, "F24D1C", "24:1 c", G),
    F4D0(607, "F4D0", "4:00", G),
    F6D0(608, "F6D0", "6:00", G),
    F8D0(609, "F8D0", "8:00", G),
    FAMS(645, "FAMS", "Fatty acids, total monounsaturated", G),
    FAPU(646, "FAPU", "Fatty acids, total polyunsaturated", G),
    FATRNM(693, "FATRNM", "Fatty acids, total trans-monoenoic", G),
    FATRNP(695, "FATRNP", "Fatty acids, total trans-polyenoic", G),
    FLD(313, "FLD", "Fluoride, F", µg),
    FOL(417, "FOL", "Folate, total", µg),
    FOLAC(431, "FOLAC", "Folic acid", µg),
    FOLDFE(435, "FOLDFE", "Folate, DFE", µg),
    FOLFD(432, "FOLFD", "Folate, food", µg),
    FRUS(212, "FRUS", "Fructose", G),
    GALACTOSE(287, "GALS", "Galactose", G),
    GLUTAMICACID(515, "GLU_G", "Glutamic acid", G),
    GLUCOSE(211, "GLUS", "Glucose (dextrose)", G),
    GLYCINE(516, "GLY_G", "Glycine", G),
    HISTIDINE(512, "HISTN_G", "Histidine", G),
    HYDROXYPROLINE(521, "HYP", "Hydroxyproline", G),
    ISOLEUCINE(503, "ILE_G", "Isoleucine", G),
    LACTOSE(213, "LACS", "Lactose", G),
    LEUCINE(504, "LEU_G", "Leucine", G),
    LUTEIN(338, "LUT+ZEA", "Lutein + zeaxanthin", µg),
    LYCOPENE(337, "LYCPN", "Lycopene", µg),
    LYSINE(505, "LYS_G", "Lysine", G),
    MALTOSE(214, "MALS", "Maltose", G),
    METHIONINE(506, "MET_G", "Methionine", G),
    MAGNESIUM(304, "MG", "Magnesium, Mg", MG),
    MENAQUINONE(428, "MK4", "Menaquinone-4", µg),
    MANGANESE(315, "MN", "Manganese, Mn", MG),
    NIACIN(406, "NIA", "Niacin", MG),
    VITAMIN_E_ADDED(573, "NULL", "Vitamin E, added", MG),
    VITAMIN_B_12_ADDED(578, "NULL", "Vitamin B-12, added", µg),
    ADJUSTED_PROTEIN(257, "NULL", "Adjusted Protein", G),
    _22_1_T(664, "NULL", "22:1 t", G),
    _22_1_C(676, "NULL", "22:1 c", G),
    _18_3I(856, "NULL", "18:3i", G),
    _18_2_T(665, "NULL", "18:2 t not further defined", G),
    _18_2_I(666, "NULL", "18:2 i", G),
    PHOSPHORUS(305, "P", "Phosphorus, P", MG),
    PANTOTHENIC(410, "PANTAC", "Pantothenic acid", MG),
    PHENYLALANINE(508, "PHE_G", "Phenylalanine", G),
    PHYTOSTEROLS(636, "PHYSTR", "Phytosterols", MG),
    PROLINE(517, "PRO_G", "Proline", G),
    RETINOL(319, "RETOL", "Retinol", µg),
    RIBOFLAVIN(405, "RIBF", "Riboflavin", MG),
    SELENIUM(317, "SE", "Selenium, Se", µg),
    SERINE(518, "SER_G", "Serine", G),
    BETA_SITOSTEROL(641, "SITSTR", "Beta-sitosterol", MG),
    STARCH(209, "STARCH", "Starch", G),
    STIGMASTEROL(638, "STID7", "Stigmasterol", MG),
    SUCROSE(210, "SUCS", "Sucrose", G),
    THEOBROMINE(263, "THEBRN", "Theobromine", MG),
    THIAMIN(404, "THIA", "Thiamin", MG),
    THREONINE(502, "THR_G", "Threonine", G),
    VITAMIN_E(323, "TOCPHA", "Vitamin E (alpha-tocopherol)", MG),
    TOCOPHEROLB(341, "TOCPHB", "Tocopherol, beta", MG),
    TOCOPHEROLD(343, "TOCPHD", "Tocopherol, delta", MG),
    TOCOPHEROLG(342, "TOCPHG", "Tocopherol, gamma", MG),
    TRYPTOPHAN(501, "TRP_G", "Tryptophan", G),
    TYROSINE(509, "TYR_G", "Tyrosine", G),
    VALINE(510, "VAL_G", "Valine", G),
    VITAMIN_A_IU(318, "VITA_IU", "Vitamin A, IU", IU),
    VITAMIN_A_RAE(320, "VITA_RAE", "Vitamin A, RAE", µg),
    VITAMIN_B_12(418, "VITB12", "Vitamin B-12", µg),
    VITAMIN_B_6(415, "VITB6A", "Vitamin B-6", MG),
    VITAMIN_C(401, "VITC", "Vitamin C, total ascorbic acid", MG),
    VITAMIN_D_2_3(328, "VITD", "Vitamin D (D2 + D3)", µg),
    VITAMIN_K(430, "VITK1", "Vitamin K (phylloquinone)", µg),
    DIHYDROPHYLLOQUINONE(429, "VITK1D", "Dihydrophylloquinone", µg),
    WATER(255, "WATER", "Water", G),
    ZINC(309, "ZN", "Zinc, Zn", MG);

    @JsonRawValue
    private final String name, USDA_TAG;

    @JsonIgnore
    private final int tag;
    private final Unit unit;

    Nutrient(int tag, String USDA_TAG, String name, Unit unit) {
        this.tag = tag;
        this.name = name;
        this.USDA_TAG = USDA_TAG;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getUSDA_TAG() {
        return USDA_TAG;
    }

    public int getTag() {
        return tag;
    }

    public Unit getUnit() {
        return unit;
    }
}
