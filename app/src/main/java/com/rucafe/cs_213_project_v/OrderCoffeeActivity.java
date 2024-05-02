package com.rucafe.cs_213_project_v;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import data.Coffee;
import enums.CoffeeAddIn;
import enums.CoffeeSize;
import impl.OrderService;

/**
 * Activity class to handle coffee ordering.
 * Allows users to select coffee size, add-ins, quantity, and add them to the cart.
 *
 * @author Sasanka Paththameistreege
 * @version 1.0
 */
public class OrderCoffeeActivity extends AppCompatActivity {
    /** Maximum quantity of coffee that can be ordered. */
    private static final int COFFEE_MAXSIZE = 10;

    /** ImageView to display an image representative of the selected coffee size. */
    private ImageView coffeeImageView;

    /** RadioGroup for users to select the size of their coffee. */
    private RadioGroup coffeeSizeRadioGroup;

    /** CheckBoxes for selecting optional add-ins such as sweet cream, mocha, caramel, french vanilla, and irish cream. */
    private CheckBox sweetCreamCheckBox, mochaCheckBox, caramelCheckBox, frenchVanillaCheckBox, irishCreamCheckBox;

    /** Spinner for selecting the quantity of coffee to order. */
    private Spinner coffeeQuantitySpinner;

    /** TextView to display the calculated subtotal for the current coffee order based on size, add-ins, and quantity. */
    private TextView coffeeSubTotalText;

    /** Button to add the configured coffee order to the shopping cart. */
    private Button addToCartButton;


