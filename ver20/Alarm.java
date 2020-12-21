package com.example.ver20;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Alarm {

    public String dayOfWeek;
    public Integer id;
    public Integer hour;
    public Integer minute;
    public String className;
    public Boolean state;
    public SQLiteDatabase database;

    public Alarm(String dayOfWeek, Integer id,SQLiteDatabase database){
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.hour = Utils.getHour(dayOfWeek,id,database);
        this.minute = Utils.getMinute(dayOfWeek,id,database);
        this.className = Utils.getClassName(dayOfWeek,id,database);
        this.database = database;
        if(Utils.getState(dayOfWeek,id,database) == 1){
            this.state = true;
        }
        else{
            this.state = false;
        }
    }

    public void rewriteTime(Integer hour,Integer minute){
        this.hour = hour;
        this.minute = minute;
        String tableName = dayOfWeek;
        ContentValues contentValues1 = new ContentValues();
        ContentValues contentValues2 = new ContentValues();
        contentValues1.put("hour",hour);
        contentValues2.put("minute",minute);
        database.update(tableName,contentValues1,"id=?",new String[]{id.toString()});
        database.update(tableName,contentValues2,"id=?",new String[]{id.toString()});
    }

    public void rewriteClassName(String className){
        this.className = className;
        String tableName = dayOfWeek;
        String mid = id.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("className",className);
        database.update(tableName,contentValues,"id=?",new String[]{mid});
    }

    public void rewriteState(Boolean state){
        this.state = state;
        String tableName = dayOfWeek;
        String mid = id.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("state",state);
        database.update(tableName,contentValues,"id=?",new String[]{mid});
    }

    public void changeState(){
        if(state){
            rewriteState(false);
        }
        else{
            rewriteState(true);
        }
    }

}
