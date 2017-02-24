package org.cchao.imagepreviewlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shucc on 17/2/23.
 * cc@cchao.org
 */
public class ImagePreViewActivity extends AppCompatActivity {

    private static final String STATE_POSITION = "STATE_POSITION";
    private static final String EXTRA_IMAGE_INDEX = "image_index";
    private static final String EXTRA_IMAGE_URLS = "image_urls";

    private HackyViewPager viewPager;
    private TextView textIndicator;

    //第几张图片
    private int pagerPosition;
    //图片地址
    private ArrayList<String> urls = new ArrayList<>();

    public static void launch(Activity context, int pagerPosition, ArrayList<String> urls) {
        Intent intent = new Intent(context, ImagePreViewActivity.class);
        intent.putExtra(EXTRA_IMAGE_INDEX, pagerPosition);
        intent.putStringArrayListExtra(EXTRA_IMAGE_URLS, urls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.image_preview_enter, R.anim.image_preview_exit);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, viewPager.getCurrentItem());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //状态栏透明
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        viewPager = (HackyViewPager) findViewById(R.id.viewPager);
        textIndicator = (TextView) findViewById(R.id.text_indicator);

        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        ImageDetailAdapter imageDetailAdapter = new ImageDetailAdapter(getSupportFragmentManager(), urls);
        viewPager.setAdapter(imageDetailAdapter);
        CharSequence text = getString(R.string.viewpager_indicator, 1, viewPager.getAdapter().getCount());
        textIndicator.setText(text);
        // 更新下标
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {}

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, viewPager.getAdapter().getCount());
                textIndicator.setText(text);
            }
        });

        viewPager.setCurrentItem(pagerPosition);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.image_preview_enter, R.anim.image_preview_exit);
    }
}
