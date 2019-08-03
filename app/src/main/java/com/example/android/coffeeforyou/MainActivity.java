package com.example.android.coffeeforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity.java","hey this is my first log text");
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 2;
    boolean wh_cream =false;

    public void increment(View view){
        if(quantity!=20) {
            quantity++;
        }
        display(quantity);
        displayPrice(quantity * 5);
    }

    public void decrement(View view){
        if(quantity!=1){
            quantity--;
        }
        display(quantity);
        displayPrice(quantity * 5);
    }

    public void submitOrder(View view) {

        int finalPrice=calculate();
        String top1="No",top2="No";
        StringBuilder priceMessage= new StringBuilder();

        EditText nametxt= findViewById(R.id.name);
        String name = nametxt.getText().toString();

        EditText emailtxt =findViewById(R.id.email);
        String email = emailtxt.getText().toString();

        String pmsg= "Name: " + name + "\nCoffee Price: $" + finalPrice ;
        String qty= "\nCups of Coffees: " + quantity + "\nAdd Whipped Cream: "  ;
        priceMessage.append(pmsg+qty);

        CheckBox checkTop1 = findViewById(R.id.top1);
        CheckBox checkTop2 = findViewById(R.id.top2);

        if(checkTop1.isChecked()){
            top1="Yes";
            finalPrice=finalPrice +(quantity);
            priceMessage.append(top1);
        }
        else priceMessage.append(top1);

        // finalDisplay(priceMessage);

        priceMessage.append("\nAdd Chocolate: ");

        if(checkTop2.isChecked()){
            top2="Yes";
            finalPrice=finalPrice +(quantity);
            priceMessage.append(top2);
        }
        else priceMessage.append(top2);

        priceMessage.append("\nTotal price: $" + finalPrice);

        String final_value =String.valueOf(priceMessage);

        //INTENT TO SEND MAIL
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        //URI is a type of content provider
        intent.setData(Uri.parse("mailto: ")); //only email apps will handle this
        intent.putExtra(Intent.EXTRA_EMAIL,new String[] {email});
        intent.putExtra(Intent.EXTRA_SUBJECT,"You Coffee Order Details");
        intent.putExtra(Intent.EXTRA_TEXT,"\nDETAILS\n" + final_value);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

    private int calculate(){
        return quantity*5;
    }



    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int quantity) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = findViewById(R.id.orderSummary);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }


    private void finalDisplay(StringBuilder msg){
        TextView finalMsg = findViewById(R.id.orderSummary);
        finalMsg.setText(msg);
    }

}
