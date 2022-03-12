package com.bytedance.jstu.demo.lesson3.animation;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.jstu.demo.R;


public class LikeActivity extends AppCompatActivity {

    public static final Long DURATION = 200L;

    private ImageView likeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        likeView = findViewById(R.id.like_image);
//        setAnimation();
        likeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimation2();
            }
        });
    }

    private void setAnimation2() {
        likeView.animate()
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(DURATION)
                .withEndAction(step2())
                .start();
    }

    private Runnable step2() {
        return new Runnable() {
            @Override
            public void run() {
                likeView.animate()
                        .translationY(-10)
                        .rotationX(-30)
                        .scaleX(1)
                        .scaleY(1)
                        .setDuration(DURATION)
                        .withEndAction(step3())
                        .start();
            }
        };
    }

    private Runnable step3() {
        return new Runnable() {
            @Override
            public void run() {
                likeView.animate()
                        .translationY(0)
                        .rotationX(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                likeView.setColorFilter(Color.RED);
                            }
                        });
            }
        };
    }

    private void setAnimation() {
        final Animation step1 = AnimationUtils.loadAnimation(this, R.anim.like_step_1);
        final Animation step2 = AnimationUtils.loadAnimation(this, R.anim.like_step_2);
        final Animation step3 = AnimationUtils.loadAnimation(this, R.anim.like_step_3);

        step1.setDuration(DURATION);
        step2.setDuration(DURATION);
        step3.setDuration(DURATION);


        step1.setAnimationListener(new EasyListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                likeView.setAnimation(step2);
            }
        });
        step2.setAnimationListener(new EasyListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                likeView.setAnimation(step3);
            }
        });
        step3.setAnimationListener(new EasyListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                likeView.setColorFilter(Color.RED);
            }
        });
        likeView.setAnimation(step1);
    }

    static abstract class EasyListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}