package org.cchao.test;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.cchao.imagepreviewlib.ImagePreviewLoad;
import org.cchao.imagepreviewlib.ImagePreviewLoadListener;
import org.cchao.imagepreviewlib.ImagePreviewLoadTarget;

/**
 * Created by shucc on 17/12/6.
 * cc@cchao.org
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImagePreview();
    }

    private void initImagePreview() {
        ImagePreviewLoad.setImagePreviewLoadListener(new ImagePreviewLoadListener() {
            @Override
            public void load(Fragment fragment, String imageUrl, final ImagePreviewLoadTarget imagePreviewLoadTarget) {
                Glide.with(fragment)
                        .load(imageUrl)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                imagePreviewLoadTarget.getImageView().setImageDrawable(resource.getCurrent());
                                imagePreviewLoadTarget.onLoadSuccess();
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                imagePreviewLoadTarget.onLoadFailure();
                            }
                        });
            }
        });
    }
}
