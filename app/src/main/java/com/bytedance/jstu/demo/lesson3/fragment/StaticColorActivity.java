package com.bytedance.jstu.demo.lesson3.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.jstu.demo.R;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;


public class StaticColorActivity extends AppCompatActivity {

    private int lastColor = Color.BLUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_color);

        findViewById(R.id.btn_pick_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker picker = new ColorPicker(StaticColorActivity.this);
                picker.setColor(lastColor);
                picker.enableAutoClose();
                picker.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(int color) {
                        lastColor = color;
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, ColorFragment.newInstance(color))
                                .commit();
                    }
                });
                picker.show();
            }
        });
    }
}
