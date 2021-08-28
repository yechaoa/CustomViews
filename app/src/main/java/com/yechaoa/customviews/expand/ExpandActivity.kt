package com.yechaoa.customviews.expand

import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.yechaoa.customviews.databinding.ActivityExpandBinding

class ExpandActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityExpandBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.llBtn.setOnClickListener {
            val toggle = mBinding.ell.toggle()
            mBinding.tvTip.text = if (toggle) "收起" else "展开"
            startImageRotate(mBinding.ivArrow, toggle)
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