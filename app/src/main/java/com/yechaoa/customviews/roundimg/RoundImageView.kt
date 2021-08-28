package com.yechaoa.customviews.roundimg

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView
import com.yechaoa.customviews.R

/**
 * GitHub : https://github.com/yechaoa
 * CSDN : http://blog.csdn.net/yechaoa
 *
 * Created by yechao on 2021/7/25.
 * Describe : 圆角ImageView
 * 1.clipPath
 * 2.PorterDuffXfermode
 */
class RoundImageView : AppCompatImageView {

    private var mCornerSize = 0F
    private var mWidth = 0F
    private var mHeight = 0F

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        //获取自定义属性
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet, R.styleable.RoundImageView, defStyle, 0
        )
        mCornerSize = typedArray.getDimensionPixelSize(
            R.styleable.RoundImageView_myCornerSize,
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 0F, resources.displayMetrics).toInt()
        ).toFloat()
        typedArray.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mWidth = width.toFloat()
        mHeight = height.toFloat()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        if (mWidth > mCornerSize && mHeight > mCornerSize) {
            // 画圆角
            val path = Path()
            path.moveTo(mCornerSize, 0f)
            //右上
            path.lineTo(mWidth - mCornerSize, 0f)
            path.quadTo(mWidth, 0f, mWidth, mCornerSize)
            //右下
            path.lineTo(mWidth, mHeight - mCornerSize)
            path.quadTo(mWidth, mHeight, mWidth - mCornerSize, mHeight)
            //左下
            path.lineTo(mCornerSize, mHeight)
            path.quadTo(0f, mHeight, 0f, mHeight - mCornerSize)
            //左上
            path.lineTo(0f, mCornerSize)
            path.quadTo(0f, 0f, mCornerSize, 0f)
            canvas?.clipPath(path)
        }
        super.onDraw(canvas)
    }

}