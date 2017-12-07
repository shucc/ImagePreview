package org.cchao.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
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
            List<String> images = new ArrayList<>();
            images.add("http://img3.duitang.com/uploads/item/201606/04/20160604010014_Art48.thumb.224_0.jpeg");
            images.add("http://imgsrc.baidu.com/forum/w=580/sign=a3d6766038292df597c3ac1d8c305ce2/20e941c2d5628535d2e5616e92ef76c6a6ef63b5.jpg");
            images.add("http://imgsrc.baidu.com/forum/w%3D580/sign=ba6c1291f21f3a295ac8d5c6a924bce3/028195504fc2d562b30d63a2e51190ef77c66cb5.jpg");
            images.add("http://pic1.ipadown.com/imgs/20110326172546834.jpg");
            images.add("http://n.sinaimg.cn/97973/transform/20160115/5AgV-fxnqrkc6483530.jpg");
            commentModel.setImages(images);
            data.add(commentModel);
        }

        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(new CommentAdapter(data));
    }
}
