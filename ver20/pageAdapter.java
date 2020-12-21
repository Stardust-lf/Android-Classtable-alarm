package com.example.ver20;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class pageAdapter extends PagerAdapter {
    private List<View> mViews;

    public pageAdapter(List<View> mViews){
        this.mViews = mViews;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView(mViews.get(position));
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        View view = mViews.get(position);
        container.addView(view);
        return view;
    }
    @Override
    public boolean isViewFromObject(View arg0,Object arg1){
        return arg0 == arg1;
    }
    @Override
    public int getCount(){
        return mViews.size();
    }
}
