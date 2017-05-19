package com.example.administrator.appdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/3/16.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT=3;
    private Myfragment_1 myfragment_1 = null;
    private Myfragment_2 myfragment_2 = null;
    private Myfragment_3 myfragment_3 = null;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        myfragment_1 = new Myfragment_1();
        myfragment_2 = new Myfragment_2();
        myfragment_3 = new Myfragment_3();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = myfragment_1;
                break;
            case MainActivity.PAGE_TWO:
                fragment = myfragment_2;
                break;
            case MainActivity.PAGE_THREE:
                fragment = myfragment_3;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
