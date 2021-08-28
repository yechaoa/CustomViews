package com.yechaoa.customviews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yechaoa.customviews.databinding.ActivityMainBinding
import com.yechaoa.customviews.expand.ExpandActivity
import com.yechaoa.customviews.palette.PaletteActivity
import com.yechaoa.customviews.progress.ProgressActivity
import com.yechaoa.customviews.randomtext.RandomTextActivity
import com.yechaoa.customviews.roundimg.RoundImageViewActivity
import com.yechaoa.customviews.tagtext.TagTextViewActivity

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.btnExpand.setOnClickListener {
            startActivity(Intent(this, ExpandActivity::class.java))
        }

        mBinding.btnProgress.setOnClickListener {
            startActivity(Intent(this, ProgressActivity::class.java))
        }

        mBinding.btnRandomText.setOnClickListener {
            startActivity(Intent(this, RandomTextActivity::class.java))
        }

        mBinding.btnTagText.setOnClickListener {
            startActivity(Intent(this, TagTextViewActivity::class.java))
        }

        mBinding.btnPalette.setOnClickListener {
            startActivity(Intent(this, PaletteActivity::class.java))
        }

        mBinding.btnRoundImage.setOnClickListener {
            startActivity(Intent(this, RoundImageViewActivity::class.java))
        }

    }
}