package pl.krasnowski.DigitalKitchen.model.domain.diet;

/**
 * Contains percentages of nutrients in diet.
 */
public enum DietType {
    MODERATE(0.5, 0.25, 0.25),
    BALANCED(0.40, 0.30, 0.30),
    OPTIMAL(0.15, 0.20, 0.65),
    RICH_PROTEIN(0.40, 0.30, 0.30),
    LOW_FAT(0.6, 0.25, 0.15),
    LOW_PROTEIN(0.6, 0.1, 0.3),
    LOW_CARBOHYDRATE(0.1, 0.45, 0.45),
    KETOGENIC(0.5, 0.35, 0.6),
    CUSTOM(0, 0, 0);

    private final double carbohydrates, proteins, fat;

    DietType(double carbohydrates, double proteins, double fat) {
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.fat = fat;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getProteins() {
        return proteins;
    }

    public double getFat() {
        return fat;
    }
}