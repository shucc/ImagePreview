package org.cchao.imagepreviewlib;

import android.support.annotation.Nullable;

/**
 * Created by shucc on 17/2/24.
 * cc@cchao.org
 */
public class ImageLoader {

    private static ImageLoaderListener imageLoaderListener;

    public static void init(@Nullable ImageLoaderListener listener) {
        imageLoaderListener = listener;
    }

    protected static ImageLoaderListener getImageLoaderListener() {
        return imageLoaderListener;
    }
}
