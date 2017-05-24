package com.example.administrator.appdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.TypiconsModule;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/3/12.
 */

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    //存储
    public static Context mContext;
    //页面常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;

    private ViewPager viewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    //UI Object
    private TextView textTopContent;
    private TextView login;

    private ToggleButton bottom_toggle_1;
    private ToggleButton bottom_toggle_2;
    private ToggleButton bottom_toggle_3;

    private View bottomBar_1;
    private View bottomBar_2;
    private View bottomBar_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify
                .with(new FontAwesomeModule())
                .with(new TypiconsModule());
        setContentView(R.layout.home);
        //如果用户未登录跳转到登录
        mContext = getApplicationContext();
        if(SPUtils.contains(mContext,"expectedDate")){
           // Log.i("xl","main");
            Toast.makeText(this,"欢迎您,"+SPUtils.get(mContext,"userName",""),Toast.LENGTH_SHORT).show();
        }else {
            startActivity(new Intent(this,LoginActivity.class));
        }
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        setSelectedState(PAGE_ONE);
    }


    //初始化
    private void bindViews(){
        //获取视图对象
        textTopContent = (TextView) findViewById(R.id.text_Panel);
        bottom_toggle_1 = (ToggleButton) findViewById(R.id.bottom_toggle_1);
        bottom_toggle_2 = (ToggleButton) findViewById(R.id.bottom_toggle_2);
        bottom_toggle_3 = (ToggleButton) findViewById(R.id.bottom_toggle_3);

        //viewGroup = (ViewGroup) findViewById(R.id.bottomPanel);
        bottomBar_1 = findViewById(R.id.bottom_bar1);
        bottomBar_2 = findViewById(R.id.bottom_bar2);
        bottomBar_3 = findViewById(R.id.bottom_bar3);
        login = (TextView) findViewById(R.id.tv_login);

        //设置监听事件

        bottomBar_1.setOnClickListener(listener);
        bottomBar_2.setOnClickListener(listener);
        bottomBar_3.setOnClickListener(listener);
        login.setOnClickListener(listener);

        viewPager = (ViewPager) findViewById(R.id.scrollView);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(PAGE_ONE);
        viewPager.addOnPageChangeListener(MainActivity.this);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bottom_bar1:
                    viewPager.setCurrentItem(PAGE_ONE);
                    setToggleButtonCheckedState(PAGE_ONE);
                    break;
                case R.id.bottom_bar2:
                    viewPager.setCurrentItem(PAGE_TWO);
                    setToggleButtonCheckedState(PAGE_TWO);
                    break;
                case R.id.bottom_bar3:
                    viewPager.setCurrentItem(PAGE_THREE);
                    setToggleButtonCheckedState(PAGE_THREE);
                    break;
                case R.id.tv_login:
                    if(SPUtils.contains(mContext,"userName")){
                        startActivity(new Intent(MainActivity.this,UserInfoActivity.class));
                    }else {
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    }
                    break;
            }
        }
    };


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    //ViewPager滑动状态处理
    @Override
    public void onPageScrollStateChanged(int state) {
//state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case PAGE_ONE:
                    setSelectedState(PAGE_ONE);
                    setToggleButtonCheckedState(PAGE_ONE);
                    textTopContent.setText("每日");
                    break;
                case PAGE_TWO:
                    setSelectedState(PAGE_TWO);
                    setToggleButtonCheckedState(PAGE_TWO);
                    textTopContent.setText("安全");
                    break;
                case PAGE_THREE:
                    setSelectedState(PAGE_THREE);
                    setToggleButtonCheckedState(PAGE_THREE);
                    textTopContent.setText("发现");
                    break;
            }
        }
    }

    //Selected state 处理
    public void setSelectedState(int page){
        bottomBar_1.setSelected(false);
        bottomBar_2.setSelected(false);
        bottomBar_3.setSelected(false);

        switch (page){
            case PAGE_ONE:
                bottomBar_1.setSelected(true);
                break;
            case PAGE_TWO:
                bottomBar_2.setSelected(true);
                break;
            case PAGE_THREE:
                bottomBar_3.setSelected(true);
                break;
        }
    }

    //ToggleButtonCheck 处理
    public void setToggleButtonCheckedState(int page){
        bottom_toggle_1.setChecked(false);
        bottom_toggle_2.setChecked(false);
        bottom_toggle_3.setChecked(false);

        switch (page){
            case PAGE_ONE:
                bottom_toggle_1.setChecked(true);
                break;
            case PAGE_TWO:
                bottom_toggle_2.setChecked(true);
                break;
            case PAGE_THREE:
                bottom_toggle_3.setChecked(true);
                break;
        }
    }


    //双击退出
    private long exitTime;//类型为基本类型long,不是对象类型Long
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-exitTime > 2000){
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }
        else {
            finish();
        }
    }
}
