package com.yechaoa.customviews.randomtext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.yechaoa.customviews.R
import kotlin.random.Random

/**
 * Created by yechao on 2020/11/13.
 * Describe : 随机数字的TextView
 *
 * 步骤：
 * 1.自定义属性
 * 2.获取自定义样式
 * 3.绘制宽高
 * 4.设置点击事件
 *
 */
class RandomTextView : View {

    private lateinit var mRandomText: String
    private var mRandomTextColor: Int = 0
    private var mRandomTextSize: Int = 0

    var paint = Paint()

    var rect = Rect()

    //调用第二个构造
    constructor(context: Context) : this(context, null)

    //xml默认调用第二个构造，再调用第三个构造，在第三个构造里获取自定义属性
    constructor(context: Context, attributeSet: AttributeSet?) : this(
        context,
        attributeSet,
        R.attr.randomText
    )

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context) {
        //获取自定义属性
        val ta = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.RandomTextView,
            defStyle,
            0
        )

        for (index in 0 until ta.indexCount) {
            val attr = ta.getIndex(index)
            when (attr) {
                R.styleable.RandomTextView_randomText -> {
                    mRandomText = ta.getString(attr).toString()
                }
                R.styleable.RandomTextView_randomTextColor -> {
                    mRandomTextColor = ta.getColor(attr, Color.BLACK)//默认黑色
                }
                R.styleable.RandomTextView_randomTextSize -> {
                    // 默认值16sp，TypedValue可以把sp转为px
                    mRandomTextSize = ta.getDimensionPixelSize(
                        attr,
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP,
                            16F,
                            resources.displayMetrics
                        ).toInt()
                    )
                }
            }
        }

        ta.recycle()

        paint.textSize = mRandomTextSize.toFloat()

        paint.getTextBounds(mRandomText, 0, mRandomText.length, rect)


        //添加点击事件
        this.setOnClickListener {
            mRandomText = randomText()
            postInvalidate()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        /**
         * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
         * AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
         * UNSPECIFIED：表示子布局想要多大就多大，很少使用
         */
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var width: Int
        var height: Int

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize
        } else {
            paint.textSize = mRandomTextSize.toFloat()
            paint.getTextBounds(mRandomText, 0, mRandomText.length, rect)

            val textWidth = rect.width()
            val desiredWidth = paddingLeft + paddingRight + textWidth
            width = desiredWidth
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize
        } else {
            paint.textSize = mRandomTextSize.toFloat()
            paint.getTextBounds(mRandomText, 0, mRandomText.length, rect)

            val textHeight = rect.height()
            val desiredHeight = paddingLeft + paddingRight + textHeight
            height = desiredHeight
        }

        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var width: Int = width //屏幕宽度
        var height: Int = height //屏幕高度

        //矩形背景
        val bgRect = Rect(0, 0, width, height / 2)
        paint.color = Color.YELLOW
        canvas?.drawRect(bgRect, paint)

        paint.color = mRandomTextColor
        //注意这里的坐标xy不是左上角，而是左下角
        canvas?.drawText(
            mRandomText, (width / 2 - rect.width() / 2).toFloat(),
            (height / 4 + rect.height() / 2).toFloat(), paint
        )
    }

    private fun randomText(): String {

        val hashSet = hashSetOf<Int>()

        for (index in 0 until 4) {
            hashSet.add(Random.nextInt(10))
        }

        val stringBuffer = StringBuffer()

        for (i in hashSet) {
            stringBuffer.append("" + i)
        }

        return stringBuffer.toString()
    }

}
