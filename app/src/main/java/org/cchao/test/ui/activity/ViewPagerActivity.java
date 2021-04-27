package org.cchao.test.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.cchao.imagepreviewlib.ImagePreviewBuilder;
import org.cchao.imagepreviewlib.ImagePreviewExitListener;
import org.cchao.imagepreviewlib.ImagePreviewSelectListener;
import org.cchao.test.Constants;
import org.cchao.test.R;
import org.cchao.test.ui.fragment.ViewPagerFragment;

import java.util.ArrayList;
import java.util.Arrays;
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

        final List<String> data = Constants.TEMP_IMAGES;
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
