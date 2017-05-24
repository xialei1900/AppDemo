package com.example.administrator.appdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import bean.QuestionBean;

public class QuestionActivity extends AppCompatActivity {

    private QuestionBean questionBean;
    private TextView question_info_topTitle;
    private TextView question_info_topic;
    private TextView question_info_title;
    private TextView question_info_info;
    private TextView question_info_agree;
    private TextView question_info_disagree;
    private TextView question_info_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question_info_topTitle = (TextView) findViewById(R.id.question_info_topTitle);
        question_info_topic = (TextView) findViewById(R.id.question_info_topic);
        question_info_title = (TextView) findViewById(R.id.question_info_title);
        question_info_info = (TextView) findViewById(R.id.question_info_info);
        question_info_agree = (TextView) findViewById(R.id.question_info_agree);
        question_info_disagree = (TextView) findViewById(R.id.question_info_disagree);
        question_info_comment = (TextView) findViewById(R.id.question_info_comment);

        Intent intent = getIntent();
        if(intent != null) {
            questionBean = (QuestionBean) intent.getSerializableExtra("item");
            question_info_topic.setText("主题： "+questionBean.getTopic());
            question_info_title.setText(questionBean.getTitle());
            question_info_info.setText(questionBean.getInfo());
            question_info_agree.setText(questionBean.getAgree()+" {fa-thumbs-o-up}");
            question_info_disagree.setText(questionBean.getDisagree()+" {fa-thumbs-o-down}");
            question_info_comment.setText(questionBean.getComment()+" {fa-comments-o}");

        }
        //返回
        findViewById(R.id.question_info_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
