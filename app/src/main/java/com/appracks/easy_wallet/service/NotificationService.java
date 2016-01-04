package com.appracks.easy_wallet.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import com.appracks.easy_wallet.R;
import com.appracks.easy_wallet.SplashScreen;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    private Timer timer;
    private TimerTask timerTask;
    private Calendar calendar;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setService();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void setService() {
        if (timer == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    calendar = Calendar.getInstance();
                    if (calendar.get(Calendar.HOUR) == 8 && calendar.get(Calendar.MINUTE) == 0 && calendar.get(Calendar.SECOND) == 0 && calendar.get(Calendar.AM_PM) == 1) {
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(NotificationService.this)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setAutoCancel(true)
                                        .setContentTitle("Add your income, expense statement.")
                                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                                        .setContentText("Have you income or expense today?");
                        int NOTIFICATION_ID = 6;
                        PendingIntent contentIntent = PendingIntent.getActivity(NotificationService.this, 0, new Intent(NotificationService.this, SplashScreen.class), PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(contentIntent);
                        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        nManager.notify(NOTIFICATION_ID, builder.build());
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask, 1000, 1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }
}
