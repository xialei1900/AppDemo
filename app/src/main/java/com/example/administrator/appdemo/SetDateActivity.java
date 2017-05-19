package com.example.administrator.appdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SetDateActivity extends AppCompatActivity {

    private DatePicker datePicker = null;
    private DatePicker dateLast = null;
    private EditText menstruation = null;
    private Button getDate = null;
    private TextView tv_title = null;
    private TextView tv_setExpectedDate = null;

    private int year;
    private int month;
    private int day;
    private int yjzq;//月经周期
    private Date expectedDate;
    private boolean flag = true;

    private TextView calculation = null;
    private View line_date = null;
    private RelativeLayout tip = null;
    private View line_bottom = null;
    private RelativeLayout last = null;
    private RelativeLayout days = null;
    private TextView unCalculation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
        setContentView(R.layout.activity_set_date);

        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        datePicker = (DatePicker) findViewById(R.id.date);
        dateLast = (DatePicker) findViewById(R.id.date_last);
        menstruation = (EditText) findViewById(R.id.menstruation);
        getDate = (Button) findViewById(R.id.btn_get_date);
        tv_setExpectedDate = (TextView) findViewById(R.id.tv_setExpectedDate);

        calculation = (TextView) findViewById(R.id.calculation);
        line_date = findViewById(R.id.line_date);
        tip = (RelativeLayout) findViewById(R.id.tip);
        line_bottom = findViewById(R.id.line_bottom);
        last = (RelativeLayout) findViewById(R.id.last);
        days = (RelativeLayout) findViewById(R.id.days);
        unCalculation = (TextView) findViewById(R.id.unCalculation);
        tv_title = (TextView) findViewById(R.id.tv_title);
        //界面隐藏
        calculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitWidget(true);
            }
        });
        unCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitWidget(false);
            }
        });

        //预产期修改监听
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SetDateActivity.this.year = year;
                SetDateActivity.this.month = monthOfYear+1;
                SetDateActivity.this.day = dayOfMonth;
                tv_title.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        });

        //末次月经日期监听
        getDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menstruation.getText().toString().equals(""))
                {
                    Toast.makeText(SetDateActivity.this,"请输入月经周期天数",Toast.LENGTH_SHORT).show();
                }else if(flag){
                    Toast.makeText(SetDateActivity.this,"请修改末次月经日期",Toast.LENGTH_SHORT).show();
                }
                else{
                    yjzq = Integer.parseInt(menstruation.getText().toString());
                    getExpectedDate();
                }

            }
        });

        //末次月经日期初始化
        dateLast.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SetDateActivity.this.year = year;
                SetDateActivity.this.month = monthOfYear+1;
                SetDateActivity.this.day = dayOfMonth;
                flag = false;
            }
        });

        //确认预产期
        tv_setExpectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SetDateActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }


    //隐藏控件
    private void hitWidget(boolean state){
        if(state){
            datePicker.setVisibility(View.GONE);
            line_date.setVisibility(View.GONE);
            tip.setVisibility(View.GONE);
            last.setVisibility(View.VISIBLE);
            days.setVisibility(View.VISIBLE);
            line_bottom.setVisibility(View.VISIBLE);
            unCalculation.setVisibility(View.VISIBLE);
        }else
        {
            datePicker.setVisibility(View.VISIBLE);
            line_date.setVisibility(View.VISIBLE);
            tip.setVisibility(View.VISIBLE);
            last.setVisibility(View.GONE);
            days.setVisibility(View.GONE);
            line_bottom.setVisibility(View.GONE);
            unCalculation.setVisibility(View.GONE);
        }
    }

    //计算预产期
    private void getExpectedDate(){
        Calendar c = Calendar.getInstance();

        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            expectedDate = format1.parse(String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(expectedDate);
        c.add(Calendar.MONTH,9);
        c.add(Calendar.DAY_OF_MONTH,7+yjzq-28);
        expectedDate = c.getTime();
        //修改显示时间
        tv_title.setText(new SimpleDateFormat("yyyy-MM-dd").format(expectedDate));
    }

    //保存预产期
    public void setExpectedDate(){
        final RequestQueue requestQueue = Volley.newRequestQueue(SetDateActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_SHORT).show();
                if(response.equals("S")){
                    Intent intent = new Intent(SetDateActivity.this,SetDateActivity.class);
                    SetDateActivity.this.startActivity(intent);
                }else if(response.equals("E")){
                    Toast.makeText(SetDateActivity.this,"发生了未知错误,保存预产期失败!",Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SetDateActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("action", "SetExpectedDate");
                map.put("expectedDate", new SimpleDateFormat("yyyy-MM-dd").format(expectedDate));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
