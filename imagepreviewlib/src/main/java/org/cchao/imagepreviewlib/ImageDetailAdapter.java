package org.cchao.imagepreviewlib;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by shucc on 17/2/23.
 * cc@cchao.org
 */
public class ImageDetailAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> images;

    public ImageDetailAdapter(FragmentManager fm, ArrayList<String> images) {
        super(fm);
        this.images = images;
    }

    @Override
    public int getCount() {
        if (images == null) {
            return 0;
        }
        return images.size();
    }

    @Override
    public Fragment getItem(int position) {
        return ImageDetailFragment.newInstance(images.get(position));
    }
}
