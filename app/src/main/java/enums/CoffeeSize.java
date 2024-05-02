package enums;

import androidx.annotation.NonNull;

/**
 * Defines the available sizes for coffee orders, each with an associated base price.
 * This enumeration ensures consistency in coffee size selection across the application,
 * facilitating user choice and pricing calculation.
 *
 * @author Soham Patel
 */
public enum CoffeeSize {
    /**
     * The smallest coffee size option.
     */
    SHORT("Short", 1.99),

    /**
     * A medium size, larger than SHORT but smaller than GRANDE.
     */
    TALL("Tall", 2.49),

    /**
     * A large size, offering a generous amount of coffee.
     */
    GRANDE("Grande", 2.99),

    /**
     * The largest size available, for those who need a significant caffeine boost.
     */
    VENTI("Venti", 3.49);

    /**
     * The base price for the coffee size.
     */
    private final double basePrice;

    /**
     * The descriptive name of the coffee size.
     */
    private final String size;

    /**
     * Constructs a CoffeeSize enumeration constant with the specified size and base price.
     *
     * @param size The descriptive name of the coffee size.
     * @param basePrice The base price for this size of coffee.
     */
    CoffeeSize(String size, double basePrice) {
        this.size = size;
        this.basePrice = basePrice;
    }

    /**
     * Retrieves the base price of the coffee size.
     *
     * @return The base price for this coffee size.
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Retrieves the descriptive name of the coffee size.
     *
     * @return The name of the coffee size.
     */
    public String getSize() {
        return size;
    }

    /**
     * Provides a string representation of the coffee size.
     *
     * @return The descriptive name of the coffee size.
     */
    @NonNull
    @Override
    public String toString() {
        return size;
    }
}
