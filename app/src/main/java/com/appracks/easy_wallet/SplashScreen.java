package com.appracks.easy_wallet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.appracks.easy_wallet.service.NotificationService;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startService(new Intent(this, NotificationService.class));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, OverViewActivity.class));
                finish();
            }
        }, 2000);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
