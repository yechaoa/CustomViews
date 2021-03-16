package com.yechaoa.customviews.palette

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yechaoa.customviews.R
import kotlinx.android.synthetic.main.activity_palette.*

class PaletteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palette)

        setSupportActionBar(toolbar)

        setListener()
    }

    private fun setListener() {
        btn_red.setOnClickListener {
            palette_view.setPaintColor(Color.RED)
        }

        btn_green.setOnClickListener {
            palette_view.setPaintColor(Color.GREEN)
        }

        btn_width.setOnClickListener {
            palette_view.setStrokeWidth(30f)
        }

        btn_clear.setOnClickListener {
            palette_view.clear()
        }

        btn_revert.setOnClickListener {
            palette_view.revert()
        }

        btn_reset.setOnClickListener {
            palette_view.reset()
        }

        tv_save.setOnClickListener {
            palette_view.save()
        }
    }
}