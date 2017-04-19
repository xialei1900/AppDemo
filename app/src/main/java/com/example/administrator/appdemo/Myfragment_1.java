package com.example.administrator.appdemo;

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


/**
 * Created by Administrator on 2017/3/16.
 */

public class Myfragment_1 extends android.support.v4.app.Fragment{
    public Myfragment_1(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content_1,container,false);
        TextView tv_expectedDate = (TextView) view.findViewById(R.id.tv_expectedDate);
        final TextView babyPhysical = (TextView) view.findViewById(R.id.babyPhysical);
        final TextView momPhysical = (TextView) view.findViewById(R.id.momPhysical);
        //设置预产期
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date expectedDate = format.parse(SPUtils.get(MainActivity.mContext,"expectedDate","").toString());
            int betweenDays = DateUtils.daysBetween(new Date(System.currentTimeMillis()),expectedDate);
            SPUtils.put(MainActivity.mContext,"betweenDays",betweenDays);
            //Log.i("xl","预产期："+betweenDays);
            tv_expectedDate.setText("距离预产期还有 "+betweenDays+" 天");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //获取每日提醒数据更新
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DailyRemindBeam dailyRemindBeam = gson.fromJson(response,DailyRemindBeam.class);

                babyPhysical.setText(dailyRemindBeam.getBabyPhysical());
                momPhysical.setText(dailyRemindBeam.getMomPhysical());
                //Log.i("xl",response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                Log.i("xl",error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("action", "getRemindToday");
                map.put("expectedDate", SPUtils.get(MainActivity.mContext,"expectedDate","").toString());
                return map;
            }
        };
        requestQueue.add(stringRequest);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
