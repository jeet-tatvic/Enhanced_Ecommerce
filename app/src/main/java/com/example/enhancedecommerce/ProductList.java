package com.example.enhancedecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import android.content.Intent;
import android.icu.util.Currency;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.AddTrace;
import com.google.firebase.perf.metrics.Trace;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    CardView card1,card2,card3,card4,card5,card6;
    Bundle product1,product2,product3,product4,product5,product6,pr1,pr2,pr3,pr4,pr5,pr6,product1WithIndex,product2WithIndex,product3WithIndex,product4WithIndex,product5WithIndex,product6WithIndex;


    private FirebaseAnalytics firebaseAnalytics;
    Trace myTrace = FirebasePerformance.getInstance().newTrace("test_trace");

    @Override
    @AddTrace(name = "onCreateTrace", enabled = true /* optional */)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        //myTrace.start();
//        Bundle bdl = getIntent().getExtras();
        String idd = getIntent().getStringExtra("id");

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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
                            if (bundle.containsKey("source")) {
                                utm_source = bundle.getString("source");
                            }
                            if (bundle.containsKey("medium")) {
                                utm_medium = bundle.getString("medium");
                            }
                            if (bundle.containsKey("campaign")) {
                                utm_campaign = bundle.getString("campaign");
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



        card1 = findViewById(R.id.cardOne);
        card2 = findViewById(R.id.cardTwo);
        card3 = findViewById(R.id.cardThree);
        card4 = findViewById(R.id.cardFour);
        card5 = findViewById(R.id.cardFive);
        card6 = findViewById(R.id.cardSix);

        product1 = new Bundle();
        product1.putString(FirebaseAnalytics.Param.ITEM_ID,"PR_123");
//        product1.putString(FirebaseAnalytics.Param.PROMOTION_ID,"12345");
        product1.putString(FirebaseAnalytics.Param.ITEM_NAME,"jeggings");
        product1.putString(FirebaseAnalytics.Param.ITEM_CATEGORY,"T-Shirt");
        product1.putString(FirebaseAnalytics.Param.ITEM_VARIANT,"Black");
        product1.putString(FirebaseAnalytics.Param.ITEM_BRAND,"Google");
        product1.putDouble(FirebaseAnalytics.Param.PRICE,500);

        product2 = new Bundle();
        product2.putString(FirebaseAnalytics.Param.ITEM_ID,"PR_234");
//        product1.putString(Param.PROMOTION_ID,idd);
        product2.putString(FirebaseAnalytics.Param.ITEM_NAME,"J7");
        product2.putString(FirebaseAnalytics.Param.ITEM_CATEGORY,"Mobile");
        product2.putString(FirebaseAnalytics.Param.ITEM_VARIANT,"Black");
        product2.putString(FirebaseAnalytics.Param.ITEM_BRAND,"Samsung");
        product2.putDouble(FirebaseAnalytics.Param.PRICE,10000);

        product3 = new Bundle();
        product3.putString(FirebaseAnalytics.Param.ITEM_ID,"PR_345");
//        product1.putString("prm_id",idd);
        product3.putString(FirebaseAnalytics.Param.ITEM_NAME,"boots");
        product3.putString(FirebaseAnalytics.Param.ITEM_CATEGORY,"T-Shirt");
        product3.putString(FirebaseAnalytics.Param.ITEM_VARIANT,"Green");
        product3.putString(FirebaseAnalytics.Param.ITEM_BRAND,"Google");
        product3.putDouble(FirebaseAnalytics.Param.PRICE,700);

        product4 = new Bundle();
        product4.putString(FirebaseAnalytics.Param.ITEM_ID,"PR_456");
//        product1.putString(Param.PROMOTION_ID,idd);
        product4.putString(FirebaseAnalytics.Param.ITEM_NAME,"A13");
        product4.putString(FirebaseAnalytics.Param.ITEM_CATEGORY,"Mobile");
        product4.putString(FirebaseAnalytics.Param.ITEM_VARIANT,"White");
        product4.putString(FirebaseAnalytics.Param.ITEM_BRAND,"Nokia");
        product4.putDouble(FirebaseAnalytics.Param.PRICE,20000);

        product5 = new Bundle();
        product5.putString(FirebaseAnalytics.Param.ITEM_ID,"PR_567");
//        product1.putString(Param.PROMOTION_ID,idd);
        product5.putString(FirebaseAnalytics.Param.ITEM_NAME,"Hoody");
        product5.putString(FirebaseAnalytics.Param.ITEM_CATEGORY,"T-Shirt");
        product5.putString(FirebaseAnalytics.Param.ITEM_VARIANT,"Red");
        product5.putString(FirebaseAnalytics.Param.ITEM_BRAND,"Nike");
        product5.putDouble(FirebaseAnalytics.Param.PRICE,3000);

        product6 = new Bundle();
        product6.putString(FirebaseAnalytics.Param.ITEM_ID,"PR_678");
//        product1.putString(Param.PROMOTION_ID,idd);
        product6.putString(FirebaseAnalytics.Param.ITEM_NAME,"X11");
        product6.putString(FirebaseAnalytics.Param.ITEM_CATEGORY,"Mobile");
        product6.putString(FirebaseAnalytics.Param.ITEM_VARIANT,"White");
        product6.putString(FirebaseAnalytics.Param.ITEM_BRAND,"Oppo");
        product6.putDouble(FirebaseAnalytics.Param.PRICE,25000);


        product1WithIndex = new Bundle(product1);
        product1WithIndex.putLong(FirebaseAnalytics.Param.INDEX,1);

        product2WithIndex = new Bundle(product2);
        product2WithIndex.putLong(FirebaseAnalytics.Param.INDEX,2);

        product3WithIndex = new Bundle(product3);
        product3WithIndex.putLong(FirebaseAnalytics.Param.INDEX,3);

        product4WithIndex = new Bundle(product4);
        product4WithIndex.putLong(FirebaseAnalytics.Param.INDEX,4);

        product5WithIndex = new Bundle(product5);
        product5WithIndex.putLong(FirebaseAnalytics.Param.INDEX,5);

        product6WithIndex = new Bundle(product6);
        product6WithIndex.putLong(FirebaseAnalytics.Param.INDEX,6);

        Bundle viewItemListParams = new Bundle();
        viewItemListParams.putString(FirebaseAnalytics.Param.ITEM_LIST_ID,"LL001");
        viewItemListParams.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME,"Related products");
        viewItemListParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                new Parcelable[]{product1WithIndex,product2WithIndex,product3WithIndex,product4WithIndex,product5WithIndex,product6WithIndex});

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST,viewItemListParams);


        bundleforGA3();

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM,product1WithIndex);

                Bundle p1 = new Bundle(product1);
                p1.putBundle("items",product1);

                p1.putString(Param.ITEM_LIST, "Search_Results");
                p1.putString("is_UA","yes");
                firebaseAnalytics.logEvent(Event.SELECT_CONTENT,p1);

                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra("id","12345");
                intent.putExtras(product1);

                intent.putExtras(p1);

                startActivity(intent);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM,product2WithIndex);

                Bundle p2 = new Bundle();
                p2.putBundle("items",product2);
                p2.putString(Param.ITEM_LIST, "Search_Results");
                p2.putString("is_UA","yes");
                firebaseAnalytics.logEvent(Event.SELECT_CONTENT,p2);

                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtras(product2);
                startActivity(intent);

            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM,product3WithIndex);

                Bundle p3 = new Bundle();
                p3.putBundle("items",product3);
                p3.putString(Param.ITEM_LIST, "Search_Results");
                p3.putString("is_UA","yes");
                firebaseAnalytics.logEvent(Event.SELECT_CONTENT,p3);

                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtras(product3);
                startActivity(intent);

            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM,product4WithIndex);

                Bundle p4 = new Bundle();
                p4.putBundle("items",product4);
                p4.putString(Param.ITEM_LIST, "Search_Results");
                p4.putString("is_UA","yes");
                firebaseAnalytics.logEvent(Event.SELECT_CONTENT,p4);

                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtras(product4);
                startActivity(intent);


            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM,product5WithIndex);

                Bundle p5 = new Bundle();
                p5.putBundle("items",product5);
                p5.putString(Param.ITEM_LIST, "Search_Results");
                p5.putString("is_UA","yes");
                firebaseAnalytics.logEvent(Event.SELECT_CONTENT,p5);

                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtras(product5);
                startActivity(intent);

            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM,product6WithIndex);

                Bundle p6 = new Bundle();
                p6.putBundle("items",product6);
                p6.putString(Param.ITEM_LIST, "Search_Results");
                p6.putString("is_UA","yes");
                firebaseAnalytics.logEvent(Event.SELECT_CONTENT,p6);

                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtras(product6);
                startActivity(intent);

            }
        });
        //myTrace.stop();


    }



    public void bundleforGA3(){
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
        pr1 = new Bundle(product1);
        pr1.putLong(Param.INDEX,1);

        pr2 = new Bundle(product2);
        pr2.putLong(Param.INDEX,2);

        pr3 = new Bundle(product3);
        pr3.putLong(Param.INDEX,3);

        pr4 = new Bundle(product4);
        pr4.putLong(Param.INDEX,4);

        pr5 = new Bundle(product5);
        pr5.putLong(Param.INDEX,5);

        pr6 = new Bundle(product6);
        pr6.putLong(Param.INDEX,6);

        ArrayList<Bundle> items = new ArrayList<Bundle>();
        items.add(pr1);
        items.add(pr2);
        items.add(pr3);
        items.add(pr4);
        items.add(pr5);
        items.add(pr6);


        Bundle ecommerceBundle = new Bundle();
        ecommerceBundle.putParcelableArrayList( "items", items );
        ecommerceBundle.putString( Param.ITEM_LIST, "Search_Results" );
        ecommerceBundle.putString("is_UA","yes");


        firebaseAnalytics.logEvent( Event.VIEW_SEARCH_RESULTS, ecommerceBundle );

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }
}