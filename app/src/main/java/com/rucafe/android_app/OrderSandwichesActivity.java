package com.rucafe.android_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rucafe.cs_213_project_v.R;

import java.util.ArrayList;
import java.util.List;

import data.Sandwich;
import enums.BreadChoice;
import enums.SandwichAddOn;
import enums.SandwichMeat;
import impl.OrderService;

/**
 * Activity to handle sandwich ordering, allowing users to choose from various meats, breads, and add-ons,
 * and then add the customized sandwich to the cart.
 *
 * @author Sasanka Paththameistreege
 * @version 1.0
 */
public class OrderSandwichesActivity extends AppCompatActivity {

    /**
     * RadioGroup for selecting the type of bread and protein for the sandwich.
     */
    private RadioGroup breadGroup, proteinGroup;

    /**
     * Checkboxes for selecting additional ingredients such as cheese, lettuce, onions, and tomatoes.
     */
    private CheckBox cheeseCheckBox, lettuceCheckBox, onionsCheckBox, tomatoesCheckBox;

    /**
     * TextView to display the calculated subtotal of the current sandwich order.
     */
    private TextView subTotalEditText;


    /**
     * Initializes the activity, setting up the UI components and event listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sandwiches);

        // Set up ActionBar (or Toolbar)
        Toolbar toolbar = findViewById(R.id.allOrderToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize UI components
        breadGroup = findViewById(R.id.breadRGroup);
        proteinGroup = findViewById(R.id.proteinRGroup);
        cheeseCheckBox = findViewById(R.id.cheeseCBox);
        lettuceCheckBox = findViewById(R.id.lettuceCBox);
        onionsCheckBox = findViewById(R.id.onionsCBox);
        tomatoesCheckBox = findViewById(R.id.tomatoesCBox);
        subTotalEditText = findViewById(R.id.sandwichSubTotalText);
        Button addToCartButton = findViewById(R.id.sandwichAddtoCartButton);

        // Set listeners for UI components
        breadGroup.setOnCheckedChangeListener((group, checkedId) -> updateSubTotal());
        proteinGroup.setOnCheckedChangeListener((group, checkedId) -> updateSubTotal());
        cheeseCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSubTotal());
        lettuceCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSubTotal());
        onionsCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSubTotal());
        tomatoesCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSubTotal());
        addToCartButton.setOnClickListener(v -> onClickAddtoCart());

        // Update subtotal initially
        updateSubTotal();
   }

    /**
     * Calculates and displays the subtotal for the current sandwich configuration.
     */
   @SuppressLint("DefaultLocale")
    private void updateSubTotal() {
        SandwichMeat selectedMeat = getSelectedProtein();
        getSelectedBread();
        List<SandwichAddOn> selectedAddOns = getSelectedAddOns();

        double subtotal = 0;
        if (selectedMeat != null) {
            subtotal += selectedMeat.getPrice();
        }

        for (SandwichAddOn addOn : selectedAddOns) {
            subtotal += addOn.getPrice();
        }

        subTotalEditText.setText(String.format("$ %.2f", subtotal));
    }

    /**
     * Retrieves the selected protein type from the radio buttons.
     *
     * @return The selected SandwichMeat type or null if none is selected.
     */
    private SandwichMeat getSelectedProtein() {
        int selectedId = proteinGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.beefRButton) {
            return SandwichMeat.BEEF;
        } else if (selectedId == R.id.fishRButton) {
            return SandwichMeat.FISH;
        } else if (selectedId == R.id.chickenRButton) {
            return SandwichMeat.CHICKEN;
        }
        return null;
    }

    /**
     * Retrieves the selected bread type from the radio buttons.
     *
     * @return The selected BreadChoice or null if none is selected.
     */
    private BreadChoice getSelectedBread() {
        int selectedId = breadGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.bagelRButton) {
            return BreadChoice.BAGEL;
        } else if (selectedId == R.id.wheatTostRButton) {
            return BreadChoice.WHEAT_BREAD;
        } else if (selectedId == R.id.sourDoughRButton) {
            return BreadChoice.SOUR_DOUGH;
        }
        return null;
    }

    /**
     * Compiles a list of selected add-ons based on the state of various checkboxes.
     *
     * @return A list of selected SandwichAddOns.
     */
    private List<SandwichAddOn> getSelectedAddOns() {
        List<SandwichAddOn> addOns = new ArrayList<>();
        if (lettuceCheckBox.isChecked()) addOns.add(SandwichAddOn.LETTUCE);
        if (tomatoesCheckBox.isChecked()) addOns.add(SandwichAddOn.TOMATOES);
        if (cheeseCheckBox.isChecked()) addOns.add(SandwichAddOn.CHEESE);
        if (onionsCheckBox.isChecked()) addOns.add(SandwichAddOn.ONIONS);
        return addOns;
    }

    /**
     * Handles adding the customized sandwich to the cart upon clicking the 'Add to Cart' button.
     * Automatically resets the checkboxes and sets the bread selection to 'Bagel' and protein to 'Beef' after the sandwich is added.
     */
    private void onClickAddtoCart() {
        Sandwich sandwich = new Sandwich(getSelectedProtein(), getSelectedBread(), getSelectedAddOns(), 1);
        OrderService.getInstance().addItemToCurrentOrder(sandwich);
        resetSandwichForm();
        showAlert("Sandwich added to cart:\n" + sandwich);
    }

    /**
     * Resets the sandwich form by unchecking all checkboxes and setting default selections for the bread and protein.
     * Sets the bread selection to 'Bagel' and the protein selection to 'Beef'.
     */
    private void resetSandwichForm() {
        cheeseCheckBox.setChecked(false);
        lettuceCheckBox.setChecked(false);
        onionsCheckBox.setChecked(false);
        tomatoesCheckBox.setChecked(false);

        // Set default selections for bread and protein
        ((RadioButton) findViewById(R.id.bagelRButton)).setChecked(true);
        ((RadioButton) findViewById(R.id.beefRButton)).setChecked(true);

        // Update the subtotal after resetting the form
        updateSubTotal();
    }

    /**
     * Displays an alert dialog with the message provided, informing the user of the successful addition of the sandwich to the cart.
     * The dialog provides an 'OK' button to dismiss the alert.
     *
     * @param message The message to display in the alert dialog, typically confirmation of the added sandwich.
     */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Item added to Cart")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, id) -> {
                    // This can be used to perform action on OK button click if needed
                });
        builder.create().show();
    }
}