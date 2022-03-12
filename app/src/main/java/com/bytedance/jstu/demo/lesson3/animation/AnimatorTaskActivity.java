package com.bytedance.jstu.demo.lesson3.animation;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.jstu.demo.R;


public class AnimatorTaskActivity extends AppCompatActivity {

    private Button item;
    private TextView car;
    private LinearLayout carWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_task);
        item = findViewById(R.id.my_item);
        car = findViewById(R.id.cart);
        carWrapper = findViewById(R.id.cart_wrapper);

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
    }

    private void startAnimation() {
        final float carY = carWrapper.getY();
        final float initItemY = item.getY();
        final float dy = carY - initItemY;

        final ValueAnimator animatorY = ValueAnimator.ofFloat(0f, -0.2f, 1f);
        animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float value = (float) animation.getAnimatedValue();
                float curY = initItemY + value * dy;
                item.setY(curY);
            }
        });
        animatorY.setDuration(2000);
        animatorY.setInterpolator(new LinearInterpolator());

        float carX = car.getX();
        final float initItemX = item.getX();
        final float dx = carX - initItemX;

        final ValueAnimator animatorX = ValueAnimator.ofFloat(0f, 1f);
        animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float value = (float) animation.getAnimatedValue();
                float curX = initItemX + value * dx;
                item.setX(curX);
            }
        });
        animatorX.setInterpolator(new LinearInterpolator());
        animatorX.setDuration(2000);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorX, animatorY);
        set.start();
    }



}