package org.cchao.test.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
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
public class ActivityToPreviewActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();

    private RecyclerView rvImage;

    private List<String> data;

    public static void launch(Context context) {
        Intent starter = new Intent(context, ActivityToPreviewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_one);

        rvImage = findViewById(R.id.rv_image);

        data = Constants.TEMP_IMAGES;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvImage.setLayoutManager(gridLayoutManager);
        ImageAdapter imageAdapter = new ImageAdapter(data);
        rvImage.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                ImagePreviewBuilder.from(ActivityToPreviewActivity.this)
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
    }
}
