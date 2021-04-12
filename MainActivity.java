package com.example.android.justjava;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.EditText;
import java.text.NumberFormat;
import android.text.Editable;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 0;
    private int pricePerCup = 0;
    private String name = "";
    private boolean cream = false;
    private boolean choco = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity() {
        int qu = quantity;
        TextView quantity = (TextView) findViewById(R.id.quantity);
        quantity.setText("" + qu);
    }

    /**
     * This method displays the price
     * */
    private String displayPrice(){
        int num = calculatePrice(quantity);
        String p = NumberFormat.getCurrencyInstance().format(num);
        return (p);
    }

    /**
     * This method gets the name of customer.
     * @return name
     */
    private Editable getName(){
        EditText edit_name = (EditText) findViewById(R.id.name_edit_text);
        return (edit_name.getText());
    }

    /**
     * Method for order summary
     */
    private String orderSummary(){
        String s = "Name: " + name + "\n";
        s = s + "Add Whipped Cream? " + cream + "\n";
        s = s + "Add Chocolate?" + choco + "\n";
        s = s + "Quantity: " + quantity + "\n";
        s = s + "Total: " + displayPrice() + "\n";
        s = s + getString(R.string.thank_you);
        return s;
    }

    /**Calculate price method*/
    private int calculatePrice(int q){
        int pricePerCoffee = 5;
        int creamPrice = 1;
        int chocoPrice = 2;
        pricePerCup = pricePerCoffee;
        if(cream){
            pricePerCup = pricePerCup + creamPrice;
        }
        if(choco){
            pricePerCup = pricePerCup + chocoPrice;
        }
        return (pricePerCup * q);
    }

    public void plus(View view){
        quantity ++;
        displayQuantity();
    }
    public void minus(View view){
        if(quantity>0){
            quantity--;
        }
        displayQuantity();
    }

    /**
     * This method checks if the whipped_cream check box is checked or unchecked.
     */
    public void onCheckBoxClicked(View view){
        boolean check = ((CheckBox) view).isChecked();
        switch(view.getId()){
            case R.id.whipped_cream_cb:
                if(check){
                    cream = true;
                }
                else{
                    cream = false;
                }
                break;
            case R.id.chocolate_cb:
                if(check){
                    choco = true;
                }
                else{
                    choco = false;
                }
                break;
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText edit_name = (EditText) findViewById(R.id.name_edit_text);
        if(TextUtils.isEmpty(getName())){
            edit_name.setError("Name is required!");
        }
        else{
            name = String.valueOf(getName());
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order from: " + name);
            intent.putExtra(Intent.EXTRA_TEXT, orderSummary());
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }

    }
}