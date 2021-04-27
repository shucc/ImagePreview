package org.cchao.test.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.cchao.test.Constants;
import org.cchao.test.R;
import org.cchao.test.adapter.CommentAdapter;
import org.cchao.test.model.CommentModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shucc on 17/12/7.
 * cc@cchao.org
 */
public class RecyclerViewToPreviewActivity extends AppCompatActivity {

    private List<CommentModel> data;

    public static void launch(Context context) {
        Intent starter = new Intent(context, RecyclerViewToPreviewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_three);

        RecyclerView rvComment = findViewById(R.id.rv_comment);

        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CommentModel commentModel = new CommentModel();
            commentModel.setContent("我是内容" + i);
            List<String> images = Constants.TEMP_IMAGES;
            commentModel.setImages(images);
            data.add(commentModel);
        }

        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(new CommentAdapter(data));
    }
}
