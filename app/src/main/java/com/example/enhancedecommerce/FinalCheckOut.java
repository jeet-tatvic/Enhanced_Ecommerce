package com.example.enhancedecommerce;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Random;

public class FinalCheckOut extends AppCompatActivity {
    private FirebaseAnalytics firebaseAnalytics;
    Bundle finalpurchase,purchase_ga3;
    Button purchase;

    long trid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long startTime = System.currentTimeMillis();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_check_out);



        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        finalpurchase = getIntent().getBundleExtra("ga4");
        purchase = findViewById(R.id.purchase);



//        Random rand = new Random();
//        trid = rand.nextInt();
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle payment = new Bundle();
                payment.putLong(FirebaseAnalytics.Param.TRANSACTION_ID,12789);
                payment.putString(FirebaseAnalytics.Param.AFFILIATION,"Amazon Store");
                payment.putParcelableArray(FirebaseAnalytics.Param.ITEMS,new Parcelable[]{finalpurchase});


                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.PURCHASE,payment);

                purchase_ga3 = getIntent().getBundleExtra("ga3");

                ArrayList<Bundle> items = new ArrayList<Bundle>();
                items.add(purchase_ga3);

                Bundle pur = new Bundle();
                //pur.putDouble(Param.VALUE, purchase_ga3.getDouble(Param.VALUE));
//                pur.putDouble(Param.VALUE, );
                pur.putParcelableArrayList("items",items);
                pur.putDouble(Param.VALUE,purchase_ga3.getLong(Param.QUANTITY)*purchase_ga3.getDouble(Param.PRICE));
                pur.putLong(Param.TRANSACTION_ID,12789);
                pur.putDouble( Param.TAX, 2.85 );
                pur.putDouble( Param.SHIPPING, 5.34 );
                pur.putString(Param.AFFILIATION,"Amazon Store");
//                pur.putBundle("pur",purchase_ga3);
                pur.putString("is_UA","yes");
                firebaseAnalytics.logEvent(Event.ECOMMERCE_PURCHASE,pur);

//                firebaseAnalytics.logEvent(Event.,pur);



            }

        });
        long endTime= System.currentTimeMillis();
        Log.d("SSS", "Time taken:" + (endTime - startTime));
    }
}