    /**
     * Initializes the activity, setting up the UI components and event listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_coffee);

        Toolbar toolbar = findViewById(R.id.coffeeToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeUIComponents();
        setupListeners();
    }

    /**
     * Initializes all UI components used in the activity.
     */
    private void initializeUIComponents() {
        coffeeImageView = findViewById(R.id.coffeeImageView);
        coffeeSizeRadioGroup = findViewById(R.id.coffeeSizeRadioGroup);
        RadioButton shortRadioButton = findViewById(R.id.shortRButton);
        sweetCreamCheckBox = findViewById(R.id.sweetCreamCBox);
        mochaCheckBox = findViewById(R.id.mochaCBox);
        caramelCheckBox = findViewById(R.id.caramelCBox);
        frenchVanillaCheckBox = findViewById(R.id.frenchVanillaCBox);
        irishCreamCheckBox = findViewById(R.id.irishCreamCBox);
        coffeeQuantitySpinner = findViewById(R.id.coffeeQtySpinner);
        coffeeSubTotalText = findViewById(R.id.coffeeSubTotalText);
        addToCartButton = findViewById(R.id.coffeeAddtoCartButton);

        // Initialize and set the coffee quantity spinner
        Integer[] quantities = new Integer[COFFEE_MAXSIZE];
        for (int i = 0; i < quantities.length; i++) {
            quantities[i] = i + 1;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quantities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coffeeQuantitySpinner.setAdapter(adapter);

        shortRadioButton.setChecked(true);
        setImageBasedOnCoffeeSize(CoffeeSize.SHORT);
    }

    /**
     * Sets up listeners for all interactive UI components.
     */
    private void setupListeners() {
        coffeeSizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> updateSubTotal());
        sweetCreamCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSubTotal());
        mochaCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSubTotal());
        caramelCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSubTotal());
        frenchVanillaCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSubTotal());
        irishCreamCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSubTotal());
        coffeeQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateSubTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        addToCartButton.setOnClickListener(v -> onClickAddtoCart());
    }

    /**
     * Updates the subtotal displayed in the TextView based on selected options and quantity.
     */
    @SuppressLint("DefaultLocale")
    private void updateSubTotal() {
        double subtotal = 0;
        CoffeeSize selectedSize = getCoffeeSizeFromRadioButton();
        int quantity = (int) coffeeQuantitySpinner.getSelectedItem();

        if (selectedSize != null) {
            subtotal += selectedSize.getBasePrice();
            setImageBasedOnCoffeeSize(selectedSize);
        }

        subtotal += addAddInCost(sweetCreamCheckBox, CoffeeAddIn.SWEET_CREAM);
        subtotal += addAddInCost(mochaCheckBox, CoffeeAddIn.MOCHA);
        subtotal += addAddInCost(caramelCheckBox, CoffeeAddIn.CARAMEL);
        subtotal += addAddInCost(frenchVanillaCheckBox, CoffeeAddIn.FRENCH_VANILLA);
        subtotal += addAddInCost(irishCreamCheckBox, CoffeeAddIn.IRISH_CREAM);

        subtotal *= quantity;
        coffeeSubTotalText.setText(String.format("$ %.2f", subtotal));
    }

    /**
     * Calculates additional cost based on whether a CheckBox is checked and the associated add-in price.
     *
     * @param checkBox The checkbox associated with the add-in.
     * @param addIn The CoffeeAddIn enum value representing the add-in.
     * @return the cost of the add-in if the checkbox is checked, otherwise 0.
     */
    private double addAddInCost(CheckBox checkBox, CoffeeAddIn addIn) {
        return checkBox.isChecked() ? addIn.getPrice() : 0;
    }

    /**
     * Retrieves the CoffeeSize based on which RadioButton is checked in the RadioGroup.
     *
     * @return The CoffeeSize enum corresponding to the selected radio button.
     */
    private CoffeeSize getCoffeeSizeFromRadioButton() {
        int selectedId = coffeeSizeRadioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.shortRButton) {
            return CoffeeSize.SHORT;
        } else if (selectedId == R.id.tallRButton) {
            return CoffeeSize.TALL;
        } else if (selectedId == R.id.grandeRButton) {
            return CoffeeSize.GRANDE;
        } else if (selectedId == R.id.ventiRButton) {
            return CoffeeSize.VENTI;
        }
        return null;
    }

    /**
     * Sets the coffee image based on the selected size.
     *
     * @param size The selected CoffeeSize.
     */
    private void setImageBasedOnCoffeeSize(CoffeeSize size) {
        int imageResId = R.drawable.coffee;
        switch (size) {
            case SHORT:
                imageResId = R.drawable.short_coffee;
                break;
            case TALL:
                imageResId = R.drawable.tall_coffee;
                break;
            case GRANDE:
                imageResId = R.drawable.grande_coffee;
                break;
            case VENTI:
                imageResId = R.drawable.venti_coffee;
                break;
        }
        coffeeImageView.setImageResource(imageResId);
    }

    /**
     * Compiles a list of all selected coffee add-ins.
     * Each add-in corresponds to a CheckBox that, when checked, indicates the user's desire to include that particular add-in in their coffee order.
     *
     * @return A list of {@link CoffeeAddIn} enumerations representing the selected add-ins.
     */
    private List<CoffeeAddIn> getSelectedAddIns() {
        List<CoffeeAddIn> addIns = new ArrayList<>();
        if(sweetCreamCheckBox.isChecked()) addIns.add(CoffeeAddIn.SWEET_CREAM);
        if(frenchVanillaCheckBox.isChecked()) addIns.add(CoffeeAddIn.FRENCH_VANILLA);
        if(irishCreamCheckBox.isChecked()) addIns.add(CoffeeAddIn.IRISH_CREAM);
        if(mochaCheckBox.isChecked()) addIns.add(CoffeeAddIn.MOCHA);
        if(caramelCheckBox.isChecked()) addIns.add(CoffeeAddIn.CARAMEL);
        return addIns;
    }

    /**
     * Handles the event when the 'Add to Cart' button is clicked.
     * This method checks for the selected coffee size, compiles the selected add-ins,
     * creates a Coffee object, adds it to the current order, and shows an alert.
     * It then resets the form for the next order.
     */
    private void onClickAddtoCart() {
        CoffeeSize size = getCoffeeSizeFromRadioButton();
        if (size == null) {
            Toast.makeText(this, "Please select a coffee size.", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = (int) coffeeQuantitySpinner.getSelectedItem();
        List<CoffeeAddIn> addIns = getSelectedAddIns();

        Coffee coffee = new Coffee(size, addIns, quantity);
        OrderService.getInstance().addItemToCurrentOrder(coffee);
        showAlert("Coffee added to cart:\n" + coffee);

        resetOrderForm(); // Reset the form for the next order
    }

    /**
     * Resets the form elements to their default state.
     * This includes unchecking all add-in checkboxes, setting the coffee size to 'Short',
     * and resetting the quantity spinner to its initial value. It also updates the subtotal display.
     */
    private void resetOrderForm() {
        sweetCreamCheckBox.setChecked(false);
        mochaCheckBox.setChecked(false);
        caramelCheckBox.setChecked(false);
        frenchVanillaCheckBox.setChecked(false);
        irishCreamCheckBox.setChecked(false);

        RadioButton shortRadioButton = findViewById(R.id.shortRButton);
        shortRadioButton.setChecked(true);
        setImageBasedOnCoffeeSize(CoffeeSize.SHORT);

        coffeeQuantitySpinner.setSelection(0);

        updateSubTotal();
    }

    /**
     * Displays an alert dialog with a custom message.
     * The dialog confirms that an item was added to the cart and offers an 'OK' button that,
     * when pressed, triggers the form reset.
     *
     * @param message The message to be displayed in the alert dialog.
     */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Item added to Cart")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, id) -> resetOrderForm())
                .show();
    }

}
