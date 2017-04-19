package com.example.administrator.appdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/16.
 */

public class Myfragment_2 extends android.support.v4.app.Fragment{
    public Myfragment_2(){}

    private Context mContext;
    private MyGridView grid_food;
    private MyGridView grid_exercise;
    private BaseAdapter mAdapter = null;
    private ArrayList<GridBean> mData_food = null;
    private ArrayList<GridBean> mData_exercise = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content_2,container,false);
        mContext = view.getContext();
        grid_food = (MyGridView) view.findViewById(R.id.grid_food);
        grid_exercise = (MyGridView) view.findViewById(R.id.grid_exercise);

        mData_food = new ArrayList<GridBean>();
        mData_food.add(new GridBean(R.mipmap.food_zhushi,"主食"));
        mData_food.add(new GridBean(R.mipmap.food_jianguo,"坚果"));
        mData_food.add(new GridBean(R.mipmap.food_niunai,"豆奶制品"));
        mData_food.add(new GridBean(R.mipmap.food_lingshi,"零食小吃"));
        mData_food.add(new GridBean(R.mipmap.food_yinpin,"饮品"));
        mData_food.add(new GridBean(R.mipmap.food_tiaoweipin,"调味品"));
        mData_food.add(new GridBean(R.mipmap.food_haichan,"海产品"));
        mData_food.add(new GridBean(R.mipmap.food_shuiguo,"水果"));
        mData_food.add(new GridBean(R.mipmap.food_roudanlei,"肉蛋类"));
        mData_food.add(new GridBean(R.mipmap.food_shucai,"蔬菜菌类"));
        mData_food.add(new GridBean(R.mipmap.food_jiagongshipin,"加工食品"));
        mData_food.add(new GridBean(R.mipmap.food_caoyao,"补药草药"));
        mAdapter = new MyAdapter<GridBean>(mData_food, R.layout.grid_content) {
            @Override
            public void bindView(ViewHolder holder, GridBean obj) {
                holder.setImageResource(R.id.img_icon, obj.getiId());
                holder.setText(R.id.txt_icon, obj.getiName());
            }
        };
        grid_food.setAdapter(mAdapter);

        mData_exercise = new ArrayList<GridBean>();
        mData_exercise.add(new GridBean(R.mipmap.behavior_chuxing,"出行安全"));
        mData_exercise.add(new GridBean(R.mipmap.behavior_yundong,"行为运动"));
        mData_exercise.add(new GridBean(R.mipmap.behavior_huli,"身体护理"));
        mData_exercise.add(new GridBean(R.mipmap.behavior_xiuxian,"休闲娱乐"));
        mData_exercise.add(new GridBean(R.mipmap.behavior_chuandai,"穿戴讲究"));
        mData_exercise.add(new GridBean(R.mipmap.behavior_jujia,"居家生活"));
        mAdapter = new MyAdapter<GridBean>(mData_exercise, R.layout.grid_content) {
            @Override
            public void bindView(ViewHolder holder, GridBean obj) {
                holder.setImageResource(R.id.img_icon, obj.getiId());
                holder.setText(R.id.txt_icon, obj.getiName());
            }
        };
        grid_exercise.setAdapter(mAdapter);
        return view;
    }
}
