package org.cchao.imagepreviewlib;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shucc on 17/2/23.
 * cc@cchao.org
 */
public class ImageDetailFragment extends Fragment {

    private final String TAG = getClass().getName();

    private static final String KEY_URL = "key_url";
    private static final String KEY_INIT_POSITION = "key_init_position";
    private static final String KEY_NOW_POSITION = "key_now_position";
    private static final String KEY_IMAGE_TAG = "key_tag";

    private PhotoView photoView;
    private ProgressBar progressBar;

    private String imageUrl;

    private int initPosition;

    private int nowPosition;

    private ImagePreviewActivity imagePreViewActivity;

    public static ImageDetailFragment newInstance(String imageUrl, String tag, int initPosition, int nowPosition) {
        Bundle args = new Bundle();
        args.putString(KEY_URL, imageUrl);
        args.putString(KEY_IMAGE_TAG, tag);
        args.putInt(KEY_INIT_POSITION, initPosition);
        args.putInt(KEY_NOW_POSITION, nowPosition);
        ImageDetailFragment fragment = new ImageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        imageUrl = bundle.getString(KEY_URL);
        String tag = bundle.getString(KEY_IMAGE_TAG);
        initPosition = bundle.getInt(KEY_INIT_POSITION);
        nowPosition = bundle.getInt(KEY_NOW_POSITION);
        View rootView = inflater.inflate(R.layout.fragment_image_preview_detail, null);
        photoView = rootView.findViewById(R.id.img_detail);
        progressBar = rootView.findViewById(R.id.pb_loading);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String name = getString(R.string.image_preview_transition_name, nowPosition);
            photoView.setTransitionName(name.concat(TextUtils.isEmpty(tag) ? "" : tag));
        }
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if (photoView.getScale() > 1.0f) {
                    photoView.setScale(1.0f, true);
                } else {
                    getActivity().supportFinishAfterTransition();
                }
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imagePreViewActivity = (ImagePreviewActivity) getActivity();
        ImagePreviewLoad.getImagePreviewLoadListener().load(this, imageUrl, new ImagePreviewLoadTarget(photoView) {
            @Override
            public void onLoadFailure() {
                progressBar.setVisibility(View.GONE);
                if (initPosition == nowPosition) {
                    imagePreViewActivity.finish();
                }
            }

            @Override
            public void onLoadSuccess() {
                progressBar.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    photoView.setTag(getString(R.string.image_preview_transition_name, nowPosition));
                    if (nowPosition == initPosition) {
                        setStartPostTransition(photoView);
                    }
                }
            }
        });
    }

    @TargetApi(21)
    private void setStartPostTransition(final View sharedView) {
        sharedView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedView.getViewTreeObserver().removeOnPreDrawListener(this);
                        getActivity().startPostponedEnterTransition();
                        return false;
                    }
                });
    }
}
