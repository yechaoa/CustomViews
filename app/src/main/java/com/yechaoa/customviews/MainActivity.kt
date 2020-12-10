package com.yechaoa.customviews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yechaoa.customviews.expand.ExpandActivity
import com.yechaoa.customviews.progress.ProgressActivity
import com.yechaoa.customviews.randomtext.RandomTextActivity
import com.yechaoa.customviews.tagtext.TagTextViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_expand.setOnClickListener {
            startActivity(Intent(this,ExpandActivity::class.java))
        }

        btn_progress.setOnClickListener {
            startActivity(Intent(this, ProgressActivity::class.java))
        }

        btn_randomText.setOnClickListener {
            startActivity(Intent(this, RandomTextActivity::class.java))
        }

        btn_tagText.setOnClickListener {
            startActivity(Intent(this, TagTextViewActivity::class.java))
        }

    }
}