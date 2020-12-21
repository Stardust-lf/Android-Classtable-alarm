package com.example.ver20;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Utils {

    public static String numberToDayOfWeek(Integer number){
        String dayOfWeek = null;
        switch (number){
            case 1:
                dayOfWeek = "Monday";
                break;
            case 2:
                dayOfWeek = "Tuesday";
                break;
            case 3:
                dayOfWeek = "Wednesday";
                break;
            case 4:
                dayOfWeek = "Thursday";
                break;
            case 5:
                dayOfWeek = "Friday";
                break;
            case 6:
                dayOfWeek = "Saturday";
                break;
            case 7:
                dayOfWeek = "Sunday";
                break;
        }
        return dayOfWeek;
    }

    public static Integer getHour(String dayOfWeek, Integer id, SQLiteDatabase database){
        String str_id = id.toString();
        Cursor cursor;
        cursor = database.query(dayOfWeek,new String[]{"hour"},"id=?",new String[]{str_id},null,null,null,null);
        cursor.moveToFirst();
        Integer result = cursor.getInt(cursor.getColumnIndex("hour"));
        cursor.close();
        return result;
    }

    public static Integer getMinute(String dayOfWeek, Integer id, SQLiteDatabase database){
        String str_id = id.toString();
        Cursor cursor;
        cursor = database.query(dayOfWeek,new String[]{"minute"},"id=?",new String[]{str_id},null,null,null,null);
        cursor.moveToFirst();
        Integer result = cursor.getInt(cursor.getColumnIndex("minute"));
        cursor.close();
        return result;
    }

    public static String getClassName(String dayOfWeek, Integer id, SQLiteDatabase database){
        String str_id = id.toString();
        Cursor cursor;
        cursor = database.query(dayOfWeek,new String[]{"className"},"id=?",new String[]{str_id},null,null,null,null);
        cursor.moveToFirst();
        String  result = cursor.getString(cursor.getColumnIndex("className"));
        cursor.close();
        return result;
    }

    public static Integer getState(String dayOfWeek, Integer id, SQLiteDatabase database){
        String str_id = id.toString();
        Cursor cursor;
        cursor = database.query(dayOfWeek,new String[]{"state"},"id=?",new String[]{str_id},null,null,null,null);
        cursor.moveToFirst();
        Integer result = cursor.getInt(cursor.getColumnIndex("state"));
        cursor.close();
        return result;
    }

    public static Integer getSkin(SQLiteDatabase database){
        Cursor cursor;
        cursor = database.query("DIY",new String[]{"defaultBackground"},"id=?",new String[]{"1"},null,null,null,null);
        cursor.moveToFirst();
        Integer result = cursor.getInt(cursor.getColumnIndex("defaultBackground"));
        cursor.close();
        return result;
    }

    public static void changeSkin(Integer newSkin,SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put("defaultBackground",newSkin);
        database.update("DIY",contentValues,"id=?",new String[]{"1"});
    }

    public static String translateWeek(String dayOfWeek) {
        switch (dayOfWeek) {
            case "Sunday":
                return "星期日";
            case "Monday":
                return "星期一";
            case "Tuesday":
                return "星期二";
            case "Wednesday":
                return "星期三";
            case "Thursday":
                return "星期四";
            case "Friday":
                return "星期五";
            case "Saturday":
                return "星期六";
            default:
                return "";
        }
    }

    public static String getWeekInEnglish() {
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return "";
        }
    }

    public static String translateWeekToEnglish(int i){
        switch (i) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return "";
        }
    }

    public static String getYesterday(String dayOfWeek){
        String yesterday = null;
        switch(dayOfWeek){
            case "Monday":
                yesterday = "Sunday";
                break;
            case "Tuesday":
                yesterday = "Monday";
                break;
            case "Wednesday":
                yesterday = "Tuesday";
                break;
            case "Thursday":
                yesterday = "Wednesday";
                break;
            case "Friday":
                yesterday = "Thursday";
                break;
            case "Saturday":
                yesterday = "Friday";
                break;
            case "Sunday":
                yesterday = "Saturday";
                break;
        }
        return yesterday;
    }

    public static String getTomorrow(String dayOfWeek){
        String tomorrow = null;
        switch(dayOfWeek){
            case "Monday":
                tomorrow = "Tuesday";
                break;
            case "Tuesday":
                tomorrow = "Wednesday";
                break;
            case "Wednesday":
                tomorrow = "Thursday";
                break;
            case "Thursday":
                tomorrow = "Friday";
                break;
            case "Friday":
                tomorrow = "Saturday";
                break;
            case "Saturday":
                tomorrow = "Sunday";
                break;
            case "Sunday":
                tomorrow = "Monday";
                break;
        }
        return tomorrow;
    }

    public static String changeTimeFormatToString(Integer hour,Integer minute){
        String str_hour = hour.toString();
        String str_minute = minute.toString();
        if(hour<10){
            str_hour = "0" + str_hour;
        }
        if(minute<10){
            str_minute = "0" + str_minute;
        }
        return str_hour + ":" + str_minute;
    }

    public static String changeDateFormatToString(Integer year,Integer month,Integer date){
        String str_year = year.toString();
        String str_month = month.toString();
        String str_date = date.toString();
        if(month<10){
            str_month = "0" + str_month;
        }
        if(date<10){
            str_date = "0" + str_date;
        }
        return str_year + "/" + str_month + "/" + str_date;
    }

    public static String translateState(Boolean bol){
        String result = null;
        if(bol){
            result = "ON";
        }
        else{
            result = "OFF";
        }
        return result;
    }

    public static List<OneTimeAlarm> getOneTimeList(SQLiteDatabase database){
        List<OneTimeAlarm> list = new ArrayList<OneTimeAlarm>();
        Cursor cursor = database.query("OneTime",null,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            list.add(new OneTimeAlarm(cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getInt(cursor.getColumnIndex("year")),
                cursor.getInt(cursor.getColumnIndex("month")),
                cursor.getInt(cursor.getColumnIndex("date")),
                cursor.getInt(cursor.getColumnIndex("hour")),
                cursor.getInt(cursor.getColumnIndex("minute")),
                cursor.getString(cursor.getColumnIndex("description")),
                database
                ));
        };
        cursor.close();
        return list;
    }

    public static void restartApp(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (null == packageManager) {
            return;
        }
        final Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    public static String sendRequest(final String url) {
        final StringBuilder sb = new StringBuilder();
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL requestUrl = new URL(url);
                    connection = (HttpURLConnection) requestUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    if (connection.getResponseCode() == 200) {
                        InputStream in = connection.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(in));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                        System.out.println(sb);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return sb.toString();
            }
        });
        new Thread(task).start();
        String s = null;
        try {
            s = task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
