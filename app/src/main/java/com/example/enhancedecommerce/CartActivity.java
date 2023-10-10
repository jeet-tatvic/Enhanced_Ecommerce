package com.example.enhancedecommerce;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;
    Button removeCart,checkOut;
    Bundle getCartParams,ecommerceBundle,cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {

                            // get deep link url
                            deepLink = pendingDynamicLinkData.getLink();
                            Log.e("DeepLink", String.valueOf(deepLink));

                            // get utm parameters
                            Bundle bundle =pendingDynamicLinkData.getUtmParameters();

                            String utm_source = null, utm_medium = null, utm_campaign = null;
                            if (bundle.containsKey("utm_source")) {
                                utm_source = bundle.getString("utm_source");
                            }
                            if (bundle.containsKey("utm_medium")) {
                                utm_medium = bundle.getString("utm_medium");
                            }
                            if (bundle.containsKey("utm_campaign")) {
                                utm_campaign = bundle.getString("utm_campaign");
                            }

                            // pass custom event of dynamic link
                            Bundle params = new Bundle();
                            params.putString("eventCategory", "dynamic link campaign");
                            params.putString("eventAction", "campaign link");
                            params.putString("utm_source", utm_source);
                            params.putString("utm_medium", utm_medium);
                            params.putString("utm_campaign", utm_campaign);
                            firebaseAnalytics.logEvent("dynamic_link_event", params);

                        }
                    }
                });


        removeCart = findViewById(R.id.removeToCart);
        checkOut = findViewById(R.id.checkOut);
        getCartParams = getIntent().getBundleExtra("ga4");

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_CART,getCartParams);


        removeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle removecarts = new Bundle(getCartParams);
                removecarts.putString("delete","all products");
                removecarts.putParcelableArray(Param.ITEMS, new Parcelable[]{getCartParams});
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.REMOVE_FROM_CART,removecarts);

//================================================================
//                // when products are out of stocks and when user land on cart screen this remove_from_cart will trigger
//
//                Bundle autoRemoveProducts = new Bundle(getCartParams);
//                autoRemoveProducts.putString("outOfStock","true");
//                autoRemoveProducts.putDouble(FirebaseAnalytics.Param.VALUE, (1 * 24.99)); // pass the total value of all products here
//                autoRemoveProducts.putParcelableArray(FirebaseAnalytics.Param.ITEMS,new Parcelable[]{getCartParams}); // pass the array of product which were out of stock
//                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.REMOVE_FROM_CART,autoRemoveProducts);
//
//==================================================================

                ArrayList <Bundle> items = new ArrayList<Bundle>();
                items.add(cart);
                ecommerceBundle = new Bundle(cart);
                ecommerceBundle.putParcelableArrayList("items",items);
                ecommerceBundle.putString("is_UA","yes");
                firebaseAnalytics.logEvent(Event.REMOVE_FROM_CART,ecommerceBundle);

                getCartParams.clear();
                cart.clear();
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_CART,getCartParams);

            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bdl = new Bundle();
                bdl.putString("begin", "checkout start");
                bdl.putParcelableArray(FirebaseAnalytics.Param.ITEMS,new Parcelable[]{getCartParams});
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT,bdl);

                cart = getIntent().getBundleExtra("ga3");


                ArrayList <Bundle> items = new ArrayList<Bundle>();
                items.add(cart);

                Bundle ecd = new Bundle(cart);
                ecd.putParcelableArrayList( "items", items );
//                ecd.putBundle("bd",ecommerceBundle);
                ecd.putString("is_UA","yes");
                ecd.putString("check","checkout_ga3");
                firebaseAnalytics.logEvent(Event.BEGIN_CHECKOUT, ecd);

                Intent intent = new Intent(getApplicationContext(),CheckOut.class);
                intent.putExtra("ga4",getCartParams);
                intent.putExtra("ga3",ecd);
                startActivity(intent);


            }
        });



    }
}