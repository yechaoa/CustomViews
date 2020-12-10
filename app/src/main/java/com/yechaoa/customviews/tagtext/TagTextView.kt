package com.yechaoa.customviews.tagtext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.yechaoa.customviews.R
import kotlinx.android.synthetic.main.item_text_tag.view.*


/**
 * Created by yechao on 2020/10/29.
 * Describe :
 */
class TagTextView : androidx.appcompat.widget.AppCompatTextView {

    private var mContext: Context

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        mContext = context
    }

    fun setTagAndText(
        tags: List<String>, textColor: Int, bgColor: Int,
        text: String, startIndex: Int = 0, endIndex: Int = 0, titleColor: Int? = null
    ) {

        val spanBuilder = SpannableStringBuilder()

        val stringBuffer = StringBuffer()
        //将每个tag的内容添加到text之前，之后将用drawable替代这些tag所占的位置
        tags.forEach {
            stringBuffer.append(it)
        }
        var span = SpannableString(stringBuffer)

        tags.forEachIndexed { index, str ->
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_text_tag, null)
            view.tv_tag.setTextColor(textColor)
            view.tv_tag.setBackgroundColor(bgColor)
            view.tv_tag.text = str

            //转为bitmap
            val bitmap = convertViewToBitmap(view)
            val bitmapDrawable = BitmapDrawable(resources, bitmap)
            //绘制位置
            bitmapDrawable.setBounds(0, 0, view.tv_tag.width, view.tv_tag.height)

            //图片将对齐底部边线
            val imageSpan = ImageSpan(bitmapDrawable, ImageSpan.ALIGN_BOTTOM)
            val start = getLastLength(tags, index)
            val end = start + str.length
            /**
             * what：对SpannableString进行润色的各种Span；
             * int：需要润色文字段开始的下标；
             * end：需要润色文字段结束的下标；
             * flags：决定开始和结束下标是否包含的标志位，有四个参数可选，默认SPAN_EXCLUSIVE_EXCLUSIVE 含头不含尾
             */
            span.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        spanBuilder.append(span)

        span = SpannableString(text)
        //endIndex!=0表示需要标题需要高亮
        if (endIndex != 0) {
            span.setSpan(ForegroundColorSpan(titleColor!!), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        spanBuilder.append(span)

        setText(spanBuilder)

        gravity = Gravity.CENTER_VERTICAL

    }

    private fun convertViewToBitmap(view: View): Bitmap? {
        //利用 LayoutInflater 生成的 View 并没有经过 measure() 和 layout() 方法的洗礼，所以一定没对它的 width 和 height 等属性赋值
        view.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)

        //返回Bitmap
        view.buildDrawingCache()
        return view.getDrawingCache()
    }


    private fun getLastLength(list: List<String>, maxLength: Int): Int {
        var length = 0
        for (i in 0 until maxLength) {
            length += list[i].length
        }
        return length
    }

}