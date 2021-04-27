package org.cchao.test.adapter;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import org.cchao.imagepreviewlib.ImagePreviewBuilder;
import org.cchao.imagepreviewlib.ImagePreviewExitListener;
import org.cchao.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cchen6
 * @Date on 2019/7/30
 * @Description
 */
public class VPImageAdapter extends PagerAdapter {

    private List<String> data = new ArrayList();

    private Context mContext;

    private SelectedCallBack mSeclectedCallBack;

    private float pageWidth = 0.74f;

    private ViewPager vpData;

    public VPImageAdapter(Context context, ViewPager vpData) {
        this.mContext = context;
        this.vpData = vpData;
    }

    public void setDatas(List<String> list) {
        data.clear();
        data = list;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = View.inflate(mContext, R.layout.item_banner, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setTransitionName(mContext.getString(R.string.image_preview_transition_name, position));
            view.setTag(position);
        }
        ImageView banner = view.findViewById(R.id.iv_banner_item);
        Glide.with(mContext).load(data.get(position)).into(banner);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePreviewBuilder.from((AppCompatActivity) mContext)
                        .setInitPosition(position)
                        .setImageUrlArray(data)
                        .setPairView(view)
                        .setImagePreviewExitListener(new ImagePreviewExitListener() {
                            @Override
                            public View exitView(int exitPosition) {
                                return vpData.findViewWithTag(exitPosition);
                            }
                        })
                        .startActivity();
                if (null != mSeclectedCallBack) {
                    mSeclectedCallBack.onSelected(position);
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return pageWidth;
    }

    public void setPageWidth(float value) {
        this.pageWidth = value;
    }

    public void setOnSelectedCallBack(SelectedCallBack callBack) {
        mSeclectedCallBack = callBack;
    }

    public interface SelectedCallBack {
        void onSelected(int postion);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
