package com.clique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import com.clique.R;
import com.clique.utils.SessionManager;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "IytaP3xwOqKEFqPRPWBPJbyJ5";
    private static final String TWITTER_SECRET = "vsphd1LpC3tLoZUpPXbKAWGsGqlLX1qcs0XgVsLZy8BzGEIvor";


    private static final long SPLASH_SCREEN_TIME_IN_MILLIS = 2000;
    private Handler handler;
    private Thread thread;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FullScreencall();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(SplashActivity.this);
        handler = new Handler();
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SPLASH_SCREEN_TIME_IN_MILLIS);
                    handler.post(new Runnable() {
                        public void run() {
                            goToNextScreen();
                        }
                    });
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();

    }

    @Override
    protected void onResume() {
       // FullScreencall();
        super.onResume();
    }

  /*  public void FullScreencall() {
        if (Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for higher api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }*/

    private void goToNextScreen() {
      /*  if (SessionManager.getTut(SplashActivity.this) == 0) {
            Intent i = new Intent(SplashActivity.this, TutorialActivity.class);
            startActivity(i);
            finish();
        } else if (sessionManager.get(SplashActivity.this) != null) {
            Intent i = new Intent(SplashActivity.this, OrderActivity.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(SplashActivity.this, CreateActivity.class);
            startActivity(i);
            finish();
        }
*/
        Intent i = new Intent(SplashActivity.this, TutorialActivity.class);
        startActivity(i);
        finish();
    }
}
