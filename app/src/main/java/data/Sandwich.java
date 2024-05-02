package data;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import enums.BreadChoice;
import enums.SandwichAddOn;
import enums.SandwichMeat;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Represents a sandwich order, detailing the meat, bread, optional add-ons, quantity,
 * and calculates the total price.
 * This class extends the MenuItem abstract class, providing a specific implementation
 * for sandwich items.
 *
 * @author Soham Patel
 */
public class Sandwich extends MenuItem {

    /**
     * The type of meat used in the sandwich, determining part of its overall flavor and cost.
     */
    private final SandwichMeat meat;

    /**
     * The type of bread chosen for the sandwich, contributing to both its texture and taste.
     */
    private final BreadChoice bread;

    /**
     * A list of additional toppings or condiments added to the sandwich, enhancing its flavor and potentially increasing its price.
     * This list can be modified after the sandwich is created to add more add-ons.
     */
    private final List<SandwichAddOn> addOns;

    /**
     * The quantity of this particular sandwich configuration being ordered, used to calculate the total price.
     */
    private final int quantity;

    /**
     * Constructs a Sandwich object with specified meat, bread, add-ons, and quantity.
     *
     * @param meat     The type of meat for the sandwich.
     * @param bread    The type of bread for the sandwich.
     * @param addOns   A list of add-ons for the sandwich.
     * @param quantity The quantity of sandwiches ordered.
     */
    public Sandwich(SandwichMeat meat, BreadChoice bread, List<SandwichAddOn> addOns, int quantity) {
        this.meat = meat;
        this.bread = bread;
        this.addOns = addOns;
        this.quantity = quantity;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    /**
     * Calculates the total price of the sandwich order, considering the meat, add-ons, and quantity.
     *
     * @return The total price of the sandwich order.
     */
    @Override
    public double price() {
        double price = meat.getPrice() + (addOns != null ? addOns.stream().mapToDouble(SandwichAddOn::getPrice).sum() : 0);
        double totalPrice = price * quantity;
        DecimalFormat df = new DecimalFormat("#.##");
        totalPrice = Double.parseDouble(df.format(totalPrice));

        return totalPrice;
    }

    /**
     * Generates a string representation of the sandwich order, including meat, bread, add-ons, quantity, and total price.
     *
     * @return A string detailing the sandwich order specifics.
     */
    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        StringBuilder description = new StringBuilder("Sandwich: ");
        description.append(meat.getMeat()).append(" on ").append(bread.getBreadType()).append(" bread");
        if (addOns != null && !addOns.isEmpty()) {
            description.append(" + ");
            addOns.forEach(addOn -> description.append(addOn.getAddOn()).append(", "));
            description.setLength(description.length() - 2);
        }
        description.append(" - Quantity: ").append(quantity);
        description.append(String.format(" (Price: $%.2f)", price()));
        return description.toString();
    }

}
