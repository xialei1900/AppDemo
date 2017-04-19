package com.example.administrator.appdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends Activity {
    private EditText registerId = null;
    private EditText registerPwd = null;
    private EditText registerPwd_confirm = null;
    private EditText registerUserName = null;
    private Button register = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerId = (EditText) findViewById(R.id.edt_registerId);
        registerPwd = (EditText) findViewById(R.id.edt_registerPwd);
        registerPwd_confirm = (EditText) findViewById(R.id.edt_registerPwd_confirm);
        registerUserName = (EditText) findViewById(R.id.edt_registerUserName);
        register = (Button) findViewById(R.id.btn_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registerPwd.getText().toString().equals(registerPwd_confirm.getText().toString())){
                    if(registerId.getText().toString().equals("")){
                        Toast.makeText(RegisterActivity.this,"电话不能为空!",Toast.LENGTH_SHORT).show();
                    }else if(registerUserName.getText().toString().equals("")){
                        Toast.makeText(RegisterActivity.this,"用户名不能为空!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        register();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this,"输入的两次密码不相同!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //注册
    public void register(){
        final RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_SHORT).show();
                if(response.equals("S")){
                    Intent intent = new Intent(RegisterActivity.this,SetDateActivity.class);
                    RegisterActivity.this.startActivity(intent);
                }else if(response.equals("E")){
                    Toast.makeText(RegisterActivity.this,"发生了未知错误,注册失败!",Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("action", "Register");
                map.put("registerId", registerId.getText().toString());
                map.put("registerPwd", registerPwd.getText().toString());
                map.put("registerUserName", registerUserName.getText().toString());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
