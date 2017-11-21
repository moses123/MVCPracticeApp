package com.test.testapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.testapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
