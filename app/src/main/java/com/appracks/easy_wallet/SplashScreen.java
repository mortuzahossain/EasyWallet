package com.appracks.easy_wallet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.appracks.easy_wallet.service.NotificationService;

public class SplashScreen extends AppCompatActivity {

    ImageView imageView;
    int orient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startService(new Intent(this, NotificationService.class));
        imageView= (ImageView) findViewById(R.id.imageView);
        orient = getWindowManager().getDefaultDisplay().getRotation(); //land=1 and port=0
        if (orient == 0) {
            imageView.setImageResource(R.drawable.common_signin_btn_icon_light);
        }else{
            imageView.setImageResource(R.drawable.common_signin_btn_icon_light);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, 2000);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        orient = getWindowManager().getDefaultDisplay().getRotation();
        if (orient == 0) {
            imageView.setImageResource(R.drawable.common_signin_btn_icon_light);
        }else{
            imageView.setImageResource(R.drawable.common_signin_btn_icon_light);
        }
    }
}
