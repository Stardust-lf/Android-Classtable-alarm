package com.example.ver20;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(Context context){
        super(context,"TimeList.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase database){
         int num = 1;
         while(num<=7){
            String dayOfWeek = Utils.numberToDayOfWeek(num);
            database.execSQL("create table if not exists " + dayOfWeek +"(id int primary Key,hour int,minute int,className varchar(20),state int)");
            database.execSQL("insert into " + dayOfWeek + "(id,hour,minute,className,state) values(1,8,30,null,0)");
            database.execSQL("insert into " + dayOfWeek + "(id,hour,minute,className,state) values(2,10,30,null,0)");
            database.execSQL("insert into " + dayOfWeek + "(id,hour,minute,className,state) values(3,14,30,null,0)");
            database.execSQL("insert into " + dayOfWeek + "(id,hour,minute,className,state) values(4,16,30,null,0)");
            database.execSQL("insert into " + dayOfWeek + "(id,hour,minute,className,state) values(5,19,0,null,0)");
            database.execSQL("insert into " + dayOfWeek + "(id,hour,minute,className,state) values(6,20,50,null,0)");
            num++;
    }
         database.execSQL("create table if not exists OneTime(id INTEGER primary Key,year int,month int,date int,hour int,minute int,description varvhar(20))");
         database.execSQL("create table if not exists DIY(id INTEGER primary Key,defaultBackground int,defaultmusic int,backgroundpath varchar(100),musicpath varchar(100))");
         database.execSQL("insert into DIY(id,defaultBackground,defaultmusic,backgroundpath,musicpath) values(1,0,0,null,null)");
}
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newversion){};
}
