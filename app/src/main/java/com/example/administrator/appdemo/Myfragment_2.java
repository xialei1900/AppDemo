package com.example.administrator.appdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListPopupWindow;
import android.widget.Toast;

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
import bean.GridBean;

/**
 * Created by Administrator on 2017/3/16.
 */

public class Myfragment_2 extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {
    public Myfragment_2(){}

    private Context mContext;
    private MyGridView grid_food;
    private BaseAdapter mAdapter = null;
    private ArrayList<GridBean> mData_food = null;

    private EditText fragment_search_key;
    private List<FoodBean> mdata;
    private MyAdapter<FoodBean> myAdapter;
    private ListPopupWindow listPopupWindow;
    private String foodName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content_2,container,false);
        mContext = view.getContext();
        grid_food = (MyGridView) view.findViewById(R.id.grid_food);
        fragment_search_key = (EditText) view.findViewById(R.id.fragment_search_key);

        fragment_search_key.addTextChangedListener(watcher);

        mData_food = new ArrayList<GridBean>();
        mData_food.add(new GridBean(R.mipmap.food_zhushi,"主食"));//1
        mData_food.add(new GridBean(R.mipmap.food_jiagongshipin,"加工食品"));//2
        mData_food.add(new GridBean(R.mipmap.food_shucai,"蔬菜菌类"));//3
        mData_food.add(new GridBean(R.mipmap.food_roudanlei,"肉蛋类"));//4
        mData_food.add(new GridBean(R.mipmap.food_shuiguo,"水果"));//5
        mData_food.add(new GridBean(R.mipmap.food_haichan,"海产品"));//6
        mData_food.add(new GridBean(R.mipmap.food_tiaoweipin,"调味品"));//7
        mData_food.add(new GridBean(R.mipmap.food_yinpin,"饮品"));//8
        mData_food.add(new GridBean(R.mipmap.food_lingshi,"零食小吃"));//9
        mData_food.add(new GridBean(R.mipmap.food_niunai,"豆奶制品"));//10
        mData_food.add(new GridBean(R.mipmap.food_jianguo,"坚果"));//11
        mData_food.add(new GridBean(R.mipmap.food_caoyao,"补药草药"));//12

        mAdapter = new MyAdapter<GridBean>(mData_food, R.layout.grid_content) {
            @Override
            public void bindView(ViewHolder holder, GridBean obj) {
                holder.setImageResource(R.id.img_icon, obj.getiId());
                holder.setText(R.id.txt_icon, obj.getiName());
            }
        };
        grid_food.setAdapter(mAdapter);
        grid_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"position:"+position+" id:"+id,Toast.LENGTH_SHORT).show();
                //ArrayList<GridBean> item = (ArrayList<GridBean>) parent.getItemAtPosition(position);
                //HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);
                GridBean item = (GridBean) parent.getItemAtPosition(position);
                Intent it = new Intent(getActivity(),FoodActivity.class);
                it.putExtra("title",item.getiName());
                it.putExtra("sort",""+(position+1));
                startActivity(it);
            }
        });

        return view;
    }

    //搜索监听
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            showListPopulWindow(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //搜索框下拉List

    private void showListPopulWindow(final String keyWord){
        listPopupWindow = new ListPopupWindow(getActivity());
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("E"))
                {
                    return;
                }
                Gson gson = new Gson();
                mdata = gson.fromJson("["+response+"]",new TypeToken<ArrayList<FoodBean>>() {}.getType());
                myAdapter = new MyAdapter<FoodBean>((ArrayList<FoodBean>) mdata, R.layout.list_content) {
                    @Override
                    public void bindView(ViewHolder holder, FoodBean obj) {
                        foodName = obj.getName().substring(4,obj.getName().length()-2);
                        holder.setText(R.id.food_name, foodName);
                        holder.setText(R.id.food_alias, obj.getAlias());
                        holder.loadImageResource(R.id.food_pic,obj.getSmall_pic(),getActivity());
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
                listPopupWindow.setAdapter(myAdapter);
                listPopupWindow.setAnchorView(fragment_search_key);
                listPopupWindow.setModal(true);
                listPopupWindow.show();
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
                map.put("action", "searchFoodByName");
                map.put("keyWord", keyWord);
                return map;
            }
        };
        requestQueue.add(stringRequest);
        listPopupWindow.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FoodBean itemFoodBean = (FoodBean) parent.getItemAtPosition(position);
        Intent it = new Intent(getActivity(),FoodInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("itemFoodBean", itemFoodBean);
        it.putExtras(bundle);
        startActivity(it);
    }
}
