package org.cchao.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.text_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityToPreviewActivity.launch(MainActivity.this);
            }
        });
        findViewById(R.id.text_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentToPreviewActivity.launch(MainActivity.this);
            }
        });
        findViewById(R.id.text_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerViewToPreviewActivity.launch(MainActivity.this);
            }
        });
    }
}
