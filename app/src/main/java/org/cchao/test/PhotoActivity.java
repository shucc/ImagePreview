package org.cchao.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PhotoActivity extends AppCompatActivity {

    public static final String KEY_IMAGE_URL = "key_image_url";

    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imgPhoto = (ImageView) findViewById(R.id.img_photo);

        String url = getIntent().getStringExtra(KEY_IMAGE_URL);
        Glide.with(this).load(url).into(imgPhoto);
    }
}
