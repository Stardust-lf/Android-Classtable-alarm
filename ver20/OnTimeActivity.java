package com.example.ver20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class OnTimeActivity extends Activity {
    LinearLayout ll;
    TextView tv_1,tv_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.tab03);
        ll = findViewById(R.id.ll_tab03);
        ll.setVisibility(View.INVISIBLE);
        Calendar calendar = Calendar.getInstance();
        tv_1 = findViewById(R.id.tv_time_tab03);
        tv_2 = findViewById(R.id.tv_description_tab03);
        tv_1.setText(Utils.changeTimeFormatToString(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)));
        tv_2.setText(Utils.changeDateFormatToString(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)));
    }
}
