package org.cchao.imagepreviewlib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shucc on 17/2/23.
 * cc@cchao.org
 */
public class ImageDetailFragment extends Fragment {

    private static final String KEY_URL = "key_url";

    private PhotoView photoView;

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
        photoView = (PhotoView) rootView.findViewById(R.id.img_detail);

        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
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
        if (null != ImageLoader.getImageLoaderListener()) {
            ImageLoader.getImageLoaderListener().load(getContext(), photoView, imageUrl);
        } else {
            throw new NullPointerException("ImageLoader not initialized!");
        }
    }
}
