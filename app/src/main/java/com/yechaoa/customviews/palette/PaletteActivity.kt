package com.yechaoa.customviews.palette

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yechaoa.customviews.R
import com.yechaoa.customviews.databinding.ActivityPaletteBinding

class PaletteActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityPaletteBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        setListener()
    }

    private fun setListener() {
        mBinding.btnRed.setOnClickListener {
            mBinding.paletteView.setPaintColor(Color.RED)
        }

        mBinding.btnGreen.setOnClickListener {
            mBinding.paletteView.setPaintColor(Color.GREEN)
        }

        mBinding.btnWidth.setOnClickListener {
            mBinding.paletteView.setStrokeWidth(30f)
        }

        mBinding.btnClear.setOnClickListener {
            mBinding.paletteView.clear()
        }

        mBinding.btnRevert.setOnClickListener {
            mBinding.paletteView.revert()
        }

        mBinding.btnReset.setOnClickListener {
            mBinding.paletteView.reset()
        }

        mBinding.tvSave.setOnClickListener {
            mBinding.paletteView.save()
        }
    }
}