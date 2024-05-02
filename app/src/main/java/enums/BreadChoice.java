package enums;

/**
 * Enumerates the types of bread choices available for sandwiches.
 * Each choice is associated with a descriptive name to ensure clarity and consistency across the ordering system.
 * <p>
 * This enumeration facilitates the selection process for bread types in sandwich orders,
 * enhancing user experience by providing recognizable options.
 * </p>
 *
 * @author Soham Patel
 */
public enum BreadChoice {
    /**
     * Represents a bagel bread option for sandwiches.
     */
    BAGEL("Bagel"),

    /**
     * Represents a wheat bread option, offering a healthier choice for sandwiches.
     */
    WHEAT_BREAD("Wheat Bread"),

    /**
     * Represents sour dough bread, known for its distinct taste and texture.
     */
    SOUR_DOUGH("Sour Dough");

    /**
     * The descriptive name of the bread choice.
     */
    private final String breadType;

    /**
     * Constructs a BreadChoice enum constant with the specified descriptive name.
     *
     * @param breadType The descriptive name of the bread type.
     */
    BreadChoice(String breadType) {
        this.breadType = breadType;
    }

    /**
     * Retrieves the descriptive name of the bread choice.
     *
     * @return The descriptive name of this bread type.
     */
    public String getBreadType() {
        return this.breadType;
    }
}
