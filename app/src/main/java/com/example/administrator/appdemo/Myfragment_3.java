package com.example.administrator.appdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
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

import bean.FoodBean;
import bean.questionBean;

/**
 * Created by Administrator on 2017/3/16.
 */

public class Myfragment_3 extends android.support.v4.app.Fragment {
    public Myfragment_3(){}

    private ListView question_list;
    private List<questionBean> mdata;
    private MyAdapter<questionBean> myAdapter;

    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content_3,container,false);

        question_list = (ListView) view.findViewById(R.id.question_list);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        getQuestionList();
        return view;
    }

    private void getQuestionList(){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mdata = gson.fromJson("["+response+"]",new TypeToken<ArrayList<questionBean>>() {}.getType());
                myAdapter = new MyAdapter<questionBean>((ArrayList<questionBean>) mdata, R.layout.question_content) {
                    @Override
                    public void bindView(ViewHolder holder, questionBean obj) {

                        holder.setText(R.id.question_list_topic, obj.getTopic());
                        holder.setText(R.id.question_list_title, obj.getTitle());
                        holder.setText(R.id.question_list_info, obj.getInfo());
                        holder.setText(R.id.question_list_agree, ""+obj.getAgree()+" {fa-thumbs-o-up} · ");
                        holder.setText(R.id.question_list_disagree, ""+obj.getDisagree()+" {fa-thumbs-o-down} · ");
                        holder.setText(R.id.question_list_comment, ""+obj.getComment()+" {fa-comments-o}");
                    }
                };
                question_list.setAdapter(myAdapter);
                question_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
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
                map.put("action", "getQuestionList");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void showPopup(View v){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_content, null, false);
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, 500, true);
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popWindow.showAtLocation(v, Gravity.CENTER,0,0);
        darkenBackground(0.2f);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
    }

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgcolor){
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgcolor;

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
    }
}
