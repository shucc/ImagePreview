package org.cchao.test;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

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
                        .into(new SimpleTarget<GlideDrawable>() {

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                imagePreviewLoadTarget.onLoadFailure();
                            }

                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                imagePreviewLoadTarget.getImageView().setImageDrawable(resource.getCurrent());
                                imagePreviewLoadTarget.onLoadSuccess();
                            }
                        });
            }
        });
    }
}
