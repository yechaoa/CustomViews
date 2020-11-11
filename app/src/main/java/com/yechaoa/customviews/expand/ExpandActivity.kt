package com.yechaoa.customviews.expand

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.yechaoa.customviews.R
import kotlinx.android.synthetic.main.activity_expand.*

class ExpandActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expand)

        ll_btn.setOnClickListener {
            val toggle = ell.toggle()
            tv_tip.text = if (toggle) "收起" else "展开"
            startImageRotate(iv_arrow, toggle)
        }
    }

    /**
     * 旋转箭头图标
     */
    private fun startImageRotate(imageView: ImageView, toggle: Boolean) {
        val tarRotate: Float = if (toggle) {
            0f
        } else {
            180f
        }

        imageView.apply {
            ObjectAnimator.ofFloat(this, "rotation", rotation, tarRotate).let {
                it.duration = 300
                it.start()
            }
        }
    }
}