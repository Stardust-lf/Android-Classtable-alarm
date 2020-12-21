package com.example.ver20;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class AlarmManagerUtils {
    private static final long TIME_INTERVAL = 7 * 24 * 3600 * 1000;
    private Context context;
    public static AlarmManager alarmManager;
    public static PendingIntent pendingIntent;

    private AlarmManagerUtils(Context context){
        this.context = context;
    }

    private static AlarmManagerUtils instance = null;

    public static AlarmManagerUtils getInstance(Context acontext){
        if(instance == null){
            synchronized (AlarmManagerUtils.class){
                if(instance == null){
                    instance = new AlarmManagerUtils(acontext);
                }
            }
        }
        return instance;
    }

    public void createGetUpAlarmManager(){
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
        intent.putExtra("msg","赶紧起床");
        pendingIntent= PendingIntent.getBroadcast(context, 0 ,intent,0);

    }
    @SuppressLint("NewApi")
    public void getUpAlarmManagerStartWork(Long time){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,time,pendingIntent);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,pendingIntent);
        }else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,TIME_INTERVAL + TIME_INTERVAL,pendingIntent);
        }
    }
    @SuppressLint("NewApi")
    public void getUpAlarmManagerWorkOnReceiver(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + TIME_INTERVAL,pendingIntent);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + TIME_INTERVAL, pendingIntent);
        }
    }
}
