package com.example.administrator.appdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Administrator on 2017/3/16.
 */

public class Myfragment_4 extends android.support.v4.app.Fragment {
    public Myfragment_4 (){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content_4,container,false);

        return view;
    }
}
