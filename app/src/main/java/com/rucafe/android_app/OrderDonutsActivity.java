package com.rucafe.android_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rucafe.cs_213_project_v.R;

import java.util.ArrayList;
import java.util.List;

import customAdapter.CustomAdapterDonut;
import data.Donut;
import enums.DonutFlavor;
import enums.DonutType;
import impl.OrderService;

/**
 * Activity for ordering donuts, where users can select from various donut types and flavors,
 * adjust quantities, and add them to the cart.
 *
 * @author Soham Patel
 * @version 1.0
 */
public class OrderDonutsActivity extends AppCompatActivity implements CustomAdapterDonut.OnDonutClickListener{
    private CustomAdapterDonut adapter;

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
        setContentView(R.layout.activity_order_donuts);

        // Set up ActionBar (or Toolbar)
        Toolbar toolbar = findViewById(R.id.allOrderToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.donutRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Generate donut data
        List<Donut> donuts = generateDonuts();

        // Initialize adapter with the subtotalTextView reference
        TextView subtotalTextView = findViewById(R.id.donutSubTotalTextNumber);
        adapter = new CustomAdapterDonut(donuts, subtotalTextView);

        // Set adapter
        recyclerView.setAdapter(adapter);

        // Set up adapter and click listener
        adapter.setOnDonutClickListener(this);
        recyclerView.setAdapter(adapter);

        // Set click listener for Add to Cart button
        Button addToCartButton = findViewById(R.id.donutAddtoCartButton);
        addToCartButton.setOnClickListener(v -> onClickAddtoCart());

    }

    /**
     * Generates a list of donuts based on all available flavors.
     *
     * @return A list of {@link Donut} objects, one for each available flavor.
     */
    private List<Donut> generateDonuts() {
        List<Donut> donutList = new ArrayList<>();

        for (DonutFlavor flavor : DonutFlavor.values()) {
            DonutType type = flavor.getAssociatedType();
            donutList.add(new Donut(type, 0, flavor.getFlavorName()));
        }

        return donutList;
    }

    /**
     * Handles actions for menu item selections in the Toolbar.
     *
     * @param item The menu item that was selected.
     * @return true to consume the menu selection here, or false to allow normal menu processing to continue.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Responds to click events from the donut items in the RecyclerView.
     *
     * @param position The position of the clicked donut item in the list.
     * @param quantity The new quantity set for the donut.
     */
    @Override
    public void onDonutClick(int position, int quantity) {
        Donut donut = adapter.getDonuts().get(position);
        Donut updatedDonut = new Donut(donut.getType(), quantity, donut.getFlavor());
        adapter.getDonuts().set(position, updatedDonut);
        adapter.updateSubtotal(); // Call updateSubtotal() after changing the quantity
    }

    /**
     * Handles the addition of selected donuts to the current order when the 'Add to Cart' button is clicked.
     */
    @SuppressLint("NotifyDataSetChanged")
    private void onClickAddtoCart() {
        boolean donutsSelected = false; // Flag to track if any donut is selected
        StringBuilder string = new StringBuilder();
        for (Donut donut : adapter.getDonuts()) {
            if (donut.getQuantity() > 0) {
                donutsSelected = true;
                // Check if the donut is already in the current order
                boolean alreadyAdded = OrderService.getInstance().getCurrentOrderItems().contains(donut);
                // If not already added, add it temporarily
                if (!alreadyAdded) {
                    OrderService.getInstance().addItemToCurrentOrder(donut);
                }
                // Append the donut details to the string
                string.append(donut).append("\n");
            }
        }

        if (!donutsSelected) {
            // Show a message indicating no donuts are selected
            Toast.makeText(this, "Please select a donut to add it to cart.", Toast.LENGTH_SHORT).show();
            return; // Exit the method without further processing
        }
        // Reset donut quantities to 0 by clearing the adapter list and repopulating it
        adapter.getDonuts().clear();
        adapter.getDonuts().addAll(generateDonuts());

        adapter.notifyDataSetChanged(); // Notify the adapter of data change
        showAlert(string.toString()); // Show alert with items added to the cart
    }

    /**
     * Displays an AlertDialog with the details of items added to the cart.
     *
     * @param message The message to be displayed, listing the added items.
     */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Item added to Cart")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, id) -> {
                    // Positive button click action (if needed)
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}