package com.example.administrator.appdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */

public class LoginActivity extends Activity{
    Context mContext;

    EditText editText_loginId = null;
    EditText editText_loginPwd = null;
    Button btn_login = null;
    TextView textView_register = null;

    String loginId = null;
    String loginPwd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mContext = getApplicationContext();

        btn_login = (Button) findViewById(R.id.btn_login);
        editText_loginId = (EditText) findViewById(R.id.edt_loginId);
        editText_loginPwd = (EditText) findViewById(R.id.edt_loginPwd);
        textView_register = (TextView) findViewById(R.id.register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginId = editText_loginId.getText().toString();
                loginPwd = editText_loginPwd.getText().toString();

                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //解析用户json
                        Gson gson = new Gson();
                        UserBean userBean = gson.fromJson(response,UserBean.class);
                        //存储用户信息
                        SPUtils.put(mContext,"userName",userBean.getUserName());
                        SPUtils.put(mContext,"sex",userBean.getSex());
                        SPUtils.put(mContext,"age",userBean.getAge());
                        SPUtils.put(mContext,"height",userBean.getHeight());
                        SPUtils.put(mContext,"birthday",userBean.getBirthday());
                        SPUtils.put(mContext,"expectedDate",userBean.getExpectedDate());
                        //跳转到主页面
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("xl",error.getMessage());
                        Toast.makeText(LoginActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("action", "Login");
                        map.put("loginId", loginId);
                        map.put("loginPwd", loginPwd);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
