package com.example.ver20;

import androidx.viewpager.widget.ViewPager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private ViewPager mViewPager;
    private List<View> mViews = new ArrayList<View>();
    private pageAdapter mAdapter = new pageAdapter(mViews);
    private LinearLayout mTabAlarm,mTabOneTime,mTabDiy,mTabHelp,mTopNavigation;
    private ImageButton mAlarmImg,mOneTimeImg,mDiyImg,mHelpImg;
    private Button btn_className1,btn_className2,btn_className3,btn_className4,btn_className5,btn_className6;
    private Button btn_time1,btn_time2,btn_time3,btn_time4,btn_time5,btn_time6;
    private Button btn_state1,btn_state2,btn_state3,btn_state4,btn_state5,btn_state6;
    private Button btn_yesterday,btn_tomorrow;
    private TextView btn_setAlarms;
    private TextView tv_dayOfWeek,tv_note;
    private View tab01,tab02,tab03,tab04;
    private SQLiteDatabase database;
    private Alarm alarm1,alarm2,alarm3,alarm4,alarm5,alarm6,alarm11,alarm22,alarm33,alarm44,alarm55,alarm66;
    private OneTimeAlarm oneTimeAlarm;
    private Button btn_addOneTimeAlarm;
    private MyBroadcastReceiver myBroadcastReceiver;
    private IntentFilter intentFilter;
    private AlarmManagerUtils alarmManagerUtils;
    private Button btn_diyBackground,btn_choseDefaultBackground;
    private TextView tv_timeNow;
    private Integer season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MySQLiteOpenHelper sqLiteOpenHelper = new MySQLiteOpenHelper(MainActivity.this);
        database = sqLiteOpenHelper.getWritableDatabase();
        season = Utils.getSkin(database);
        LayoutInflater mInflater = LayoutInflater.from(this);
        tab04 = mInflater.inflate(R.layout.tab04,null);
        tab03 = mInflater.inflate(R.layout.tab03,null);
        switch(season){
            case 0:
                tab01 = mInflater.inflate(R.layout.tab01,null);
                tab02 = mInflater.inflate(R.layout.tab02,null);
                break;
            case 1:
                tab01 = mInflater.inflate(R.layout.tab01_spring,null);
                tab02 = mInflater.inflate(R.layout.tab02_spring,null);
                break;
            case 2:
                tab01 = mInflater.inflate(R.layout.tab01_summer,null);
                tab02 = mInflater.inflate(R.layout.tab02_summer,null);
                break;
            case 3:
                tab01 = mInflater.inflate(R.layout.tab01_autumn,null);
                tab02 = mInflater.inflate(R.layout.tab02_autumn,null);
                break;
            case 4:
                tab01 = mInflater.inflate(R.layout.tab01_winter,null);
                tab02 = mInflater.inflate(R.layout.tab02_winter,null);
                break;
        }
        initView();
        initEvents();
        alarm1 = new Alarm(Utils.getWeekInEnglish(),1,database);
        alarm2 = new Alarm(Utils.getWeekInEnglish(),2,database);
        alarm3 = new Alarm(Utils.getWeekInEnglish(),3,database);
        alarm4 = new Alarm(Utils.getWeekInEnglish(),4,database);
        alarm5 = new Alarm(Utils.getWeekInEnglish(),5,database);
        alarm6 = new Alarm(Utils.getWeekInEnglish(),6,database);
        initTab01();

        oneTimeAlarm = new OneTimeAlarm();
        initTab02();

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.MY_BROADCAST");
        myBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver,intentFilter);
        alarmManagerUtils = AlarmManagerUtils.getInstance(this);
        alarmManagerUtils.createGetUpAlarmManager();

        initTab03();
        initTab04();
    }

    private void setAlarms(){
        for(int i = 1;i<=7;i++){
            alarm11 = new Alarm(Utils.translateWeekToEnglish(i),1,database);
            alarm22 = new Alarm(Utils.translateWeekToEnglish(i),2,database);
            alarm33 = new Alarm(Utils.translateWeekToEnglish(i),3,database);
            alarm44 = new Alarm(Utils.translateWeekToEnglish(i),4,database);
            alarm55 = new Alarm(Utils.translateWeekToEnglish(i),5,database);
            alarm66 = new Alarm(Utils.translateWeekToEnglish(i),6,database);
            if(alarm11.state){
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),alarm11.hour,alarm11.minute,0);
                //Toast.makeText(this,"Alarm has been set on " + alarm11.hour.toString() + ":" + alarm11.minute.toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"Alarm has been set on " + calendar.getTimeInMillis() + ":" + alarm11.minute.toString(),Toast.LENGTH_SHORT).show();
                alarmManagerUtils.getUpAlarmManagerStartWork(calendar.getTimeInMillis());
            }
            if(alarm22.state){
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),alarm22.hour,alarm22.minute,0);
                Toast.makeText(this,"Alarm has been set on " + alarm22.hour.toString() + ":" + alarm22.minute.toString(),Toast.LENGTH_SHORT).show();
                alarmManagerUtils.getUpAlarmManagerStartWork(calendar.getTimeInMillis());
            }
            if(alarm33.state){
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),alarm33.hour,alarm33.minute,0);
                Toast.makeText(this,"Alarm has been set on " + alarm33.hour.toString() + ":" + alarm33.minute.toString(),Toast.LENGTH_SHORT).show();
                alarmManagerUtils.getUpAlarmManagerStartWork(calendar.getTimeInMillis());
            }
            if(alarm44.state){
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),alarm44.hour,alarm44.minute,0);
                Toast.makeText(this,"Alarm has been set on " + alarm44.hour.toString() + ":" + alarm44.minute.toString(),Toast.LENGTH_SHORT).show();
                alarmManagerUtils.getUpAlarmManagerStartWork(calendar.getTimeInMillis());
            }
            if(alarm55.state){
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),alarm55.hour,alarm55.minute,0);
                Toast.makeText(this,"Alarm has been set on " + alarm55.hour.toString() + ":" + alarm55.minute.toString(),Toast.LENGTH_SHORT).show();
                alarmManagerUtils.getUpAlarmManagerStartWork(calendar.getTimeInMillis());
            }
            if(alarm66.state){
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),alarm66.hour,alarm66.minute,0);
                Toast.makeText(this,"Alarm has been set on " + alarm66.hour.toString() + ":" + alarm66.minute.toString(),Toast.LENGTH_SHORT).show();
                alarmManagerUtils.getUpAlarmManagerStartWork(calendar.getTimeInMillis());
            }
        }
    }

    private void initEvents(){
        mTabAlarm.setOnClickListener(this);
        mTabOneTime.setOnClickListener(this);
        mTabDiy.setOnClickListener(this);
        mTabHelp.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int arg0) {
                resetImg();  //将图片全部默认为不选中
                int currentItem = mViewPager.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        switch (season){
                            case 0:
                                mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                                break;
                            case 1:
                                mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                                break;
                            case 2:
                                mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                                break;
                            case 3:
                                mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                                break;
                            case 4:
                                mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                                break;
                        }
                        break;
                    case 1:
                        switch (season){
                            case 0:
                                mOneTimeImg.setImageResource(R.drawable.onetime_chosen);
                                break;
                            case 1:
                                mOneTimeImg.setImageResource(R.drawable.onetime_chosen_spring);
                                break;
                            case 2:
                                mOneTimeImg.setImageResource(R.drawable.onetime_chosen_summer);
                                break;
                            case 3:
                                mOneTimeImg.setImageResource(R.drawable.onetime_chosen_autumn);
                                break;
                            case 4:
                                mOneTimeImg.setImageResource(R.drawable.onetime_chosen_winter);
                                break;
                        }
                        break;
                    case 2:
                        switch (season){
                            case 0:
                                mDiyImg.setImageResource(R.drawable.diy_chosen);
                                break;
                            case 1:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_spring);
                                break;
                            case 2:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_summer);
                                break;
                            case 3:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_autumn);
                                break;
                            case 4:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_winter);
                                break;
                        }
                        break;
                    case 3:
                        switch (season){
                            case 0:
                                mHelpImg.setImageResource(R.drawable.help_chosen);
                                break;
                            case 1:
                                mHelpImg.setImageResource(R.drawable.help_chosen_spring);
                                break;
                            case 2:
                                mHelpImg.setImageResource(R.drawable.help_chosen_summer);
                                break;
                            case 3:
                                mHelpImg.setImageResource(R.drawable.help_chosen_autumn);
                                break;
                            case 4:
                                mHelpImg.setImageResource(R.drawable.help_chosen_winter);
                                break;
                        }
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        // Tab
        mTabAlarm = (LinearLayout) findViewById(R.id.LL_alarm);
        mTabOneTime = (LinearLayout) findViewById(R.id.LL_onetime);
        mTabDiy = (LinearLayout) findViewById(R.id.LL_diy);
        mTabHelp = (LinearLayout) findViewById(R.id.LL_help);
        // img
        mAlarmImg = (ImageButton) findViewById(R.id.Img_alarm);
        mOneTimeImg = (ImageButton) findViewById(R.id.Img_onetime);
        mDiyImg = (ImageButton) findViewById(R.id.Img_diy);
        mHelpImg = (ImageButton) findViewById(R.id.Img_help);

        switch (season){
            case 0:
                mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                break;
            case 1:
                mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                break;
            case 2:
                mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                break;
            case 3:
                mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                break;
            case 4:
                mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                break;
        }
        mOneTimeImg.setImageResource(R.drawable.onetime);
        mDiyImg.setImageResource(R.drawable.diy);
        mHelpImg.setImageResource(R.drawable.help);


        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);
        mViews.add(tab04);

        mViewPager.setAdapter(mAdapter);
    }
    @Override
    public void onClick(View view) {
        resetImg();
        List<Integer> list = new ArrayList<Integer>();
        switch (view.getId()) {
            case R.id.LL_alarm:
                mViewPager.setCurrentItem(0);
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                break;
            case R.id.LL_onetime:
                mViewPager.setCurrentItem(1);
                switch (season){
                    case 0:
                        mOneTimeImg.setImageResource(R.drawable.onetime_chosen);
                        break;
                    case 1:
                        mOneTimeImg.setImageResource(R.drawable.onetime_chosen_spring);
                        break;
                    case 2:
                        mOneTimeImg.setImageResource(R.drawable.onetime_chosen_summer);
                        break;
                    case 3:
                        mOneTimeImg.setImageResource(R.drawable.onetime_chosen_autumn);
                        break;
                    case 4:
                        mOneTimeImg.setImageResource(R.drawable.onetime_chosen_winter);
                        break;
                }
                break;
            case R.id.LL_diy:
                mViewPager.setCurrentItem(2);
                switch (season){
                    case 0:
                        mDiyImg.setImageResource(R.drawable.diy_chosen);
                        break;
                    case 1:
                        mDiyImg.setImageResource(R.drawable.diy_chosen_spring);
                        break;
                    case 2:
                        mDiyImg.setImageResource(R.drawable.diy_chosen_summer);
                        break;
                    case 3:
                        mDiyImg.setImageResource(R.drawable.diy_chosen_autumn);
                        break;
                    case 4:
                        mDiyImg.setImageResource(R.drawable.diy_chosen_winter);
                        break;
                }
                break;
            case R.id.LL_help:
                mViewPager.setCurrentItem(3);
                switch (season){
                    case 0:
                        mHelpImg.setImageResource(R.drawable.help_chosen);
                        break;
                    case 1:
                        mHelpImg.setImageResource(R.drawable.help_chosen_spring);
                        break;
                    case 2:
                        mHelpImg.setImageResource(R.drawable.help_chosen_summer);
                        break;
                    case 3:
                        mHelpImg.setImageResource(R.drawable.help_chosen_autumn);
                        break;
                    case 4:
                        mHelpImg.setImageResource(R.drawable.help_chosen_winter);
                        break;
                }
                break;
            case R.id.time1:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                showTimePickerDialog(alarm1);
                break;
            case R.id.time2:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                showTimePickerDialog(alarm2);
                break;
            case R.id.time3:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                showTimePickerDialog(alarm3);
                break;
            case R.id.time4:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                showTimePickerDialog(alarm4);
                break;
            case R.id.time5:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                showTimePickerDialog(alarm5);
                break;
            case R.id.time6:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                showTimePickerDialog(alarm6);
                break;
            case R.id.state1:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                alarm1.changeState();
                btn_state1.setText(Utils.translateState(alarm1.state));
                break;
            case R.id.state2:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                alarm2.changeState();
                btn_state2.setText(Utils.translateState(alarm2.state));
                break;
            case R.id.state3:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                alarm3.changeState();
                btn_state3.setText(Utils.translateState(alarm3.state));
                break;
            case R.id.state4:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                alarm4.changeState();
                btn_state4.setText(Utils.translateState(alarm4.state));
                break;
            case R.id.state5:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                alarm5.changeState();
                btn_state5.setText(Utils.translateState(alarm5.state));
                break;
            case R.id.state6:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                alarm6.changeState();
                btn_state6.setText(Utils.translateState(alarm6.state));
                break;
            case R.id.className1:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                showEditTextDialog(alarm1);
                break;
            case R.id.className2:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                showEditTextDialog(alarm2);
                break;
            case R.id.className3:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                showEditTextDialog(alarm3);
                break;
            case R.id.className4:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                showEditTextDialog(alarm4);
                break;
            case R.id.className5:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                showEditTextDialog(alarm5);
                break;
            case R.id.className6:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                showEditTextDialog(alarm6);
                break;
            case R.id.btn_yesterday:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                String yesterday = Utils.getYesterday(alarm1.dayOfWeek);
                alarm1 =new Alarm(yesterday,1,database);
                alarm2 =new Alarm(yesterday,2,database);
                alarm3 =new Alarm(yesterday,3,database);
                alarm4 =new Alarm(yesterday,4,database);
                alarm5 =new Alarm(yesterday,5,database);
                alarm6 =new Alarm(yesterday,6,database);
                initTab01();
                break;
            case R.id.btn_tomorrow:
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                String tomorrow = Utils.getTomorrow(alarm1.dayOfWeek);
                alarm1 =new Alarm(tomorrow,1,database);
                alarm2 =new Alarm(tomorrow,2,database);
                alarm3 =new Alarm(tomorrow,3,database);
                alarm4 =new Alarm(tomorrow,4,database);
                alarm5 =new Alarm(tomorrow,5,database);
                alarm6 =new Alarm(tomorrow,6,database);
                initTab01();
                Toast.makeText(this,Utils.sendRequest("http://119.45.36.212/appNote"),Toast.LENGTH_SHORT).show();
                break;
            case R.id.setAlarm:
                setAlarms();
                switch (season){
                    case 0:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen);
                        break;
                    case 1:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_spring);
                        break;
                    case 2:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_summer);
                        break;
                    case 3:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_autumn);
                        break;
                    case 4:
                        mAlarmImg.setImageResource(R.drawable.alarm_chosen_winter);
                        break;
                }
                break;
            case R.id.btn_addOneTimeAlarm:
                oneTimeAlarm.database = database;
                showDatePickerDialog(oneTimeAlarm);
                switch (season){
                    case 0:
                        mOneTimeImg.setImageResource(R.drawable.onetime_chosen);
                        break;
                    case 1:
                        mOneTimeImg.setImageResource(R.drawable.onetime_chosen_spring);
                        break;
                    case 2:
                        mOneTimeImg.setImageResource(R.drawable.onetime_chosen_summer);
                        break;
                    case 3:
                        mOneTimeImg.setImageResource(R.drawable.onetime_chosen_autumn);
                        break;
                    case 4:
                        mOneTimeImg.setImageResource(R.drawable.onetime_chosen_winter);
                        break;
                }
                break;
            case R.id.btn_diyBackground:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,1);
                break;
            case R.id.btn_choseDefaultBackground:
                switch(season){
                    case 0:
                        mDiyImg.setImageResource(R.drawable.diy_chosen);
                        break;
                    case 1:
                        mDiyImg.setImageResource(R.drawable.diy_chosen_spring);
                        break;
                    case 2:
                        mDiyImg.setImageResource(R.drawable.diy_chosen_summer);
                        break;
                    case 3:
                        mDiyImg.setImageResource(R.drawable.diy_chosen_autumn);
                        break;
                    case 4:
                        mDiyImg.setImageResource(R.drawable.diy_chosen_winter);
                        break;
                }

                DialogForSkin dialogForSkin=new DialogForSkin(this);

                dialogForSkin.setListener0(new DialogForSkin.btnskin0Listener() {
                    @Override
                    public void Toskin0(DialogForSkin dialog) {
                        Utils.changeSkin(0,database);
                        switch(season){
                            case 0:
                                mDiyImg.setImageResource(R.drawable.diy_chosen);
                                break;
                            case 1:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_spring);
                                break;
                            case 2:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_summer);
                                break;
                            case 3:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_autumn);
                                break;
                            case 4:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_winter);
                                break;
                        }
                        Utils.restartApp(MainActivity.this);
                    }
                });
                dialogForSkin.setListener1(new DialogForSkin.btnskin1LIstener() {
                    @Override
                    public void Toskin1(DialogForSkin dialog) {
                        Utils.changeSkin(1,database);
                        switch(season){
                            case 0:
                                mDiyImg.setImageResource(R.drawable.diy_chosen);
                                break;
                            case 1:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_spring);
                                break;
                            case 2:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_summer);
                                break;
                            case 3:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_autumn);
                                break;
                            case 4:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_winter);
                                break;
                        }
                        Utils.restartApp(MainActivity.this);
                    }
                });
                dialogForSkin.setListener2(new DialogForSkin.btnskin2LIstener() {
                    @Override
                    public void Toskin2(DialogForSkin dialog) {
                        Utils.changeSkin(2,database);
                        switch(season){
                            case 0:
                                mDiyImg.setImageResource(R.drawable.diy_chosen);
                                break;
                            case 1:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_spring);
                                break;
                            case 2:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_summer);
                                break;
                            case 3:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_autumn);
                                break;
                            case 4:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_winter);
                                break;
                        }
                        Utils.restartApp(MainActivity.this);
                    }
                });
                dialogForSkin.setListener3(new DialogForSkin.btnskin3LIstener() {
                    @Override
                    public void Toskin3(DialogForSkin dialog) {
                        Utils.changeSkin(3,database);
                        switch(season){
                            case 0:
                                mDiyImg.setImageResource(R.drawable.diy_chosen);
                                break;
                            case 1:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_spring);
                                break;
                            case 2:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_summer);
                                break;
                            case 3:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_autumn);
                                break;
                            case 4:
                                mDiyImg.setImageResource(R.drawable.diy_chosen_winter);
                                break;
                        }
                        Utils.restartApp(MainActivity.this);
                    }
                });
                dialogForSkin.setListener4(new DialogForSkin.btnskin4LIstener() {
                    @Override
                    public void Toskin4(DialogForSkin dialog) {
                        Utils.changeSkin(4,database);
                        mDiyImg.setImageResource(R.drawable.diy_chosen);
                        Utils.restartApp(MainActivity.this);
                        return;
                    }
                });
                dialogForSkin.show();
                break;
            default:
                break;
        }
    }

    private void resetImg() {
        mAlarmImg.setImageResource(R.drawable.alarm);
        mOneTimeImg.setImageResource(R.drawable.onetime);
        mDiyImg.setImageResource(R.drawable.diy);
        mHelpImg.setImageResource(R.drawable.help);
    }

    private void initTab01(){
        btn_className1 = tab01.findViewById(R.id.className1);
        btn_className2 = tab01.findViewById(R.id.className2);
        btn_className3 = tab01.findViewById(R.id.className3);
        btn_className4 = tab01.findViewById(R.id.className4);
        btn_className5 = tab01.findViewById(R.id.className5);
        btn_className6 = tab01.findViewById(R.id.className6);

        btn_time1 = tab01.findViewById(R.id.time1);
        btn_time2 = tab01.findViewById(R.id.time2);
        btn_time3 = tab01.findViewById(R.id.time3);
        btn_time4 = tab01.findViewById(R.id.time4);
        btn_time5 = tab01.findViewById(R.id.time5);
        btn_time6 = tab01.findViewById(R.id.time6);

        btn_state1 = tab01.findViewById(R.id.state1);
        btn_state2 = tab01.findViewById(R.id.state2);
        btn_state3 = tab01.findViewById(R.id.state3);
        btn_state4 = tab01.findViewById(R.id.state4);
        btn_state5 = tab01.findViewById(R.id.state5);
        btn_state6 = tab01.findViewById(R.id.state6);

        btn_yesterday = tab01.findViewById(R.id.btn_yesterday);
        btn_tomorrow = tab01.findViewById(R.id.btn_tomorrow);
        mTopNavigation = tab01.findViewById(R.id.topNavigation);
        btn_setAlarms = tab01.findViewById(R.id.setAlarm);

        btn_className1.setOnClickListener(this);
        btn_className2.setOnClickListener(this);
        btn_className3.setOnClickListener(this);
        btn_className4.setOnClickListener(this);
        btn_className5.setOnClickListener(this);
        btn_className6.setOnClickListener(this);

        btn_time1.setOnClickListener(this);
        btn_time2.setOnClickListener(this);
        btn_time3.setOnClickListener(this);
        btn_time4.setOnClickListener(this);
        btn_time5.setOnClickListener(this);
        btn_time6.setOnClickListener(this);

        btn_state1.setOnClickListener(this);
        btn_state2.setOnClickListener(this);
        btn_state3.setOnClickListener(this);
        btn_state4.setOnClickListener(this);
        btn_state5.setOnClickListener(this);
        btn_state6.setOnClickListener(this);

        btn_yesterday.setOnClickListener(this);
        btn_tomorrow.setOnClickListener(this);

        btn_setAlarms.setOnClickListener(this);

        tv_dayOfWeek = tab01.findViewById(R.id.tv_DayOfWeek);
        tv_dayOfWeek.setText(Utils.translateWeek(alarm1.dayOfWeek));

        btn_time1.setText(Utils.changeTimeFormatToString(alarm1.hour,alarm1.minute));
        btn_time2.setText(Utils.changeTimeFormatToString(alarm2.hour,alarm2.minute));
        btn_time3.setText(Utils.changeTimeFormatToString(alarm3.hour,alarm3.minute));
        btn_time4.setText(Utils.changeTimeFormatToString(alarm4.hour,alarm4.minute));
        btn_time5.setText(Utils.changeTimeFormatToString(alarm5.hour,alarm5.minute));
        btn_time6.setText(Utils.changeTimeFormatToString(alarm6.hour,alarm6.minute));

        btn_state1.setText(Utils.translateState(alarm1.state));
        btn_state2.setText(Utils.translateState(alarm2.state));
        btn_state3.setText(Utils.translateState(alarm3.state));
        btn_state4.setText(Utils.translateState(alarm4.state));
        btn_state5.setText(Utils.translateState(alarm5.state));
        btn_state6.setText(Utils.translateState(alarm6.state));

        btn_className1.setText(alarm1.className);
        btn_className2.setText(alarm2.className);
        btn_className3.setText(alarm3.className);
        btn_className4.setText(alarm4.className);
        btn_className5.setText(alarm5.className);
        btn_className6.setText(alarm6.className);


    }

    private void initTab02(){
        final ListViewAdapter listViewAdapter = new ListViewAdapter(MainActivity.this,database);
        final ListView data_view = tab02.findViewById(R.id.data_view);
        data_view.setAdapter(listViewAdapter);
        data_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        listViewAdapter.setOnItemDeleteClickListener(new ListViewAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int i) {
                final Integer id = i + 1;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确定要删除这个闹钟吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.delete("OneTime","id=?",new String[]{id.toString()});
                        database.execSQL("update OneTime set id=id-1 where id>" + id.toString());
                        initTab02();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
        listViewAdapter.setOnDateChangeListener(new ListViewAdapter.onDateChangeListener(){
            @Override
            public void onDateChangeClick(int i) {
                List<OneTimeAlarm> list = Utils.getOneTimeList(database);
                oneTimeAlarm = list.get(i);
                Integer pre_year = oneTimeAlarm.year;
                Integer pre_month = oneTimeAlarm.month;
                Integer pre_date = oneTimeAlarm.dayOfMonth;
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        oneTimeAlarm.changeDate(year,month,dayOfMonth);
                        initTab02();
                    }
                },pre_year,pre_month,pre_date);
                datePickerDialog.show();
            }
        });
        listViewAdapter.setOnTimeChangeListener(new ListViewAdapter.onTimeChangeListener() {
            @Override
            public void onTimeChangeClick(int i) {
                List<OneTimeAlarm> list = Utils.getOneTimeList(database);
                oneTimeAlarm = list.get(i);
                Integer pre_hour = oneTimeAlarm.hour;
                Integer pre_minute = oneTimeAlarm.minute;
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        oneTimeAlarm.changeTime(hourOfDay,minute);
                        initTab02();
                    }
                },pre_hour,pre_minute,true);
                timePickerDialog.show();
            }
        });
        listViewAdapter.setOnDescriptionChangeListener(new ListViewAdapter.onDescriptionChangeListener() {
            @Override
            public void onDescriptionChangeClick(int i) {
                List<OneTimeAlarm> list = Utils.getOneTimeList(database);
                oneTimeAlarm = list.get(i);
                String pre_description = oneTimeAlarm.description;
                final EditText editText = new EditText(MainActivity.this);
                editText.setText(pre_description);
                new AlertDialog.Builder(MainActivity.this).setTitle("请输入备注")
                        .setIcon(android.R.drawable.sym_def_app_icon)
                        .setView(editText)
                        .setNeutralButton("清空", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, false);
                                } catch (NoSuchFieldException | IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                                editText.setText("");
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, true);
                                } catch (NoSuchFieldException | IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                                oneTimeAlarm.changeDescription(editText.getText().toString());
                                initTab02();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, true);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
            }
        });
        btn_addOneTimeAlarm = tab02.findViewById(R.id.btn_addOneTimeAlarm);
        btn_addOneTimeAlarm.setOnClickListener(this);
    }

    private void initTab03(){
        tv_timeNow = tab03.findViewById(R.id.tv_time_tab03);
        btn_diyBackground = tab03.findViewById(R.id.btn_diyBackground);
        btn_choseDefaultBackground = tab03.findViewById(R.id.btn_choseDefaultBackground);

        Calendar calendar = Calendar.getInstance();
        Integer hour_now = calendar.get(Calendar.HOUR);
        Integer minute_now = calendar.get(Calendar.MINUTE);
        tv_timeNow.setText(Utils.changeTimeFormatToString(hour_now,minute_now));
        btn_diyBackground.setOnClickListener(this);
        btn_choseDefaultBackground.setOnClickListener(this);

        ImageView imageView = tab03.findViewById(R.id.iv_bg_tab03);
//        Uri uri = Uri.parse(getCacheDir().getAbsolutePath() + "/background.png");
//        Uri uri = getBackgroundPath(database);
//        if(uri != null){
//        File file = new File(uri.getPath());
//            if(file.exists()) {
//                imageView.setImageURI(uri);
//            }
//        }

        tv_timeNow.setText(getBackgroundPath(database).getPath());
    }

    private void initTab04(){
        String note = Utils.sendRequest("http://119.45.36.212/appNote").replace(",","\n");
        tv_note = tab04.findViewById(R.id.note);
        tv_note.setText(note);

    }

    private void showTimePickerDialog(final Alarm alarm){
        Integer pre_hourOfDay = alarm.hour;
        Integer pre_minute = alarm.minute;
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                alarm.rewriteTime(hourOfDay,minute);
                initTab01();
            }
        },pre_hourOfDay,pre_minute,true);
        timePickerDialog.show();
    }

    private void showTimePickerDialog(final OneTimeAlarm oneTimeAlarm){
        Calendar calendar = Calendar.getInstance();
        Integer pre_hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        Integer pre_minute = calendar.get(Calendar.MINUTE);
        oneTimeAlarm.hour = pre_hourOfDay;
        oneTimeAlarm.minute = pre_minute;
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                oneTimeAlarm.hour = hourOfDay;
                oneTimeAlarm.minute = minute;
                oneTimeAlarm.insertOneTimeAlarm();
                initTab02();
            }
        },pre_hourOfDay,pre_minute,true);
        timePickerDialog.show();
    }

    private void showDatePickerDialog(final OneTimeAlarm oneTimeAlarm){
        Calendar calendar = Calendar.getInstance();
        Integer pre_year = calendar.get(Calendar.YEAR);
        Integer pre_month = calendar.get(Calendar.MONTH);
        Integer pre_date = calendar.get(Calendar.DATE);
        oneTimeAlarm.year = pre_year;
        oneTimeAlarm.month = pre_month;
        oneTimeAlarm.dayOfMonth = pre_date;
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                oneTimeAlarm.year = year;
                oneTimeAlarm.month = month;
                oneTimeAlarm.dayOfMonth = dayOfMonth;
                showTimePickerDialog(oneTimeAlarm);
            }
        },pre_year,pre_month,pre_date);
        datePickerDialog.show();
    }

    private void showEditTextDialog(final Alarm alarm){
        final EditText editText = new EditText(this);
        editText.setText(alarm.className);
        new AlertDialog.Builder(this).setTitle("请输入课程名")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(editText)
                .setNeutralButton("清空", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, false);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        editText.setText("");
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, true);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        alarm.rewriteClassName(editText.getText().toString());
                        switch (alarm.id){
                            case 1:
                                btn_className1.setText(editText.getText().toString());
                                break;
                            case 2:
                                btn_className2.setText(editText.getText().toString());
                                break;
                            case 3:
                                btn_className3.setText(editText.getText().toString());
                                break;
                            case 4:
                                btn_className4.setText(editText.getText().toString());
                                break;
                            case 5:
                                btn_className5.setText(editText.getText().toString());
                                break;
                            case 6:
                                btn_className6.setText(editText.getText().toString());
                                break;
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                    field.set(dialog, true);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }).show();
    }

    private void updateBackgroundPath(Uri uri){
        ContentValues contentValues = new ContentValues();
        contentValues.put("backgroundPath",uri.getPath());
        database.update("DIY",contentValues,"id=?",new String[]{"1"});
        Toast.makeText(MainActivity.this,"updateBackgroundPath",Toast.LENGTH_SHORT).show();
    }

    private Uri getBackgroundPath(SQLiteDatabase database){
        Cursor cursor;
        cursor = database.query("DIY",new String[]{"backgroundPath"},"id=?",new String[]{"1"},null,null,null,null);
        cursor.moveToFirst();
        String path_string = cursor.getString(cursor.getColumnIndex("backgroundpath"));
        cursor.close();
        if(path_string == null){
            return null;
        }else{
        return Uri.parse(path_string);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null){
            mDiyImg.setImageResource(R.drawable.diy_chosen);
            return;
        }
        if(resultCode == Activity.RESULT_OK){
            if (requestCode == 1) {
                Uri uri = data.getData();
                File file = new File(uri.getPath());
//                Toast.makeText(this, "文件路径："+ file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "文件name："+ file.getName(), Toast.LENGTH_LONG).show();
                ImageView imageView = tab03.findViewById(R.id.iv_bg_tab03);
                imageView.setImageURI(uri);
                updateBackgroundPath(uri);
                TextView textView = tab03.findViewById(R.id.tv_time_tab03);
                //textView.setText(uri.getPath());
                mDiyImg.setImageResource(R.drawable.diy_chosen);
            }
        }
    }
}
