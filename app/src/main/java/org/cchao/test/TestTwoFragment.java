package org.cchao.test;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.cchao.imagepreviewlib.ImageLoader;
import org.cchao.imagepreviewlib.ImageLoaderListener;
import org.cchao.imagepreviewlib.ImagePreViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.co.senab.photoview.PhotoView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by shucc on 17/10/23.
 * cc@cchao.org
 */
public class TestTwoFragment extends Fragment {

    private final String TAG = getClass().getName();

    private RecyclerView rvImage;

    private List<String> data;

    private int enterPosition;
    private int exitPosition;

    public static TestTwoFragment newInstance() {
        Bundle args = new Bundle();
        TestTwoFragment fragment = new TestTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_two, container, false);
        rvImage = (RecyclerView) view.findViewById(R.id.rv_image);

        ImageLoader.init(new ImageLoaderListener() {
            @Override
            public void load(Fragment fragment, PhotoView photoView, String imageUrl) {
                Glide.with(fragment)
                        .load(imageUrl)
                        .into(photoView);
            }
        });

        data = new ArrayList<>();
        data.add("http://img3.duitang.com/uploads/item/201606/04/20160604010014_Art48.thumb.224_0.jpeg");
        data.add("http://imgsrc.baidu.com/forum/w=580/sign=a3d6766038292df597c3ac1d8c305ce2/20e941c2d5628535d2e5616e92ef76c6a6ef63b5.jpg");
        data.add("http://imgsrc.baidu.com/forum/w%3D580/sign=ba6c1291f21f3a295ac8d5c6a924bce3/028195504fc2d562b30d63a2e51190ef77c66cb5.jpg");
        data.add("http://pic1.ipadown.com/imgs/20110326172546834.jpg");
        data.add("http://n.sinaimg.cn/97973/transform/20160115/5AgV-fxnqrkc6483530.jpg");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvImage.setLayoutManager(gridLayoutManager);
        ImageAdapter imageAdapter = new ImageAdapter(data);
        rvImage.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Pair<View, String> pair = Pair.create(view, view.getTransitionName());
                    ImagePreViewActivity.launch(getActivity(), position, (ArrayList) data, pair);
                    enterPosition = position;
                    setSharedElementCallback();
                } else {
                    ImagePreViewActivity.launch(getActivity(), position, (ArrayList) data);
                }
            }
        });
        return view;
    }

    @TargetApi(21)
    private void setSharedElementCallback() {
        getActivity().setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Log.d(TAG, "onMapSharedElements: ");
                if (exitPosition != enterPosition) {
                    View view = rvImage.findViewWithTag(exitPosition);
                    names.clear();
                    sharedElements.clear();
                    names.add(view.getTransitionName());
                    sharedElements.put(view.getTransitionName(), view);
                }
                getActivity().setExitSharedElementCallback((SharedElementCallback) null);
            }
        });
    }

    public void onActivityReenter(int resultCode, Intent data) {
        Log.d(TAG, "onActivityReenter: ");
        if (resultCode == RESULT_OK && null != data) {
            exitPosition = data.getIntExtra(ImagePreViewActivity.EXIT_POSITION, 0);
            Log.d(TAG, "onActivityResult: " + exitPosition);
        }
    }
}
