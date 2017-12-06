package org.cchao.imagepreviewlib;

import android.widget.ImageView;

/**
 * Created by shucc on 17/12/6.
 * cc@cchao.org
 */
public abstract class ImagePreviewLoadTarget {

    private ImageView imageView;

    public ImagePreviewLoadTarget(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public abstract void onLoadSuccess();

    public abstract void onLoadFailure();
}
