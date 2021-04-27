package org.cchao.test.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.cchao.test.R;

/**
 * @author cchen6
 * @Date on 2019/7/30
 * @Description
 */
public class ViewPagerFragment extends Fragment {

    private ImageView imgDetail;

    private OnExitListener onExitListener;

    public static ViewPagerFragment newInstance(String url, int position) {
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putInt("position", position);
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_viewpager, container, false);
        imgDetail = view.findViewById(R.id.img_detail);
        final int position = getArguments().getInt("position");
        String url = getArguments().getString("url");
        Glide.with(this)
                .load(url)
                .into(imgDetail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imgDetail.setTransitionName(getString(R.string.image_preview_transition_name, position));
            imgDetail.setTag(position);
        }
        imgDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onExitListener) {
                    onExitListener.onExit(view, position);
                }
            }
        });
        return view;
    }

    public void setOnExitListener(OnExitListener onExitListener) {
        this.onExitListener = onExitListener;
    }

    public interface OnExitListener {
        void onExit(View view, int position);
    }
}
