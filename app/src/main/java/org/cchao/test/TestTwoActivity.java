package org.cchao.test;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.cchao.imagepreviewlib.ImagePreViewActivity;

public class TestTwoActivity extends AppCompatActivity {

    private TestTwoFragment testTwoFragment;

    public static void launch(Context context) {
        Intent starter = new Intent(context, TestTwoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_two);

        testTwoFragment = TestTwoFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_fragment, testTwoFragment);
        transaction.commit();
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        testTwoFragment.onActivityReenter(resultCode, data);
    }
}
