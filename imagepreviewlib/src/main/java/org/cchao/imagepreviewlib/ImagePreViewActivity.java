package org.cchao.imagepreviewlib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shucc on 17/2/23.
 * cc@cchao.org
 */
public class ImagePreViewActivity extends AppCompatActivity {

    private static final String STATE_POSITION = "STATE_POSITION";
    private static final String EXTRA_IMAGE_INDEX = "image_index";
    private static final String EXTRA_IMAGE_URLS = "image_urls";
    public static final String EXIT_POSITION = "exit_position";

    private HackyViewPager viewPager;
    private TextView textIndicator;

    //第几张图片
    private int initPosition;
    //图片地址
    private ArrayList<String> urls = new ArrayList<>();

    public static void launch(Activity context, int initPosition, ArrayList<String> urls) {
        Intent intent = new Intent(context, ImagePreViewActivity.class);
        intent.putExtra(EXTRA_IMAGE_INDEX, initPosition);
        intent.putStringArrayListExtra(EXTRA_IMAGE_URLS, urls);
        context.startActivity(intent);
    }

    public static void launch(Activity context, int initPosition, ArrayList<String> urls, Pair<View, String> pair) {
        Intent intent = new Intent(context, ImagePreViewActivity.class);
        intent.putExtra(EXTRA_IMAGE_INDEX, initPosition);
        intent.putStringArrayListExtra(EXTRA_IMAGE_URLS, urls);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(context, pair);
            context.startActivityForResult(intent, 0, optionsCompat.toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, viewPager.getCurrentItem());
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

        viewPager = (HackyViewPager) findViewById(R.id.viewPager);
        textIndicator = (TextView) findViewById(R.id.text_indicator);

        final Intent intent = getIntent();
        initPosition = intent.getIntExtra(EXTRA_IMAGE_INDEX, 0);
        urls = intent.getStringArrayListExtra(EXTRA_IMAGE_URLS);
        if (savedInstanceState != null) {
            initPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        CharSequence text = getString(R.string.image_preview_viewpager_indicator, initPosition + 1, urls.size());
        textIndicator.setText(text);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ImageDetailFragment.newInstance(urls.get(position), initPosition, position);
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
        Intent intent = new Intent();
        intent.putExtra(EXIT_POSITION, pos);
        setResult(RESULT_OK, intent);
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

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }
}
