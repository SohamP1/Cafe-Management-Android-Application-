package com.rucafe.android_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rucafe.cs_213_project_v.R;

import java.util.List;

import customAdapter.CustomAdapterCurrentOrder;
import data.MenuItem;
import impl.Order;
import impl.OrderService;

/**
 * Activity class to display and manage the current order.
 * Allows users to view, modify, and place their current order.
 *
 * @author Soham Patel
 * @version 1.0
 */
public class CurrentOrder extends AppCompatActivity {
    /** ListView to display items in the current order. */
    private ListView listView;

    /** TextView to display the subtotal of the current order. */
    private TextView subTotalEditText;

    /** TextView to display the sales tax for the current order. */
    private TextView salesTaxEditText;

    /** TextView to display the total amount for the current order. */
    private TextView totalEditText;

    /** Button to remove a selected item from the current order. */
    private Button removeItemButton;

    /** Button to finalize and place the current order. */
    private Button placeOrderButton;

    /** List holding the items currently in the order. */
    private List<MenuItem> currentOrderItems;

    /** Adapter for managing display of current order items in the ListView. */
    private CustomAdapterCurrentOrder adapter;


    /**
     * Called when the activity is starting. This method is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById to programmatically interact
     * with widgets in the UI, calling setListAdapter(ArrayAdapter) to connect a ListView to its data,
     * and so on.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        Toolbar toolbar = findViewById(R.id.allOrderToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.currentOrderListView);
        subTotalEditText = findViewById(R.id.subTotalTextNumber);
        salesTaxEditText = findViewById(R.id.salseTexTextNumber);
        totalEditText = findViewById(R.id.totalAmountTextNumber);
        removeItemButton = findViewById(R.id.RemoveItemButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);

        currentOrderItems = OrderService.getInstance().getCurrentOrderItems();
        adapter = new CustomAdapterCurrentOrder(this, currentOrderItems);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        updateOrderSummary();
        setupListeners();
    }

    /**
     * Sets up click listeners for buttons.
     * Handles removal of selected item and placing the order.
     */
    private void setupListeners() {
        removeItemButton.setOnClickListener(v -> removeSelectedItem());
        placeOrderButton.setOnClickListener(v -> placeOrder());
    }

    /**
     * Updates the TextViews showing the subtotal, sales tax, and total for the current order.
     * Uses OrderService to calculate the subtotal and applies predefined sales tax rate.
     */
    @SuppressLint("DefaultLocale")
    private void updateOrderSummary() {
        double subtotal = OrderService.getInstance().getCurrentOrderItems().stream().mapToDouble(MenuItem::price).sum();
        double salesTax = subtotal * Order.salesTax;
        double total = subtotal + salesTax;

        subTotalEditText.setText(String.format("$ %.2f", subtotal));
        salesTaxEditText.setText(String.format("$ %.2f", salesTax));
        totalEditText.setText(String.format("$ %.2f", total));
    }

    /**
     * Removes the selected item from the current order.
     * Validates the item position and ensures there are items to remove.
     */
    private void removeSelectedItem() {
        int position = listView.getCheckedItemPosition();
        if (position == ListView.INVALID_POSITION || currentOrderItems.isEmpty()) {
            Toast.makeText(this, "Please select a valid item to remove.", Toast.LENGTH_SHORT).show();
            return;
        }
        OrderService.getInstance().removeItemAt(position);
        adapter.notifyDataSetChanged();
        listView.setItemChecked(position, false);
        updateOrderSummary();
        Toast.makeText(this, "Item removed.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Finalizes the current order by processing it through OrderService.
     * Ensures there are items to place an order, otherwise informs the user.
     */
    private void placeOrder() {
        if (currentOrderItems.isEmpty()) {
            Toast.makeText(this, "No items in the current order to place.", Toast.LENGTH_SHORT).show();
            return;
        }
        OrderService.getInstance().finalizeCurrentOrder();
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show();
        finish();  // Optionally close the activity
    }

}
