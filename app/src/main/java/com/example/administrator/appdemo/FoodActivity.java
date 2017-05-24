package com.example.administrator.appdemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.DailyRemindBeam;
import bean.FoodBean;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class FoodActivity extends AppCompatActivity {
    private TextView food_title;
    private ListView food_list;
    private String sort;

    private List<FoodBean> mdata;
    private MyAdapter<FoodBean> myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        food_title = (TextView) findViewById(R.id.food_title);
        food_list = (ListView) findViewById(R.id.food_list);

        Intent intent = getIntent();
        if(intent != null) {
            sort = intent.getStringExtra("sort");
            food_title.setText(intent.getStringExtra("title"));
            Log.i("xl",""+sort);
        }
        getFoodList();
        //返回
        findViewById(R.id.food_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String foodName;
    public void getFoodList(){
        final RequestQueue requestQueue = Volley.newRequestQueue(FoodActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mdata = gson.fromJson("["+response+"]",new TypeToken<ArrayList<FoodBean>>() {}.getType());
                myAdapter = new MyAdapter<FoodBean>((ArrayList<FoodBean>) mdata, R.layout.list_content) {
                    @Override
                    public void bindView(ViewHolder holder, FoodBean obj) {
                        foodName = obj.getName().substring(4,obj.getName().length()-2);
                        holder.setText(R.id.food_name, foodName);
                        holder.setText(R.id.food_alias, obj.getAlias());
                        holder.loadImageResource(R.id.food_pic,obj.getSmall_pic(),FoodActivity.this);
                        switch (obj.getYunma()){
                            case 0:
                                holder.setText(R.id.food_yunma, "{fa-check}能吃");
                                holder.setTextColor(R.id.food_yunma, Color.rgb(51, 204, 102));
                                break;
                            case 1:
                                holder.setText(R.id.food_yunma, "{fa-warning}少吃");
                                holder.setTextColor(R.id.food_yunma,Color.rgb(238, 180, 34));
                                break;
                            case 2:
                                holder.setText(R.id.food_yunma, "{fa-remove}慎吃");
                                holder.setTextColor(R.id.food_yunma,Color.rgb(255, 48, 48));
                                break;
                        }
                    }
                };
                food_list.setAdapter(myAdapter);
                food_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FoodBean itemFoodBean = (FoodBean) parent.getItemAtPosition(position);
                        Intent it = new Intent(FoodActivity.this,FoodInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("itemFoodBean", itemFoodBean);
                        it.putExtras(bundle);
                        startActivity(it);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FoodActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("xl", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("action", "getFoodList");
                map.put("sort", sort);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
