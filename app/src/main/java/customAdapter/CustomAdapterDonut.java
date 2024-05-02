package customAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rucafe.cs_213_project_v.R;
import data.Donut;

import java.util.List;

/**
 * Adapter class for a RecyclerView that displays a list of donuts, allowing users to select quantities
 * and view donut details.
 * @author Soham Patel
 * @version 1.0
 */
public class CustomAdapterDonut extends RecyclerView.Adapter<CustomAdapterDonut.DonutViewHolder> {
    /**
     * List of donuts.
     */
    private final List<Donut> donuts;

    /**
     * TextView to display the subtotal.
     */
    private final TextView subtotalTextView;

    /**
     * Listener for handling click events on donuts.
     */
    private OnDonutClickListener onDonutClickListener;


    /**
     * Constructs a new CustomAdapterDonut.
     *
     * @param donuts List of donut objects to be displayed.
     * @param subtotalTextView TextView to display the total price for selected donuts.
     */
    public CustomAdapterDonut(List<Donut> donuts, TextView subtotalTextView) {
        this.donuts = donuts;
        this.subtotalTextView = subtotalTextView;
    }

    @NonNull
    @Override
    public DonutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donut_item, parent, false);
        return new DonutViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DonutViewHolder holder, int position) {
        Donut donut = donuts.get(position);
        holder.bind(donut);
    }

    @Override
    public int getItemCount() {
        return donuts.size();
    }

    /**
     * Sets the listener for click events on donut items.
     *
     * @param listener The listener to handle click events.
     */
    public void setOnDonutClickListener(OnDonutClickListener listener) {
        this.onDonutClickListener = listener;
    }

    /**
     * Interface to handle click events on donuts within the adapter.
     */
    public interface OnDonutClickListener {
        void onDonutClick(int position, int quantity);
    }

    /**
     * Returns the list of donuts.
     *
     * @return A list of donuts.
     */
    public List<Donut> getDonuts() {
        return donuts;
    }

    /**
     * Updates and displays the subtotal for all selected donuts.
     */
    @SuppressLint("DefaultLocale")
    public void updateSubtotal() {
        double subtotal = 0;
        for (Donut donut : donuts) {
            subtotal += donut.price();
        }
        subtotalTextView.setText(String.format("$ %.2f", subtotal));
    }

    /**
     * ViewHolder class for donut items in the RecyclerView.
     * @author Soham Patel
     * @version 1.0
     */
    public class DonutViewHolder extends RecyclerView.ViewHolder {
        /** TextView displaying the type of the donut, such as "Glazed" or "Chocolate Frosted". */
        private final TextView typeTextView;

        /** TextView displaying the specific flavor of the donut, such as "Vanilla" or "Strawberry". */
        private final TextView flavorTextView;

        /** Spinner for selecting the quantity of this particular donut to order. */
        private final Spinner qtySpinner;

        /** ImageView displaying an image representative of the donut type and flavor. */
        private final ImageView imageView;

        /** TextView displaying the price of the donut. */
        private final TextView donutPriceTextView;


        /**
         * Constructor for the DonutViewHolder.
         *
         * @param itemView The view of the RecyclerView item.
         */
        public DonutViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.typeofDonutTextView);
            flavorTextView = itemView.findViewById(R.id.donutFlavorTextView);
            qtySpinner = itemView.findViewById(R.id.donutQtySpinner);
            imageView = itemView.findViewById(R.id.donutImageView);
            donutPriceTextView = itemView.findViewById(R.id.donutPriceTextView);

            qtySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION && onDonutClickListener != null) {
                        int quantity = Integer.parseInt(parent.getItemAtPosition(position).toString());
                        onDonutClickListener.onDonutClick(adapterPosition, quantity);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        /**
         * Binds a Donut object to this ViewHolder.
         *
         * @param donut The Donut object to bind.
         */
        public void bind(Donut donut) {
            typeTextView.setText(donut.getType().getDonutType());
            flavorTextView.setText(donut.getFlavor());
            imageView.setImageResource(getImageResourceForDonut(donut));
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(itemView.getContext(), R.array.qty_options, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            qtySpinner.setAdapter(adapter);
            qtySpinner.setSelection(donut.getQuantity());
            double price = donut.getType().getPrice();
            donutPriceTextView.setText(String.format("$ %.2f", price)); // Display the price
        }

        /**
         * Retrieves the image resource for a given Donut.
         *
         * @param donut The Donut to retrieve the image resource for.
         * @return The resource ID for the image.
         */
        private int getImageResourceForDonut(Donut donut) {
            String flavorName = donut.getFlavor().toLowerCase().replace(" ", "_");
            String typeName = donut.getType().getDonutType().toLowerCase().replace(" ", "_");
            String imageName = flavorName + "_" + typeName + "_donut";
            @SuppressLint("DiscouragedApi") int resourceId = itemView.getResources().getIdentifier(imageName, "drawable", itemView.getContext().getPackageName());

            // If resource is not found, return default image resource
            if (resourceId == 0) {
                return R.drawable.default_donut;
            }
            return resourceId;

        }
    }
}
