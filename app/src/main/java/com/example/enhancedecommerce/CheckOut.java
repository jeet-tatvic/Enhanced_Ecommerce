package com.example.enhancedecommerce;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

import java.util.ArrayList;

public class CheckOut extends AppCompatActivity {
    Button pytype;
    private FirebaseAnalytics firebaseAnalytics;

//    public static final class FirstDrawListener implements ViewTreeObserver.OnDrawListener {
//
//        private final Handler mainHandler;
//        private final View view;
////        private final MainActivity.FirstDrawListener.OnFirstDrawCallback firstDrawCallback;
//
//        private boolean onDrawInvoked;
//
//        /**
//         * Interface definition for a callback to be invoked for the draw callbacks on the First frame.
//         */
//        public interface OnFirstDrawCallback {
//            /**
//             * Callback to be invoked when the first frame is about to be drawn on the Screen.
//             * At this time the complete UI (including all views in the view hierarchy) have been
//             * measured, laid out and given a frame.
//             * <p>
//             * Most of the time until this callback is greatly affected by developer's code.
//             */
//            void onDrawingStart();
//
//            /**
//             * Callback to be invoked when the first frame has finished drawing.
//             * At this time the complete UI is visible to the user for the first time.
//             * <p>
//             * Frame rendering is governed by the Android rendering pipeline and so the time difference
//             * b/w onDrawingStart() and onDrawingFinish() is greatly effected by the device hardware.
//             */
//            void onDrawingFinish();
//        }
//
//        /**
//         * Register a FirstDrawListener instance to the caller.
//         *
//         * @param view              for which to register the {@link MainActivity.FirstDrawListener.OnFirstDrawCallback}.
//         * @param firstDrawCallback to be invoked on various stages of drawing of First frame.
//         */
//        public static MainActivity.FirstDrawListener registerFirstDrawListener(View view, MainActivity.FirstDrawListener.OnFirstDrawCallback firstDrawCallback) {
//            return new MainActivity.FirstDrawListener(view, firstDrawCallback);
//        }
//
//        /**
//         * Constructor for the class.
//         *
//         * @param view              for which to register the {@link MainActivity.FirstDrawListener.OnFirstDrawCallback}.
//         * @param firstDrawCallback to be invoked on various stages of drawing of First frame.
//         */
//        private FirstDrawListener(View view, MainActivity.FirstDrawListener.OnFirstDrawCallback firstDrawCallback) {
//            super();
//
//            this.view = view;
//            this.firstDrawCallback = firstDrawCallback;
//            this.mainHandler = new Handler(Looper.getMainLooper());
//
//            registerFirstDrawListener();
//        }
//
//        private void registerFirstDrawListener() {
//            if (view.getViewTreeObserver().isAlive() && view.isAttachedToWindow()) {
//                view.getViewTreeObserver().addOnDrawListener(CheckOut.FirstDrawListener.this);
//
//            } else {
//                // Workaround for a bug fixed in API 26
//                // https://android.googlesource.com/platform/frameworks/base/+/9f8ec54244a5e0343b9748db3329733f259604f3
//                view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//
//                    @Override
//                    public void onViewAttachedToWindow(View v) {
//                        if (view.getViewTreeObserver().isAlive()) {
//                            view.getViewTreeObserver().addOnDrawListener(CheckOut.FirstDrawListener.this);
//                        }
//
//                        // We only want to listen to this event for the first time only
//                        view.removeOnAttachStateChangeListener(this);
//                    }
//
//                    @Override
//                    public void onViewDetachedFromWindow(View v) {
//                        // No-op
//                    }
//                });
//            }
//        }
//
//        @Override
//        public void onDraw() {
//            if (!onDrawInvoked) {
//                onDrawInvoked = true;
//
//                // Report first draw start
//                firstDrawCallback.onDrawingStart();
//
//                // As part of the frame draw the Android Choreographer (coordinates the timing of
//                // animations, input and drawing) enqueues a MSG_DO_FRAME message on the
//                // Message Queue of the main thread. Processing of that frame and traversal (including
//                // the measure pass, layout pass and finally the draw pass) all happens in just one
//                // MSG_DO_FRAME message. So coming to this onDraw() callback means that the MSG_DO_FRAME
//                // message has been currently processing. Since the Handler processes messages from the
//                // Message Queue in a serial fashion we can detect when the drawing finishes by posting
//                // a message to the front of Message Queue. When that message is processed by the
//                // Handler we can safely assume that the drawing has just been finished completely.
//                mainHandler.postAtFrontOfQueue(firstDrawCallback::onDrawingFinish);
//
//                // Remove the listener after the call is finished
//                mainHandler.post(() -> {
//                    if (view.getViewTreeObserver().isAlive()) {
//                        view.getViewTreeObserver().removeOnDrawListener(CheckOut.FirstDrawListener.this);
//                    }
//                });
//            }
//        }
//    }
    private final Trace viewLoadTrace = FirebasePerformance.startTrace("CheckOut-LoadTime");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        View mainView = findViewById(android.R.id.content);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        pytype = findViewById(R.id.payType);
        Bundle addressInfo = getIntent().getBundleExtra("ga4");

