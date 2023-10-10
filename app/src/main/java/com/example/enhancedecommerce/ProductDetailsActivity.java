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
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;
    Button addToCart;
    TextView items;
    int n=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        String idd = getIntent().getStringExtra("id");


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

        addToCart = findViewById(R.id.addToCart);

        Bundle bundle = getIntent().getExtras();

        Bundle viewItemParams = new Bundle(bundle);
//        viewItemParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS, new Parcelable[]{bundle});
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, viewItemParams);
        //++++

        Bundle bdl = getIntent().getExtras();

        Bundle viewitems = new Bundle(bdl);
//        viewitems.putBundle("view",bdl);

        viewitems.putString("is_UA","yes");
        viewitems.putString(Param.SCREEN_NAME,"PDP");
        firebaseAnalytics.logEvent(Event.VIEW_ITEM,viewitems);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle itemProdect1list = new Bundle(viewItemParams);
//                itemProdect1list.putLong(FirebaseAnalytics.Param.QUANTITY, n);

                Bundle addToCartParams = new Bundle();
                addToCartParams.putLong(FirebaseAnalytics.Param.QUANTITY, n);
                addToCartParams.putString(FirebaseAnalytics.Param.PROMOTION_ID,idd);
                addToCartParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
                addToCartParams.putDouble(FirebaseAnalytics.Param.VALUE,n* bundle.getDouble(FirebaseAnalytics.Param.PRICE));
                addToCartParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS, new Parcelable[]{viewItemParams});

                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART,addToCartParams);



//                Bundle addcart = new Bundle(bdl);
//                addcart.putLong(Param.QUANTITY,n);
//
//                Bundle addcartvalue = new Bundle(addcart);
//                addcartvalue.putLong(Param.VALUE,(long) n*bdl.getInt(Param.PRICE));
//                ArrayList<Bundle> items = new ArrayList<Bundle>();
//                items.add(addToCartParams);

                Bundle ecommerceBundle = new Bundle(bdl);
                ecommerceBundle.putLong(Param.QUANTITY,n);
                ecommerceBundle.putDouble(Param.VALUE,n*bdl.getDouble(Param.PRICE));
                ecommerceBundle.putBundle("items",bdl);
                ecommerceBundle.putString("is_UA","yes");
                firebaseAnalytics.logEvent(Event.ADD_TO_CART,ecommerceBundle);

                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                intent.putExtra("ga4",addToCartParams);
                intent.putExtra("ga3",ecommerceBundle);
                startActivity(intent);
            }
        });

    }

    public void increment(View view) {
        n=n+1;
        display(n);
    }

    public void decrement(View view) {
        n=n-1;
        if(n>0)display(n);
    }
    public void display(int x){
        items = findViewById(R.id.textView);
        items.setText("" +x);

    }

}