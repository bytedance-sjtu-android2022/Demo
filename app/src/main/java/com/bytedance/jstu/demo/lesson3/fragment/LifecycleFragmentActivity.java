package com.bytedance.jstu.demo.lesson3.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.jstu.demo.R;


public class LifecycleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_fragment);
    }
}