        pytype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle saveAdd = new Bundle();
                saveAdd.putString(FirebaseAnalytics.Param.SHIPPING_TIER,"Ground");
                saveAdd.putParcelableArray(FirebaseAnalytics.Param.ITEMS, new Parcelable[]{addressInfo});

                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_SHIPPING_INFO,saveAdd);



//                Bundle product1 = new Bundle();
//                product1.putString( Param.ITEM_ID, "sku1234"); // ITEM_ID or ITEM_NAME is required
//                product1.putString( Param.ITEM_NAME, "Donut Friday Scented T-Shirt");
//                product1.putString( Param.ITEM_CATEGORY, "Apparel/Men/Shirts");
//                product1.putString( Param.ITEM_VARIANT, "Blue");
//                product1.putString( Param.ITEM_BRAND, "Google");
//                product1.putDouble( Param.PRICE, 29.99 );
//                product1.putString( Param.CURRENCY, "USD" ); // Item-level currency unused today
//                product1.putLong( Param.QUANTITY, 1 );


//
                Bundle add = getIntent().getBundleExtra("ga3");

                ArrayList<Bundle> items = new ArrayList<Bundle>();
                items.add(add);

                Bundle ecommerceBundle = new Bundle();
                ecommerceBundle.putParcelableArrayList( "items", items);
                ecommerceBundle.putDouble(Param.VALUE, add.getLong(Param.QUANTITY)*add.getDouble(Param.PRICE));
                ecommerceBundle.putLong( Param.CHECKOUT_STEP, 1 );
                ecommerceBundle.putString( Param.CHECKOUT_OPTION, "Visa" );
                ecommerceBundle.putString("is_UA","yes");
                firebaseAnalytics.logEvent( Event.CHECKOUT_PROGRESS, ecommerceBundle );


//                Bundle addShip = new Bundle();
//                addShip.putBundle("add",saveAdd);
//
//                addShip.putLong(Param.CHECKOUT_STEP,1);
//                firebaseAnalytics.logEvent(Event.CHECKOUT_PROGRESS,addShip);

                Intent intent = new Intent(getApplicationContext(),FinalCheckOut.class);
                intent.putExtra("ga4",saveAdd);
                intent.putExtra("ga3",add);
                startActivity(intent);
            }
        });
//        CheckOut.FirstDrawListener.registerFirstDrawListener(mainView, new MainActivity.FirstDrawListener.OnFirstDrawCallback() {
//            @Override
//            public void onDrawingStart() {
//                viewLoadTrace.start();
//                // In practice you can also record this event separately
//            }
//
//            @Override
//            public void onDrawingFinish() {
//                // This is when the Activity UI is completely drawn on the screen
//                viewLoadTrace.stop();
//            }
//        });

    }



}