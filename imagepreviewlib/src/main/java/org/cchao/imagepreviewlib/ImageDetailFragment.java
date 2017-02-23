package org.cchao.imagepreviewlib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shucc on 17/2/23.
 * cc@cchao.org
 */
public class ImageDetailFragment extends Fragment {

    private static final String KEY_URL = "key_url";

    private ImageView imageView;

    private PhotoViewAttacher photoViewAttacher;

    private String imageUrl;

    public static ImageDetailFragment newInstance(String imageUrl) {
        Bundle args = new Bundle();
        args.putString(KEY_URL, imageUrl);
        ImageDetailFragment fragment = new ImageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        imageUrl = getArguments().getString(KEY_URL);

        View rootView = inflater.inflate(R.layout.fragment_image_detail, null);
        imageView = (ImageView) rootView.findViewById(R.id.img_detail);
        photoViewAttacher = new PhotoViewAttacher(imageView);
        photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                getActivity().finish();
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
        Glide.with(this)
                .load(imageUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.MATCH_PARENT));
                        return false;
                    }
                })
                .dontAnimate()
                .into(imageView);
    }
}
