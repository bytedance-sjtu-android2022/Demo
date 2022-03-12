package com.bytedance.jstu.demo.lesson3

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R
import com.bytedance.jstu.demo.lesson3.animation.*
import com.bytedance.jstu.demo.lesson3.fragment.DynamicAddFragmentActivity
import com.bytedance.jstu.demo.lesson3.fragment.FragmentAActivity
import com.bytedance.jstu.demo.lesson3.fragment.LifecycleFragmentActivity
import com.bytedance.jstu.demo.lesson3.fragment.StaticColorActivity
import com.bytedance.jstu.demo.lesson3.masterdetail.ItemsListActivity

class Lesson3DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson3_demo)

        bindActivity(R.id.btn_show_fragment_a, FragmentAActivity::class.java)
        bindActivity(R.id.btn_fragment_add_demo, DynamicAddFragmentActivity::class.java)
        bindActivity(R.id.btn_fragment_arguments, StaticColorActivity::class.java)
        bindActivity(R.id.btn_master_detail, ItemsListActivity::class.java)

        bindActivity(R.id.btn_frame_animation, FrameAnimationActivity::class.java)
        bindActivity(R.id.btn_show_animation, AnimationActivity::class.java)
        bindActivity(R.id.btn_show_like, LikeActivity::class.java)

        bindActivity(R.id.btn_rotation_demo, RotationPropertyActivity::class.java)
        bindActivity(R.id.btn_scale_demo, ScalePropertyActivity::class.java)
        bindActivity(R.id.btn_lottie_demo, LottieActivity::class.java)
        bindActivity(R.id.btn_fragment_lifecycle_demo, LifecycleFragmentActivity::class.java)
        bindActivity(R.id.btn_animator, AnimatorTaskActivity::class.java)
    }

    private fun bindActivity(btnId: Int, activityClass: Class<*>) {
        findViewById<View>(btnId).setOnClickListener {
            startActivity(Intent(this, activityClass))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}