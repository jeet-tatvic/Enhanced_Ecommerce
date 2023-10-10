package com.example.enhancedecommerce;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    Bundle promotion;
    String advertisingId;
    AdvertisingIdClient.Info idInfo;
    private FirebaseAnalytics firebaseAnalytics;
//==================================

//    public static final class FirstDrawListener implements ViewTreeObserver.OnDrawListener {
//
//        private final Handler mainHandler;
//        private final View view;
//        private final OnFirstDrawCallback firstDrawCallback;
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
//         * @param view              for which to register the {@link OnFirstDrawCallback}.
//         * @param firstDrawCallback to be invoked on various stages of drawing of First frame.
//         */
//        public static FirstDrawListener registerFirstDrawListener(View view, OnFirstDrawCallback firstDrawCallback) {
//            return new FirstDrawListener(view, firstDrawCallback);
//        }
//
//        /**
//         * Constructor for the class.
//         *
//         * @param view              for which to register the {@link OnFirstDrawCallback}.
//         * @param firstDrawCallback to be invoked on various stages of drawing of First frame.
//         */
//        FirstDrawListener(View view, OnFirstDrawCallback firstDrawCallback) {
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
//                view.getViewTreeObserver().addOnDrawListener(FirstDrawListener.this);
//
//            } else {
//                // Workaround for a bug fixed in API 26
//                // https://android.googlesource.com/platform/frameworks/base/+/9f8ec54244a5e0343b9748db3329733f259604f3
//                view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//
//                    @Override
//                    public void onViewAttachedToWindow(View v) {
//                        if (view.getViewTreeObserver().isAlive()) {
//                            view.getViewTreeObserver().addOnDrawListener(FirstDrawListener.this);
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
//                        view.getViewTreeObserver().removeOnDrawListener(FirstDrawListener.this);
//                    }
//                });
//            }
//        }
//    }
//    private final Trace viewLoadTrace = FirebasePerformance.startTrace("TestActivity-LoadTime");
//==================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long startTime = System.currentTimeMillis();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAGjj", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("TAGjj", token);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });



            GetGAClientID getGAClientID = new GetGAClientID();
            getGAClientID.execute();
        // For App Instance ID
        FirebaseAnalytics.getInstance(this).getAppInstanceId().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    String user_pseudo_id = task.getResult();
                    Log.d("App Instance ID", user_pseudo_id);
                }
            }
        });
        //========
//======================
        View mainView = findViewById(android.R.id.content);
//======================
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        img = findViewById(R.id.banner);

        promotion = new Bundle();
        promotion.putString(FirebaseAnalytics.Param.PROMOTION_ID,"12345");
        promotion.putString(FirebaseAnalytics.Param.PROMOTION_NAME,"Big Sale");
        promotion.putString(FirebaseAnalytics.Param.CREATIVE_NAME,"banner.jpg");
        promotion.putString(FirebaseAnalytics.Param.CREATIVE_SLOT,"featured_app_1");
        promotion.putString(FirebaseAnalytics.Param.LOCATION_ID,"BANNER");

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION,promotion);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION,promotion);

                Intent intent = new Intent(getApplicationContext(),ProductList.class);
//                intent.putExtras(promotion);
                intent.putExtra("id","12345");
                startActivity(intent);
            }
        });

//        FirstDrawListener.registerFirstDrawListener(mainView, new FirstDrawListener.OnFirstDrawCallback() {
//            @Override
//            public void onDrawingStart() {
//                // In practice you can also record this event separately
//            }
//
//            @Override
//            public void onDrawingFinish() {
//                // This is when the Activity UI is completely drawn on the screen
//                viewLoadTrace.stop();
//            }
//        });

//        FirebaseDynamicLinks.getInstance()
//                .getDynamicLink(getIntent())
//                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
//                    @Override
//                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
//                        Uri deeplink=pendingDynamicLinkData.getLink();
//                        Log.e("Deep Link url:", String.valueOf(deeplink));
//                        //Toast.makeText(this, String.valueOf(deeplink), Toast.LENGTH_SHORT).show();
//                    }
//                });
        long endTime= System.currentTimeMillis();
        Log.d("SSS", "Time taken:" + (endTime - startTime)+"ms");

    }
    Tracker mTracker; // this variable should define as global
    class GetGAClientID extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            GoogleAnalytics ga = GoogleAnalytics .getInstance(MainActivity.this);
            mTracker = ga.newTracker("UA-123345-12"); // You need to add Tracking ID of your created UA property
            Log.d("CLIENT_ID", "doInBackground: " + mTracker.get("&cid"));

            return mTracker.get("&cid"); // this will return the client id as a string
        }
    }}