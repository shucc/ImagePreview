package org.cchao.test;

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

import java.util.ArrayList;
import java.util.List;

public class DialogFragmentToPreviewDialog extends DialogFragment {

    private final String TAG = getClass().getName();

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
        getDialog().getWindow().getDecorView().setPadding(0, 0,0,0);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(true);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        getDialog().getWindow().setAttributes(params);
        data = new ArrayList<>();
        data.add("http://img3.duitang.com/uploads/item/201606/04/20160604010014_Art48.thumb.224_0.jpeg");
        data.add("http://imgsrc.baidu.com/forum/w=580/sign=a3d6766038292df597c3ac1d8c305ce2/20e941c2d5628535d2e5616e92ef76c6a6ef63b5.jpg");
        data.add("http://imgsrc.baidu.com/forum/w%3D580/sign=ba6c1291f21f3a295ac8d5c6a924bce3/028195504fc2d562b30d63a2e51190ef77c66cb5.jpg");
        data.add("http://pic1.ipadown.com/imgs/20110326172546834.jpg");
        data.add("http://n.sinaimg.cn/97973/transform/20160115/5AgV-fxnqrkc6483530.jpg");

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
