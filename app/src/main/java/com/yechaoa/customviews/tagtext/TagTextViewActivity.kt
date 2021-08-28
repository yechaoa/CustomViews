package com.yechaoa.customviews.tagtext

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yechaoa.customviews.R
import com.yechaoa.customviews.databinding.ActivityTagTextViewBinding

class TagTextViewActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityTagTextViewBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        // TODO: 2020/12/10 标签字体大小，标签颜色集合

        val list1 = listOf("标签")

        mBinding.tvTag1.setTagAndText(
            list1,
            Color.parseColor("#F44336"),
            Color.parseColor("#FFCDD2"),
            getString(R.string.app_name)
        )


        val list2 = listOf("会员价", "促销", "抢购")

        mBinding.tvTag2.setTagAndText(
            list2,
            Color.parseColor("#1976D2"),
            Color.parseColor("#BBDEFB"),
            getString(R.string.test_string)
        )

        val list3 = listOf("标签")

        mBinding.tvTag3.setTagAndText(
            list3,
            Color.parseColor("#388E3C"),
            Color.parseColor("#C8E6C9"),
            getString(R.string.test_string),
            6,
            8,
            Color.parseColor("#4CAF50")
        )
    }
}