package org.cchao.test.ui.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
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

public class DialogFragmentToPreviewDialog extends DialogFragment {

    private RecyclerView rvImage;

    private List<String> data;

    public static DialogFragmentToPreviewDialog newInstance() {
        Bundle args = new Bundle();
        DialogFragmentToPreviewDialog fragment = new DialogFragmentToPreviewDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_test_two, null);
        rvImage = view.findViewById(R.id.rv_image);
        getDialog().getWindow().getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(true);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        getDialog().getWindow().setAttributes(params);
        data = Constants.TEMP_IMAGES;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvImage.setLayoutManager(gridLayoutManager);
        ImageAdapter imageAdapter = new ImageAdapter(data);
        rvImage.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //此处需要在Dialog依附的Activity的style中设置<item name="android:windowSharedElementsUseOverlay">false</item>，指示共享元素在转换期间不应使用叠加层
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
