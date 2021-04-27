package org.cchao.test.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.cchao.imagepreviewlib.ImagePreviewBuilder;
import org.cchao.imagepreviewlib.ImagePreviewExitListener;
import org.cchao.test.Constants;
import org.cchao.test.R;
import org.cchao.test.adapter.ImageAdapter;
import org.cchao.test.listener.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shucc on 17/10/23.
 * cc@cchao.org
 */
public class FragmentToPreviewFragment extends Fragment {

    private RecyclerView rvImage;

    private List<String> data;

    public static FragmentToPreviewFragment newInstance() {
        Bundle args = new Bundle();
        FragmentToPreviewFragment fragment = new FragmentToPreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_two, container, false);
        rvImage = view.findViewById(R.id.rv_image);

        data = Constants.TEMP_IMAGES;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvImage.setLayoutManager(gridLayoutManager);
        ImageAdapter imageAdapter = new ImageAdapter(data);
        rvImage.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                ImagePreviewBuilder.from((AppCompatActivity) getActivity())
                        .setInitPosition(position)
                        .setImageUrlArray(data)
                        .setPairView(view)
                        .setImagePreviewExitListener(new ImagePreviewExitListener() {
                            @Override
                            public View exitView(int exitPosition) {
                                return rvImage.findViewWithTag(exitPosition);
                            }
                        })
                        .startActivity();
            }
        });
        return view;
    }
}
