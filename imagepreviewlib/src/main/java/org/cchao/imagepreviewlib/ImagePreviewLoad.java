package org.cchao.imagepreviewlib;

/**
 * Created by shucc on 17/12/6.
 * cc@cchao.org
 */
public class ImagePreviewLoad {

    private static ImagePreviewLoadListener imagePreviewLoadListener;

    public static ImagePreviewLoadListener getImagePreviewLoadListener() {
        return imagePreviewLoadListener;
    }

    public static void setImagePreviewLoadListener(ImagePreviewLoadListener imagePreviewLoadListener) {
        ImagePreviewLoad.imagePreviewLoadListener = imagePreviewLoadListener;
    }
}
