package com.example.administrator.appdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.DailyRemindBeam;
import bean.FoodBean;
import bean.PregnancyCheckBean;

public class ItemActivity extends AppCompatActivity {

    private TextView weekAndDay;
    private TextView incrase;
    private TextView reduce;
    private View wad;

    private TextView item_title;
    private ListView item_list;
    private ViewFlipper vflp_help;
    private int week;
    private int index;

    private List<DailyRemindBeam> mdata;
    private MyAdapter<DailyRemindBeam> myAdapter;
    private List<PregnancyCheckBean> pdata;
    private MyAdapter<PregnancyCheckBean> pAdapter;
    private Intent intent;


    private Context mContext;
    private final static int MIN_MOVE = 100;   //最小距离
    private MyGestureListener mgListener;
    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        mContext=ItemActivity.this;

        item_title = (TextView) findViewById(R.id.item_title);
        item_list = (ListView) findViewById(R.id.item_list);
        weekAndDay = (TextView) findViewById(R.id.item_weekDay);
        incrase = (TextView) findViewById(R.id.item_right);
        reduce = (TextView) findViewById(R.id.item_left);
        vflp_help = (ViewFlipper) findViewById(R.id.vflp_help);
        wad = findViewById(R.id.wad);

        vflp_help.setVisibility(View.GONE);
        incrase.setOnClickListener(updateListener);
        reduce.setOnClickListener(updateListener);

        intent = getIntent();
        week = Integer.parseInt(intent.getStringExtra("week"));

