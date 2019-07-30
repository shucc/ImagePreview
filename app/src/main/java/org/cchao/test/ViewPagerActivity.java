package org.cchao.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.cchao.imagepreviewlib.ImagePreviewBuilder;
import org.cchao.imagepreviewlib.ImagePreviewExitListener;
import org.cchao.imagepreviewlib.ImagePreviewSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cchen6
 * @Date on 2019/7/30
 * @Description
 */
public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager vpData;

    public static void launch(Context context) {
        Intent starter = new Intent(context, ViewPagerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        vpData = findViewById(R.id.vp_data);

        final List<String> data = new ArrayList<>();
        data.add("http://imgsrc.baidu.com/forum/w=580/sign=a3d6766038292df597c3ac1d8c305ce2/20e941c2d5628535d2e5616e92ef76c6a6ef63b5.jpg");
        data.add("http://imgsrc.baidu.com/forum/w%3D580/sign=ba6c1291f21f3a295ac8d5c6a924bce3/028195504fc2d562b30d63a2e51190ef77c66cb5.jpg");
        data.add("http://n.sinaimg.cn/97973/transform/20160115/5AgV-fxnqrkc6483530.jpg");
        final List<ViewPagerFragment> fragmentList = new ArrayList<>();
        vpData.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position < fragmentList.size() && null != fragmentList.get(position)) {
                    return fragmentList.get(position);
                }
                final ViewPagerFragment viewPagerFragment = ViewPagerFragment.newInstance(data.get(position), position);
                viewPagerFragment.setOnExitListener(new ViewPagerFragment.OnExitListener() {
                    @Override
                    public void onExit(View view, int position) {
                        ImagePreviewBuilder.from((AppCompatActivity) viewPagerFragment.getActivity())
                                .setInitPosition(position)
                                .setImageUrlArray(data)
                                .setPairView(view)
                                .setImagePreviewExitListener(new ImagePreviewExitListener() {
                                    @Override
                                    public View exitView(int exitPosition) {
                                        return vpData.findViewWithTag(exitPosition);
                                    }
                                })
                                .setImagePreviewSelectListener(new ImagePreviewSelectListener() {
                                    @Override
                                    public void onPageSelect(int position) {
                                        vpData.setCurrentItem(position, false);
                                    }
                                })
                                .startActivity();
                    }
                });
                fragmentList.add(viewPagerFragment);
                return viewPagerFragment;
            }

            @Override
            public int getCount() {
                return data.size();
            }
        });
        vpData.setOffscreenPageLimit(5);
    }
}
