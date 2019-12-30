package org.cchao.test;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @author cchen6
 * @Date on 2019/7/30
 * @Description
 */
public class DepthTransformer implements ViewPager.PageTransformer {

    private int maxSize;

    public DepthTransformer(int maxSize) {
        this.maxSize = maxSize;
    }

    @SuppressLint("NewApi")
    @Override
    public void transformPage(View view, float position) {
        System.out.println("当前TAG：" + view.getTag().toString());
        if (position < -1) { // [-Infinity,-1)//This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 0) { // [-1,0]Use //the default slide transition when moving to the left page
            if (view.getTag().toString().equals(1)) {
                view.setAlpha(0.26f);
            }else {
                view.setAlpha(position + 1);
            }
        } else if (position <= 1) { // (0,1]// Fade the page out.
            if (view.getTag().toString().equals(maxSize - 1)) {
                view.setAlpha(1 - position + 0.26f);
            } else {
                view.setAlpha(1 - position);
            }
        } else { // (1,+Infinity]
            view.setAlpha(0);
        }
    }
}

