
package com.example.administrator.appdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bean.DailyRemindBeam;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.id;


/**
 * Created by Administrator on 2017/3/16.
 */

public class Myfragment_1 extends android.support.v4.app.Fragment {
    public Myfragment_1() {

    }

    private TextView tv_expectedDate;
    private TextView babyPhysical;
    private TextView momPhysical;
    private TextView checkRemind;
    private TextView nutritionRecipes;
    private TextView drinkingRemind;

    private View checkRemind_item;
    private View nutritionRecipes_item;
    private View drinkingRemind_item;

    private TextView weekAndDay;
    private TextView incrase;
    private TextView reduce;
    private int week = 1;
    private int day = 1;
    private int betweenDays;
    private int index;

    SimpleDateFormat drinkTimeFormat;
    private Date drinkingTime_1;
    private Date drinkingTime_2;
    private Date drinkingTime_3;
    private Date drinkingTime_4;
    private Date drinkingTime_5;
    private Date drinkingTime_6;
    private Date drinkingTime_7;
    private Date drinkingTime_8;
    private Date drinkingTime_0;

    private NotificationManager mNManager;
    private Notification notify;
    Bitmap LargeBitmap = null;
    private static final int NOTIFYID_1 = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content_1, container, false);
        tv_expectedDate = (TextView) view.findViewById(R.id.tv_expectedDate);
        babyPhysical = (TextView) view.findViewById(R.id.babyPhysical);
        momPhysical = (TextView) view.findViewById(R.id.momPhysical);
        checkRemind = (TextView) view.findViewById(R.id.checkRemind);
        nutritionRecipes = (TextView) view.findViewById(R.id.nutritionRecipes);
        drinkingRemind = (TextView) view.findViewById(R.id.drinkingRemind);
        weekAndDay = (TextView) view.findViewById(R.id.tv_weekAndDay);
        incrase = (TextView) view.findViewById(R.id.tv_right);
        reduce = (TextView) view.findViewById(R.id.tv_left);
        checkRemind_item = view.findViewById(R.id.checkRemind_item);
        nutritionRecipes_item = view.findViewById(R.id.nutritionRecipes_item);
        drinkingRemind_item = view.findViewById(R.id.drinkingRemind_item);

        incrase.setOnClickListener(updateListener);
        reduce.setOnClickListener(updateListener);
        babyPhysical.setOnClickListener(itemClick);
        momPhysical.setOnClickListener(itemClick);
        checkRemind_item.setOnClickListener(itemClick);
        nutritionRecipes_item.setOnClickListener(itemClick);
        drinkingRemind_item.setOnClickListener(itemClick);

        drinkTimeFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            drinkingTime_1 = drinkTimeFormat.parse("08:00:00");
            drinkingTime_2 = drinkTimeFormat.parse("09:00:00");
            drinkingTime_3 = drinkTimeFormat.parse("11:30:00");
            drinkingTime_4 = drinkTimeFormat.parse("13:30:00");
            drinkingTime_5 = drinkTimeFormat.parse("15:30:00");
            drinkingTime_6 = drinkTimeFormat.parse("17:30:00");
            drinkingTime_7 = drinkTimeFormat.parse("19:00:00");
            drinkingTime_8 = drinkTimeFormat.parse("20:30:00");
            drinkingTime_0 = drinkTimeFormat.parse("23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(SPUtils.contains(MainActivity.mContext,"expectedDate")){
            //设置预产期
            showExpectedDate();
            //设置喝水提醒
            Log.i("xl",""+2);
            setDrinkingRemind();
            //获取每日提醒数据更新
            Log.i("xl",""+3);
            getRemindToday();
            Log.i("xl",""+4);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    //设置预产期
    public void showExpectedDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date expectedDate = format.parse(SPUtils.get(MainActivity.mContext, "expectedDate", "").toString());
            betweenDays = DateUtils.daysBetween(new Date(System.currentTimeMillis()), expectedDate);
            SPUtils.put(MainActivity.mContext, "betweenDays", betweenDays);
            //Log.i("xl","预产期："+betweenDays);
            tv_expectedDate.setText("距离预产期还有 " + betweenDays + " 天");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //获取每日提醒数据更新
    public void getRemindToday() {

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DailyRemindBeam dailyRemindBeam = gson.fromJson(response, DailyRemindBeam.class);

                if (dailyRemindBeam.getWeek() < dailyRemindBeam.getCheckWeek() - 1) {
                    checkRemind.setText("距第" + dailyRemindBeam.getCheckTimes() + "次产检：还有" + (dailyRemindBeam.getCheckWeek() - dailyRemindBeam.getWeek()) + "周");
                } else if (dailyRemindBeam.getCheckWeek() == dailyRemindBeam.getWeek() - 1) {
                    checkRemind.setText("距第" + dailyRemindBeam.getCheckTimes() + "次产检：下一周要产检了");
                } else if (dailyRemindBeam.getCheckWeek() == dailyRemindBeam.getWeek()) {
                    checkRemind.setText("距第" + dailyRemindBeam.getCheckTimes() + "次产检：本周产检哦");
                }
                babyPhysical.setText(dailyRemindBeam.getBabyPhysical());
                momPhysical.setText(dailyRemindBeam.getMomPhysical());
                nutritionRecipes.setText(dailyRemindBeam.getNutritionRecipes());
                weekAndDay.setText("" + dailyRemindBeam.getWeek() + "周+" + dailyRemindBeam.getDay() + "天");
                week = dailyRemindBeam.getWeek();
                day = dailyRemindBeam.getDay();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("xl", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("action", "getRemindToday");
                map.put("expectedDate", SPUtils.get(MainActivity.mContext, "expectedDate", "").toString());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void setDrinkingRemind() {
        try {
            Date timeNow = drinkTimeFormat.parse(drinkTimeFormat.format(new Date(System.currentTimeMillis())));
            if (timeNow.getTime() < drinkingTime_1.getTime()) {
                drinkingRemind.setText((drinkingTime_1.getTime() - timeNow.getTime()) / (1000 * 60 * 60) + "小时" + ((drinkingTime_1.getTime() - timeNow.getTime()) / 1000) % 3600 / 60 + "分钟后提醒");
                index = 0;
            } else if (timeNow.getTime() > drinkingTime_1.getTime() && timeNow.getTime() < drinkingTime_2.getTime()) {
                drinkingRemind.setText((drinkingTime_2.getTime() - timeNow.getTime()) / (1000 * 60 * 60) + "小时" + ((drinkingTime_2.getTime() - timeNow.getTime()) / 1000) % 3600 / 60 + "分钟后提醒");
                index = 1;
            } else if (timeNow.getTime() > drinkingTime_2.getTime() && timeNow.getTime() < drinkingTime_3.getTime()) {
                drinkingRemind.setText((drinkingTime_3.getTime() - timeNow.getTime()) / (1000 * 60 * 60) + "小时" + ((drinkingTime_3.getTime() - timeNow.getTime()) / 1000) % 3600 / 60 + "分钟后提醒");
                index = 2;
            } else if (timeNow.getTime() > drinkingTime_3.getTime() && timeNow.getTime() < drinkingTime_4.getTime()) {
                drinkingRemind.setText((drinkingTime_4.getTime() - timeNow.getTime()) / (1000 * 60 * 60) + "小时" + ((drinkingTime_4.getTime() - timeNow.getTime()) / 1000) % 3600 / 60 + "分钟后提醒");
                index = 3;
            } else if (timeNow.getTime() > drinkingTime_4.getTime() && timeNow.getTime() < drinkingTime_5.getTime()) {
                drinkingRemind.setText((drinkingTime_5.getTime() - timeNow.getTime()) / (1000 * 60 * 60) + "小时" + ((drinkingTime_5.getTime() - timeNow.getTime()) / 1000) % 3600 / 60 + "分钟后提醒");
                index = 4;
            } else if (timeNow.getTime() > drinkingTime_5.getTime() && timeNow.getTime() < drinkingTime_6.getTime()) {
                drinkingRemind.setText((drinkingTime_6.getTime() - timeNow.getTime()) / (1000 * 60 * 60) + "小时" + ((drinkingTime_6.getTime() - timeNow.getTime()) / 1000) % 3600 / 60 + "分钟后提醒");
                index = 5;
            } else if (timeNow.getTime() > drinkingTime_6.getTime() && timeNow.getTime() < drinkingTime_6.getTime()) {
                drinkingRemind.setText((drinkingTime_7.getTime() - timeNow.getTime()) / (1000 * 60 * 60) + "小时" + ((drinkingTime_7.getTime() - timeNow.getTime()) / 1000) % 3600 / 60 + "分钟后提醒");
                index = 6;
            } else if (timeNow.getTime() > drinkingTime_7.getTime() && timeNow.getTime() < drinkingTime_8.getTime()) {
                drinkingRemind.setText((drinkingTime_8.getTime() - timeNow.getTime()) / (1000 * 60 * 60) + "小时" + ((drinkingTime_8.getTime() - timeNow.getTime()) / 1000) % 3600 / 60 + "分钟后提醒");
                index = 7;
            } else if (timeNow.getTime() > drinkingTime_8.getTime()) {
                drinkingRemind.setText(8 + ((drinkingTime_0.getTime() - timeNow.getTime()) / (1000 * 60 * 60)) + "小时" + ((drinkingTime_0.getTime() - timeNow.getTime()) / 1000) % 3600 / 60 + "分钟后提醒");
                index = 0;
            }
            //Log.i("xl","setDrinkingRemind");
            showNotify(timeNow);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public View.OnClickListener updateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_right:
                    if (day == 7) {
                        week = week + 1;
                        day = 1;
                    } else {
                        day = day + 1;
                    }
                    tv_expectedDate.setText("距离预产期还有 " + (betweenDays - 1) + " 天");
                    betweenDays = betweenDays - 1;
                    updateRemind();
                    break;
                case R.id.tv_left:
                    if (day == 1) {
                        week = week - 1;
                        day = 7;
                    } else {
                        day = day - 1;
                    }
                    tv_expectedDate.setText("距离预产期还有 " + (betweenDays + 1) + " 天");
                    betweenDays = betweenDays + 1;
                    updateRemind();
                    break;
            }
        }
    };

    //更新数据
    public void updateRemind() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DailyRemindBeam dailyRemindBeam = gson.fromJson(response, DailyRemindBeam.class);

                if (dailyRemindBeam.getWeek() < dailyRemindBeam.getCheckWeek() - 1) {
                    checkRemind.setText("距第" + dailyRemindBeam.getCheckTimes() + "次产检：还有" + (dailyRemindBeam.getCheckWeek() - dailyRemindBeam.getWeek()) + "周");
                } else if (dailyRemindBeam.getCheckWeek() == dailyRemindBeam.getWeek() - 1) {
                    checkRemind.setText("距第" + dailyRemindBeam.getCheckTimes() + "次产检：下一周要产检了");
                } else if (dailyRemindBeam.getCheckWeek() == dailyRemindBeam.getWeek()) {
                    checkRemind.setText("距第" + dailyRemindBeam.getCheckTimes() + "次产检：本周产检哦");
                }

                babyPhysical.setText(dailyRemindBeam.getBabyPhysical());
                momPhysical.setText(dailyRemindBeam.getMomPhysical());
                nutritionRecipes.setText(dailyRemindBeam.getNutritionRecipes());
                weekAndDay.setText("" + dailyRemindBeam.getWeek() + "周+" + dailyRemindBeam.getDay() + "天");
                week = dailyRemindBeam.getWeek();
                day = dailyRemindBeam.getDay();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("xl", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("action", "updateRemind");
                map.put("week", "" + week);
                map.put("day", "" + day);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    //item点击
    public View.OnClickListener itemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),ItemActivity.class);
            intent.putExtra("id",""+v.getId());
            intent.putExtra("week",""+week);
            intent.putExtra("day",""+day);
            intent.putExtra("index",""+index);
            startActivity(intent);
        }
    };

    //显示通知
    private void showNotify(Date now){
        if((int) now.getTime() == (int) drinkingTime_1.getTime())
        {
            Notify();
        }else if((int) now.getTime() == (int) drinkingTime_2.getTime()){
            Notify();
        }else if((int) now.getTime() == (int) drinkingTime_3.getTime()){
            Notify();
        }else if((int) now.getTime() == (int) drinkingTime_4.getTime()){
            Notify();
        }else if((int) now.getTime() == (int) drinkingTime_5.getTime()){
            Notify();
        }else if((int) now.getTime() == (int) drinkingTime_6.getTime()){
            Notify();
        }else if((int) now.getTime() == (int) drinkingTime_7.getTime()){
            Notify();
        }else if((int) now.getTime() == (int) drinkingTime_8.getTime()){
            Notify();
        }
    }

    //通知
    public final void Notify(){
        //定义一个PendingIntent点击Notification后启动一个Activity
        Intent it = new Intent(getActivity(), ItemActivity.class);
        it.putExtra("id","" + R.id.drinkingRemind_item);
        it.putExtra("week",""+week);
        it.putExtra("day",""+day);
        it.putExtra("index",""+index);
        PendingIntent pit = PendingIntent.getActivity(getActivity(), 0, it, 0);
        LargeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.cup);
        Notification.Builder mBuilder = new Notification.Builder(getActivity());
        mBuilder.setContentTitle("喝水时间到了")                        //标题
                .setContentText("孕期要及时补充水分哦~")      //内容
                .setTicker("喝水时间到！")             //收到信息后状态栏显示的文字信息
                .setWhen(System.currentTimeMillis())           //设置通知时间
                .setLargeIcon(LargeBitmap)                     //设置大图标
                .setDefaults(Notification.DEFAULT_VIBRATE)    //设置默认振动器
                .setAutoCancel(true)                           //设置点击后取消Notification
                .setContentIntent(pit);                        //设置PendingIntent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notify = mBuilder.build();
        }
        mNManager.notify(NOTIFYID_1, notify);
    }
}
