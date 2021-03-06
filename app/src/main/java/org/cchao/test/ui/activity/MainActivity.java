package org.cchao.test.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.cchao.test.R;
import org.cchao.test.ui.dialog.DialogFragmentToPreviewDialog;

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
        findViewById(R.id.text_four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPagerActivity.launch(MainActivity.this);
            }
        });
        findViewById(R.id.text_five).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentToPreviewDialog dialog = DialogFragmentToPreviewDialog.newInstance();
                dialog.show(getSupportFragmentManager(), "DialogFragmentToPreviewDialog");
            }
        });
    }
}
