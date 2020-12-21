package com.example.ver20;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadCastReceiver";

    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent){
        AlarmManagerUtils.getInstance(context).getUpAlarmManagerWorkOnReceiver();
        String extra = intent.getStringExtra("msg");
        Log.i(TAG,"extra = " + extra);
        Toast.makeText(context,"I received",Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent();
        intent1.setClass(context,OnTimeActivity.class);
        context.startActivity(intent1);
    }
}
