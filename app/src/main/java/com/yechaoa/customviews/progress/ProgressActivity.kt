package com.yechaoa.customviews.progress

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yechaoa.customviews.R
import kotlinx.android.synthetic.main.activity_progress.*

class ProgressActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        tv_tip.text = "最大："+progress_bar.max+"   当前："+progress_bar.progress

        btn_add.setOnClickListener {
            progress_bar.progress += 10
            tv_tip.text = "最大："+progress_bar.max+"   当前："+progress_bar.progress
        }
        btn_subtract.setOnClickListener {
            progress_bar.progress -= 10
            tv_tip.text = "最大："+progress_bar.max+"   当前："+progress_bar.progress
        }

    }
}