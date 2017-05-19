package com.example.administrator.appdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {

    private TextView week;
    private TextView day;
    private Button ok;
    private Button ok2;
    private Button writtenOff;
    public int w = 18;
    public int d = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        week = (TextView) findViewById(R.id.week);
        week.setText("" + w);
        day = (TextView) findViewById(R.id.day);
        //day.setText(""+d);
        ok = (Button) findViewById(R.id.ok);
        ok2 = (Button) findViewById(R.id.ok2);
        writtenOff = (Button) findViewById(R.id.btn_writtenOff);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue = Volley.newRequestQueue(UserInfoActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("xl", response);
//                        if(response.equals("S")) {
//                            Toast.makeText(UserInfoActivity.this, response, Toast.LENGTH_LONG).show();
//                            Log.i("xl", "第" + w + "周，第" + d + "天，成功");
//                            week.setText(""+w);
//                            day.setText(""+d);
//
//                            if(d<7){
//                                d++;
//                            }else{
//                                d=1;
//                                w++;
//                            }
//
//                        }else {
//                            Toast.makeText(UserInfoActivity.this, response, Toast.LENGTH_LONG).show();
//                            Log.i("xl", "第" + w + "周，第" + d + "天，失败");
//                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserInfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("xl", error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("action", "getData");
                        map.put("week", "" + w);
                        map.put("day", "" + d);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue = Volley.newRequestQueue(UserInfoActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.18.50.89:9090/", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("S")) {
                            Toast.makeText(UserInfoActivity.this, response, Toast.LENGTH_LONG).show();
                            Log.i("xl", "第" + w + "周，成功");
                            week.setText("" + w);
                            w++;
                        } else {
                            Toast.makeText(UserInfoActivity.this, response, Toast.LENGTH_LONG).show();
                            Log.i("xl", "第" + w + "周,失败");
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserInfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("xl", error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("action", "getDataByself");
                        map.put("week", "" + w);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        //注销
        writtenOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.clear(MainActivity.mContext);
                startActivity(new Intent(UserInfoActivity.this, MainActivity.class));
            }
        });
    }
}
