package com.yechaoa.customviews.progress

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yechaoa.customviews.databinding.ActivityProgressBinding

class ProgressActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityProgressBinding.inflate(layoutInflater) }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.tvTip.text = "最大：" + mBinding.progressBar.max + "   当前：" + mBinding.progressBar.progress

        mBinding.btnAdd.setOnClickListener {
            mBinding.progressBar.progress += 10
            mBinding.tvTip.text = "最大：" + mBinding.progressBar.max + "   当前：" + mBinding.progressBar.progress
        }
        mBinding.btnSubtract.setOnClickListener {
            mBinding.progressBar.progress -= 10
            mBinding.tvTip.text = "最大：" + mBinding.progressBar.max + "   当前：" + mBinding.progressBar.progress
        }

    }
}