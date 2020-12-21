package com.example.ver20;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private onItemDeleteListener mOnItemDeleteListener;
    private onDateChangeListener mOnDateChangeListener;
    private onTimeChangeListener mOnTimeChangeListener;
    private onDescriptionChangeListener mOnDescriptionChangeListener;
    private Integer season;
    private String color1,color2;
    List<OneTimeAlarm> alarmList;
    @Override
    public int getCount(){
        return alarmList.size();
    }
    @Override
    public Object getItem(int position){
        return alarmList.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent){
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item,null);
            viewHolder.mButton1 = view.findViewById(R.id.tv_item_date);
            viewHolder.mButton2 = view.findViewById(R.id.tv_item_time);
            viewHolder.mButton3 = view.findViewById(R.id.tv_item_description);
            viewHolder.mButton4 = view.findViewById(R.id.tv_item_delete);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        if(position%2 == 0){
            viewHolder.mButton1.setBackgroundColor(Color.parseColor(color1));
            viewHolder.mButton2.setBackgroundColor(Color.parseColor(color1));
            viewHolder.mButton3.setBackgroundColor(Color.parseColor(color1));
        }
        else{
            viewHolder.mButton1.setBackgroundColor(Color.parseColor(color2));
            viewHolder.mButton2.setBackgroundColor(Color.parseColor(color2));
            viewHolder.mButton3.setBackgroundColor(Color.parseColor(color2));
        }
        viewHolder.mButton1.setText(Utils.changeDateFormatToString(alarmList.get(position).year,alarmList.get(position).month,alarmList.get(position).dayOfMonth));
        viewHolder.mButton2.setText(Utils.changeTimeFormatToString(alarmList.get(position).hour,alarmList.get(position).minute));
        viewHolder.mButton3.setText(alarmList.get(position).description);
        viewHolder.mButton1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnDateChangeListener.onDateChangeClick(position);
                return false;
            }
        });
        viewHolder.mButton2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnTimeChangeListener.onTimeChangeClick(position);
                return false;
            }
        });
        viewHolder.mButton3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnDescriptionChangeListener.onDescriptionChangeClick(position);
                return false;
            }
        });
        viewHolder.mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(position);
            }
        });

        return view;
    }

    public ListViewAdapter(Context context, SQLiteDatabase database){
        this.context = context;
        this.season = Utils.getSkin(database);
        alarmList = Utils.getOneTimeList(database);
        switch(season){
            case 0:
                color1 = "#C0C0C0";
                color2 = "#F2EEE7";
                break;
            case 1:
                color1 = "#DDC1C6";
                color2 = "#F2EEE7";
                break;
            case 2:
                color1 = "#97CACA";
                color2 = "#F2EEE7";
                break;
            case 3:
                color1 = "#F2E751";
                color2 = "#F2EEE7";
                break;
            case 4:
                color1 = "#F7F0C6";
                color2 = "#C2C4B6";
                break;
        }
    }

    class ViewHolder{
        Button mButton1;
        Button mButton2;
        Button mButton3;
        ImageButton mButton4;
    }

    public interface onDateChangeListener{
        void onDateChangeClick(int i);
    }

    public interface onTimeChangeListener{
        void onTimeChangeClick(int i);
    }

    public interface onItemDeleteListener{
        void onDeleteClick(int i);
    }

    public interface  onDescriptionChangeListener{
        void onDescriptionChangeClick(int i);
    }

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener){
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }

    public void setOnDateChangeListener(onDateChangeListener mOnDateChangeListener){
        this.mOnDateChangeListener = mOnDateChangeListener;
    }

    public void setOnTimeChangeListener(onTimeChangeListener mOnTimeChangeListener){
        this.mOnTimeChangeListener = mOnTimeChangeListener;
    }

    public void setOnDescriptionChangeListener(onDescriptionChangeListener mOnDescriptionChangeListener){
        this.mOnDescriptionChangeListener = mOnDescriptionChangeListener;
    }
}
