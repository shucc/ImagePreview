package org.cchao.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class FragmentToPreviewActivity extends AppCompatActivity {

    public static void launch(Context context) {
        Intent starter = new Intent(context, FragmentToPreviewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_two);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_fragment, FragmentToPreviewFragment.newInstance());
        transaction.commit();
    }
}
