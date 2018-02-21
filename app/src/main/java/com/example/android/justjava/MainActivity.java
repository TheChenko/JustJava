package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox wc = findViewById(R.id.wc_checkbox);
        CheckBox ch = findViewById(R.id.choc_checkbox);
        EditText ed = findViewById(R.id.username);
        String name = ed.getText().toString();
        boolean hasChocolate = ch.isChecked();
        boolean hasWhippedCream = wc.isChecked();

        int price = calculatePrice(hasChocolate, hasWhippedCream);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, hasWhippedCream, hasChocolate, name));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean chocolate, boolean cream) {
        int ppc = 5;

        if(chocolate){
            ppc = ppc + 1;
        }
        if (cream) {
                ppc = ppc + 2;
        }
        int price = quantity * ppc;

        return price;
    }

    private String createOrderSummary(int price, boolean whippedCream, boolean chocolate, String name){
        String msg = "Name: " + name +
                    "\nAdd whipped cream? " + whippedCream +
                    "\nAdd chocolate?" + chocolate +
                    "\nQuantity: " + quantity +
                    "\nTotal: £" + price +
                    "\nThank you!";
        return msg;

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this, "Cannot go above 100", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            Toast.makeText(this, "Cannot go below 1", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}