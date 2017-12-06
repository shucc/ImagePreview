package org.cchao.imagepreviewlib;

import android.support.v4.app.Fragment;

/**
 * Created by shucc on 17/12/6.
 * cc@cchao.org
 */
public interface ImagePreviewLoadListener {
    void load(Fragment fragment, String imageUrl, ImagePreviewLoadTarget imagePreviewLoadTarget);
}
