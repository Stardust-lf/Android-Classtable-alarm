package com.example.ver20;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

public class DialogForSkin extends Dialog implements View.OnClickListener {


    private Button btn_skin0, btn_skin1,btn_skin2,btn_skin3,btn_skin4;
    private btnskin0Listener listener0;
    private btnskin1LIstener listener1;
    private btnskin2LIstener listener2;
    private btnskin3LIstener listener3;
    private btnskin4LIstener listener4;

    public DialogForSkin(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置宽度
        WindowManager m=getWindow().getWindowManager();
        Display d=m.getDefaultDisplay();
        WindowManager.LayoutParams p=getWindow().getAttributes();
        Point size=new Point();
        d.getSize(size);
        p.width=(int)(size.x*0.6);
        p.height=(int)(size.y*0.8);
        getWindow().setAttributes(p);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_for_skin);
        btn_skin0 = findViewById(R.id.btn_skin0);
        btn_skin1 = findViewById(R.id.btn_skin1);
        btn_skin2 = findViewById(R.id.btn_skin2);
        btn_skin3 = findViewById(R.id.btn_skin3);
        btn_skin4 = findViewById(R.id.btn_skin4);
        btn_skin0.setOnClickListener(this);
        btn_skin1.setOnClickListener(this);
        btn_skin2.setOnClickListener(this);
        btn_skin3.setOnClickListener(this);
        btn_skin4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_skin0:
                listener0.Toskin0(this);
                break;
            case R.id.btn_skin1:
                listener1.Toskin1(this);
                break;
            case R.id.btn_skin2:
                listener2.Toskin2(this);
                break;
            case R.id.btn_skin3:
                listener3.Toskin3(this);
                break;
            case R.id.btn_skin4:
                listener4.Toskin4(this);
                break;
        }

    }

    public interface btnskin0Listener {
        void Toskin0(DialogForSkin dialog);
    }
    public interface btnskin1LIstener {
        void Toskin1(DialogForSkin dialog);

    }
    public interface btnskin2LIstener {
        void Toskin2(DialogForSkin dialog);

    }
    public interface btnskin3LIstener {
        void Toskin3(DialogForSkin dialog);

    }
    public interface btnskin4LIstener {
        void Toskin4(DialogForSkin dialog);

    }

    public void setListener0(btnskin0Listener listener){
        this.listener0=listener;
    }
    public void setListener1(btnskin1LIstener lIstener){
        this.listener1=lIstener;
    }
    public void setListener2(btnskin2LIstener lIstener){
        this.listener2=lIstener;
    }
    public void setListener3(btnskin3LIstener lIstener) { this.listener3=lIstener; }
    public void setListener4(btnskin4LIstener lIstener){
        this.listener4=lIstener;
    }
}