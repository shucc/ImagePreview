package org.cchao.imagepreviewlib;

import android.support.v4.app.Fragment;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by shucc on 17/2/24.
 * cc@cchao.org
 */
public interface ImageLoaderListener {

    void load(Fragment fragment, PhotoView photoView, String imageUrl);
}
