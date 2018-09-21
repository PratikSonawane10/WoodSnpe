package com.mobitechs.woodsnipe;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mobitechs.woodsnipe.sessionManager.SessionManager;


public class SplashScreen extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    ImageView image1;
    RelativeLayout mainlayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //image1 = (ImageView)findViewById(R.id.logoHeader);
        mainlayout = (RelativeLayout)findViewById(R.id.mainlayout);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                SessionManager sessionManager = new SessionManager(SplashScreen.this);
                sessionManager.checkLogin();
//                Intent indexIntent = new Intent(SplashScreen.this, Login.class);
//                startActivity(indexIntent);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}