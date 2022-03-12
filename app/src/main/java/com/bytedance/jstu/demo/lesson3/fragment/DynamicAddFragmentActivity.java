package com.bytedance.jstu.demo.lesson3.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bytedance.jstu.demo.R;


public class DynamicAddFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_add_fragment);

        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, new FragmentA())
                .commit();
        final Button replaceBtn = findViewById(R.id.replace_btn);

        replaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFragmentManager
                        .beginTransaction()
                        .addToBackStack("stack1")
                        .replace(R.id.fragment_container, new FragmentB())
                        .commit();
            }
        });
    }
}
