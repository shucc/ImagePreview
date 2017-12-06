package org.cchao.imagepreviewlib;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shucc on 17/2/23.
 * cc@cchao.org
 */
public class ImagePreviewActivity extends AppCompatActivity {

    protected static final String EXTRA_IMAGE_SAVE_STATE = "image_init_position";
    protected static final String EXTRA_IMAGE_INDEX = "image_index";
    protected static final String EXTRA_IMAGE_TAG = "image_tag";
    protected static final String EXTRA_IMAGE_URLS = "image_urls";

    private HackyViewPager viewPager;

    private TextView textIndicator;

    private ProgressBar pbLoading;

    private String tag;

    //第几张图片
    private int initPosition;
    //图片地址
    private ArrayList<String> urls = new ArrayList<>();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_IMAGE_SAVE_STATE, viewPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityCompat.postponeEnterTransition(this);
        }
        setContentView(R.layout.activity_image_preview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //状态栏透明
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        viewPager = findViewById(R.id.viewPager);
        textIndicator = findViewById(R.id.text_indicator);
        pbLoading = findViewById(R.id.pb_loading);

        final Intent intent = getIntent();
        initPosition = intent.getIntExtra(EXTRA_IMAGE_INDEX, 0);
        tag = intent.getStringExtra(EXTRA_IMAGE_TAG);
        urls = intent.getStringArrayListExtra(EXTRA_IMAGE_URLS);
        if (savedInstanceState != null) {
            initPosition = savedInstanceState.getInt(EXTRA_IMAGE_SAVE_STATE);
        }
        CharSequence text = getString(R.string.image_preview_viewpager_indicator, initPosition + 1, urls.size());
        textIndicator.setText(text);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ImageDetailFragment.newInstance(urls.get(position), tag, initPosition, position);
            }

            @Override
            public int getCount() {
                return urls.size();
            }
        };
        viewPager.setAdapter(adapter);
        // 更新下标
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.image_preview_viewpager_indicator, arg0 + 1, viewPager.getAdapter().getCount());
                textIndicator.setText(text);
            }
        });
        viewPager.setCurrentItem(initPosition);
    }

    @Override
    public void finishAfterTransition() {
        int pos = viewPager.getCurrentItem();
        ImagePreviewBuilder.setExitPosition(pos);
        if (initPosition != pos) {
            View view = viewPager.findViewWithTag(getString(R.string.image_preview_transition_name, pos));
            setSharedElementCallback(view);
        }
        super.finishAfterTransition();
    }

    @TargetApi(21)
    private void setSharedElementCallback(final View view) {
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                names.clear();
                sharedElements.clear();
                names.add(view.getTransitionName());
                sharedElements.put(view.getTransitionName(), view);
            }
        });
    }

    public void hideLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }
}
