package com.bytedance.jstu.demo.lesson3.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.jstu.demo.R;


public class ScalePropertyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_property);

        final View imageView = findViewById(R.id.image_view);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView,
                "scaleX", 1.1f, 0.9f);
        scaleXAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scaleXAnimator.setInterpolator(new LinearInterpolator());
        scaleXAnimator.setDuration(1000);
        scaleXAnimator.setRepeatMode(ValueAnimator.REVERSE);

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView,
                "scaleY", 1.1f, 0.9f);
        scaleYAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scaleYAnimator.setInterpolator(new LinearInterpolator());
        scaleYAnimator.setDuration(1000);
        scaleYAnimator.setRepeatMode(ValueAnimator.REVERSE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();

        final ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final int value = (int) animation.getAnimatedValue();
                final ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.height =  value;
                imageView.requestLayout();

            }
        });
        animator.start();

//        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.breath);
//        animator.setTarget(findViewById(R.id.image_view));
//        animator.start();
    }
}
