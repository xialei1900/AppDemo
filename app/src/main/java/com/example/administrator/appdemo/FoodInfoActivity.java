package com.example.administrator.appdemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bean.FoodBean;

public class FoodInfoActivity extends AppCompatActivity {

    private TextView food_info_title;
    private ImageView food_info_pic;
    private TextView food_info_name;
    private TextView food_info_yunma;
    private TextView food_info_yunma_desc;
    private FoodBean foodBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        food_info_title = (TextView) findViewById(R.id.food_info_title);
        food_info_pic = (ImageView) findViewById(R.id.food_info_pic);
        food_info_name = (TextView) findViewById(R.id.food_info_name);
        food_info_yunma = (TextView) findViewById(R.id.food_info_yunma);
        food_info_yunma_desc = (TextView) findViewById(R.id.food_info_yunma_desc);

        Intent intent = getIntent();
        if(intent != null) {
            foodBean = (FoodBean) intent.getSerializableExtra("itemFoodBean");
            food_info_title.setText(foodBean.getName().substring(4,foodBean.getName().length()-2));
            //图片加载
            Picasso.with(this).load(foodBean.getBig_pic()).into(food_info_pic);
            food_info_name.setText(foodBean.getName());
            food_info_yunma_desc.setText(foodBean.getYunma_desc());
            switch (foodBean.getYunma()){
                case 0:
                    food_info_yunma.setText("{fa-check}能吃");
                    food_info_yunma.setTextColor(Color.rgb(51, 204, 102));
                    break;
                case 1:
                    food_info_yunma.setText("{fa-warning}少吃");
                    food_info_yunma.setTextColor(Color.rgb(238, 180, 34));
                    break;
                case 2:
                    food_info_yunma.setText( "{fa-remove}慎吃");
                    food_info_yunma.setTextColor(Color.rgb(255, 48, 48));
                    break;
            }
        }
        //返回
        findViewById(R.id.food_info_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
