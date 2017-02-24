package org.cchao.imagepreviewlib;

import android.content.Context;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by shucc on 17/2/24.
 * cc@cchao.org
 */
public interface ImageLoaderListener {

    void load(Context context, PhotoView photoView, String imageUrl);
}