        switch (intent.getStringExtra("id")) {
            case "" + R.id.babyPhysical:
                item_title.setText("宝宝发育变化");
                getItemList();
                break;
            case "" + R.id.momPhysical:
                item_title.setText("妈咪生理变化");
                getItemList();
                break;
            case "" + R.id.nutritionRecipes_item:
                item_title.setText("营养食谱");
                getItemList();
                break;
            case "" + R.id.checkRemind_item:
                item_title.setText("产检提醒");
                getCheckList("incrase");
                break;
            case "" + R.id.drinkingRemind_item:
                item_title.setText("每日八杯水");
                getDrinkItem();
                break;
        }
    }

    public void getItemList() {
        weekAndDay.setText("孕" + week + "周");
        final RequestQueue requestQueue = Volley.newRequestQueue(ItemActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mdata = gson.fromJson("[" + response + "]", new TypeToken<ArrayList<DailyRemindBeam>>() {
                }.getType());

                myAdapter = new MyAdapter<DailyRemindBeam>((ArrayList<DailyRemindBeam>) mdata, R.layout.item_content) {
                    @Override
                    public void bindView(ViewHolder holder, DailyRemindBeam obj) {
                        holder.setText(R.id.item_weekAndDay, "孕" + obj.getWeek() + "周+" + obj.getDay() + "天");
                        switch (intent.getStringExtra("id")) {
                            case "" + R.id.babyPhysical:
                                holder.setText(R.id.item_content, obj.getBabyPhysical());
                                break;
                            case "" + R.id.momPhysical:
                                holder.setText(R.id.item_content, obj.getMomPhysical());
                                break;
                            case "" + R.id.nutritionRecipes_item:
                                holder.setText(R.id.item_content, obj.getNutritionRecipes());
                                break;
                        }
                    }
                };
                item_list.setAdapter(myAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ItemActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("xl", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("action", "getItemList");
                map.put("week", "" + week);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getCheckList(final String type) {
        final RequestQueue requestQueue = Volley.newRequestQueue(ItemActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                pdata = gson.fromJson("[" + response + "]", new TypeToken<ArrayList<PregnancyCheckBean>>() {
                }.getType());
                week = pdata.get(0).getCheckWeek();
                Log.i("xl", "1:" + week);
                weekAndDay.setText("第" + pdata.get(0).getCheckTimes() + "次产检，" + pdata.get(0).getCheckWeek() + "周");
                pAdapter = new MyAdapter<PregnancyCheckBean>((ArrayList<PregnancyCheckBean>) pdata, R.layout.item_check_content) {
                    @Override
                    public void bindView(ViewHolder holder, PregnancyCheckBean obj) {
                        holder.setText(R.id.item_check_guidance, obj.getGuidance());
                        holder.setText(R.id.item_check_routine, obj.getRoutine());
                        holder.setText(R.id.item_check_mustCheck, obj.getMustCheck());
                        holder.setText(R.id.item_check_referenceCheck, obj.getReferenceCheck());
                    }
                };
                item_list.setAdapter(pAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ItemActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("xl", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                switch (type) {
                    case "incrase":
                        map.put("type", "incrase");
                        break;
                    case "reduce":
                        map.put("type", "reduce");
                        break;
                }
                map.put("action", "getCheckList");
                map.put("week", "" + week);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private View.OnClickListener updateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_right:
                    if (week < 40) {
                        week = week + 1;
                    }
                    reloadItem("incrase");
                    break;
                case R.id.item_left:
                    if (week > 1) {
                        week = week - 1;
                    }
                    reloadItem("reduce");
                    break;
            }
        }
    };

    private void reloadItem(String type) {
        if (Integer.parseInt(intent.getStringExtra("id")) == R.id.checkRemind_item) {
            if (week < 39 && week > 12) {
                getCheckList(type);
            } else if (week < 12) {
                week = 12;
            } else if (week > 39) {
                week = 39;
            }
        } else {
            getItemList();
        }
    }

    private void getDrinkItem(){
        wad.setVisibility(View.GONE);
        item_list.setVisibility(View.GONE);
        vflp_help.setVisibility(View.VISIBLE);
        //视图实例化
        mgListener = new MyGestureListener();
        mDetector = new GestureDetector(this, mgListener);
        index = Integer.parseInt(intent.getStringExtra("index"));
        vflp_help.setDisplayedChild(index);

        showBtn(index);
    }

    //重写手势
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
            if (e1.getX() - e2.getX() > MIN_MOVE) {
                vflp_help.setInAnimation(mContext, R.anim.right_in);
                vflp_help.setOutAnimation(mContext, R.anim.right_out);
                vflp_help.showNext();
            } else if (e2.getX() - e1.getX() > MIN_MOVE) {
                vflp_help.setInAnimation(mContext, R.anim.left_in);
                vflp_help.setOutAnimation(mContext, R.anim.left_out);
                vflp_help.showPrevious();
            }
            return true;
        }
    }

    //重写onTouchEvent触发MyGestureListener里的方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    public void drinkDone(View view){
        view.setVisibility(View.GONE);
        index = index + 1;
        if(index == 8)
        {
            index=0;
        }
        Toast.makeText(ItemActivity.this,"妈咪加油~",Toast.LENGTH_SHORT).show();
        vflp_help.setDisplayedChild(index);
        showBtn(index);
    }

    //控件初始化
    public void showBtn(int j)
    {
        switch (j){
            case 0:
                findViewById(R.id.item_drink_btn_1).setVisibility(View.VISIBLE);
                break;
            case 1:
                findViewById(R.id.item_drink_done_1).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_btn_2).setVisibility(View.VISIBLE);
                break;
            case 2:
                findViewById(R.id.item_drink_done_1).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_2).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_btn_3).setVisibility(View.VISIBLE);
                break;
            case 3:
                findViewById(R.id.item_drink_done_1).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_2).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_3).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_btn_4).setVisibility(View.VISIBLE);
                break;
            case 4:
                findViewById(R.id.item_drink_done_1).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_2).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_3).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_4).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_btn_5).setVisibility(View.VISIBLE);
                break;
            case 5:
                findViewById(R.id.item_drink_done_1).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_2).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_3).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_4).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_5).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_btn_6).setVisibility(View.VISIBLE);
                break;
            case 6:
                findViewById(R.id.item_drink_done_1).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_2).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_3).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_4).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_5).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_6).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_btn_7).setVisibility(View.VISIBLE);
                break;
            case 7:
                findViewById(R.id.item_drink_done_1).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_2).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_3).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_4).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_5).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_6).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_done_7).setVisibility(View.VISIBLE);
                findViewById(R.id.item_drink_btn_8).setVisibility(View.VISIBLE);
                break;
        }
    }
}
