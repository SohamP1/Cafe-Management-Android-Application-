package enums;

/**
 * Enumerates the different types of donuts available, each with a specific base price.
 * This enumeration facilitates the selection of donut types in the ordering process,
 * ensuring consistency in pricing and offering across the application.
 *
 * @author Soham Patel
 */
public enum DonutType {
    /**
     * Represents a yeast donut, known for its light, airy texture.
     */
    YEAST("Yeast", 1.79),

    /**
     * Represents a cake donut, which has a denser texture compared to yeast donuts.
     */
    CAKE("Cake", 1.89),

    /**
     * Represents donut holes, which are small, bite-sized dough balls typically made from the dough cutouts of ring donuts.
     */
    DONUT_HOLE("Donut Hole", 0.39);

    /**
     * The base price for the donut type.
     */
    private final double price;

    /**
     * The descriptive name of the donut type.
     */
    private final String donutType;

    /**
     * Constructs a DonutType enumeration constant with the specified type and base price.
     *
     * @param donutType The descriptive name of the donut type.
     * @param price The base price for this type of donut.
     */
    DonutType(String donutType, double price) {
        this.price = price;
        this.donutType = donutType;
    }

    /**
     * Retrieves the base price of the donut type.
     *
     * @return The base price for this donut type.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retrieves the descriptive name of the donut type.
     *
     * @return The name of the donut type.
     */
    public String getDonutType() {
        return donutType;
    }
}
