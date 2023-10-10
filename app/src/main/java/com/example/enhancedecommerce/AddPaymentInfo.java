package com.example.enhancedecommerce;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class AddPaymentInfo extends AppCompatActivity {
    private FirebaseAnalytics firebaseAnalytics;
    Button finalCheck;
    Bundle infoPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_info);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        finalCheck = findViewById(R.id.finalcheck);
        infoPay = getIntent().getExtras();

        finalCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle addPaymentType = new Bundle();
                addPaymentType.putString(FirebaseAnalytics.Param.COUPON,"HAPPY2022");
                addPaymentType.putString(FirebaseAnalytics.Param.PAYMENT_TYPE,"VISA");
                addPaymentType.putParcelableArray(FirebaseAnalytics.Param.ITEMS, new Parcelable[]{infoPay});

                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO,addPaymentType);
                Intent intent = new Intent(getApplicationContext(),FinalCheckOut.class);
                intent.putExtras(addPaymentType);
                startActivity(intent);

                ArrayList<Bundle> items = new ArrayList<Bundle>();
                items.add(infoPay);

                Bundle addpay = new Bundle();
                addpay.putParcelableArrayList( "items", items );
                addpay.putString("is_UA","yes");
                addpay.putLong(Param.CHECKOUT_STEP,2);
                firebaseAnalytics.logEvent(Event.CHECKOUT_PROGRESS,addpay);
            }
        });

    }
}