package enums;

/**
 * Enumerates the types of meat options available for sandwiches, each associated with a specific price.
 * This enumeration facilitates the selection of meat for a sandwich, ensuring consistent pricing and
 * offering across the application.
 * This helps in maintaining a standardized menu for sandwiches, allowing for easy customization of orders
 * while providing clear information on pricing for different meat options.
 *
 * @author Soham Patel
 */
public enum SandwichMeat {
    /**
     * Represents beef as a meat option for sandwiches.
     */
    BEEF("Beef", 10.99),

    /**
     * Represents chicken as a meat option for sandwiches.
     */
    CHICKEN("Chicken", 8.99),

    /**
     * Represents fish as a meat option for sandwiches.
     */
    FISH("Fish", 9.99);

    /**
     * The price associated with the meat option.
     */
    private final double price;

    /**
     * The descriptive name of the meat option.
     */
    private final String meat;

    /**
     * Constructs a SandwichMeat enumeration constant with the specified meat type and price.
     *
     * @param meat The descriptive name of the meat option.
     * @param price The price of the meat option.
     */
    SandwichMeat(String meat, double price) {
        this.meat = meat;
        this.price = price;
    }

    /**
     * Retrieves the descriptive name of the meat option.
     *
     * @return The name of the meat.
     */
    public String getMeat() {
        return meat;
    }

    /**
     * Retrieves the price of the meat option.
     *
     * @return The price associated with this meat option.
     */
    public double getPrice() {
        return price;
    }
}
