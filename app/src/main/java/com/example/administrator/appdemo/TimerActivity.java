package com.example.administrator.appdemo;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends AppCompatActivity {

    private Chronometer timer;
    private TextView showCount;
    private Button count;
    private Button refresh;
    int FLAG_1 = 0;
    boolean FLAG_2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timer = (Chronometer) findViewById(R.id.timer);
        showCount = (TextView) findViewById(R.id.showCount);
        count = (Button) findViewById(R.id.btn_count);
        refresh = (Button) findViewById(R.id.btn_refresh);

        //开始计数
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FLAG_2)
                {
                    timer.setBase(SystemClock.elapsedRealtime());//计时器清零
                    int hour = (int) ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000 / 60);
                    timer.setFormat("0"+String.valueOf(hour)+":%s");
                    timer.start();
                    FLAG_2 = false;
                }
                showCount.setText(""+FLAG_1);
                FLAG_1++;
            }
        });
        //重新计数
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!FLAG_2)
                {
                    timer.stop();
                    FLAG_2 = true;
                }
                showCount.setText("点击下方按键开始计数");
                FLAG_1 = 1;
            }
        });
    }
}
