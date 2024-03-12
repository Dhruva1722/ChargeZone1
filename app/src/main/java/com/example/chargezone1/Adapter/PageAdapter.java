package com.example.chargezone1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.example.chargezone1.R;

public class PageAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PageAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 3; // Number of pages
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(getLayout(position), container, false);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private int getLayout(int position) {
        switch (position) {
            case 0:
                return R.layout.page1;
            case 1:
                return R.layout.page2;
            case 2:
                return R.layout.page3;
            default:
                return R.layout.page1;
        }
    }


}
