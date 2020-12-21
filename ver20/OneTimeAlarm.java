package com.example.ver20;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OneTimeAlarm {

    public Integer id;
    public Integer year;
    public Integer month;
    public Integer dayOfMonth;
    public Integer hour;
    public Integer minute;
    public String description;
    public SQLiteDatabase database;

    public OneTimeAlarm(Integer id,Integer year,Integer month, Integer dayOfMonth, Integer hour, Integer minute, String description, SQLiteDatabase database){
        this.id = id;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.hour = hour;
        this.minute = minute;
        this.description = description;
        this.database = database;
    }

    public OneTimeAlarm(){
        this.id = 0;
        this.year = 0;
        this.month = 0;
        this.dayOfMonth = 0;
        this.hour = 0;
        this.minute = 0;
        this.description = null;
        this.database = null;
    }

    public void changeTime(Integer hour,Integer minute){
        this.hour = hour;
        this.minute = minute;
        ContentValues contentValues1 = new ContentValues();
        ContentValues contentValues2 = new ContentValues();
        contentValues1.put("hour",hour);
        contentValues2.put("minute",minute);
        database.update("OneTime",contentValues1,"id=?",new String[]{id.toString()});
        database.update("OneTime",contentValues2,"id=?",new String[]{id.toString()});
    }

    public void changeDate(Integer year,Integer month,Integer dayOfMonth){
        this.year = year;
        this.month = month;
        ContentValues contentValues1 = new ContentValues();
        ContentValues contentValues2 = new ContentValues();
        ContentValues contentValues3 = new ContentValues();
        contentValues1.put("year",year);
        contentValues2.put("month",month);
        contentValues3.put("date",dayOfMonth);
        database.update("OneTime",contentValues1,"id=?",new String[]{id.toString()});
        database.update("OneTime",contentValues2,"id=?",new String[]{id.toString()});
        database.update("OneTime",contentValues3,"id=?",new String[]{id.toString()});
    }

    public void changeDescription(String description){
        this.description = description;
        ContentValues contentValues = new ContentValues();
        contentValues.put("description",description);
        database.update("OneTime",contentValues,"id=?",new String[]{id.toString()});
    }

    public void insertOneTimeAlarm(){
        database.execSQL("insert into OneTime(id,year,month,date,hour,minute,description) values((select count(*) from OneTime)+1," + year + "," + month + "," + dayOfMonth + "," + hour + "," + minute + "," + description + ")");
    }
}
