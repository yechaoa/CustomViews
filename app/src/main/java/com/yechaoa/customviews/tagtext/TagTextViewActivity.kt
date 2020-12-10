package com.yechaoa.customviews.tagtext

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yechaoa.customviews.R
import kotlinx.android.synthetic.main.activity_tag_text_view.*

class TagTextViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_text_view)


        // TODO: 2020/12/10 标签字体大小，标签颜色集合

        val list1 = listOf("标签")

        tv_tag1.setTagAndText(
            list1,
            Color.parseColor("#F44336"),
            Color.parseColor("#FFCDD2"),
            getString(R.string.app_name)
        )


        val list2 = listOf("标签", "标签", "标签")

        tv_tag2.setTagAndText(
            list2,
            Color.parseColor("#1976D2"),
            Color.parseColor("#BBDEFB"),
            getString(R.string.test_string)
        )
    }
}