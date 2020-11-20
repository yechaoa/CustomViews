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
 * 2.添加构造方法
 * 3.在构造里获取自定义样式
 * 4.重写onMeasure测量宽高
 * 5.重写onDraw计算坐标绘制
 * 6.设置点击事件
 */
class RandomTextView : View {

    //文本
    private var mRandomText: String

    //文本颜色
    private var mRandomTextColor: Int = 0

    //文本字体大小
    private var mRandomTextSize: Int = 0

    private var paint = Paint()
    private var bounds = Rect()

    //调用两个参数的构造
    constructor(context: Context) : this(context, null)

    //xml默认调用两个参数的构造，再调用三个参数的构造，在三个参数构造里获取自定义属性
    constructor(context: Context, attributeSet: AttributeSet?) : this(
        context,
        attributeSet,
        0
    )

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        //获取自定义属性
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.RandomTextView,
            defStyle,
            0
        )

        mRandomText = typedArray.getString(R.styleable.RandomTextView_randomText).toString()
        mRandomTextColor =
            typedArray.getColor(R.styleable.RandomTextView_randomTextColor, Color.BLACK)//默认黑色
        mRandomTextSize = typedArray.getDimensionPixelSize(
            R.styleable.RandomTextView_randomTextSize,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16F, resources.displayMetrics
            ).toInt()
        )

        //获取完回收
        typedArray.recycle()


        paint.textSize = mRandomTextSize.toFloat()

        //返回文本边界，即包含文本的最小矩形，没有所谓“留白”，返回比measureText()更精确的text宽高，数据保存在bounds里
        paint.getTextBounds(mRandomText, 0, mRandomText.length, bounds)

        /**
         * 添加点击事件
         */
        this.setOnClickListener {
            mRandomText = randomText()
            //更新
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

        var width = 0
        var height = 0

        //如果指定了宽度，或不限制宽度，用可用宽度即可，如果是WARP_CONTENT，则用文本宽度，再加上左右padding
        when (widthMode) {
            MeasureSpec.UNSPECIFIED,
            MeasureSpec.EXACTLY -> {
                width = widthSize + paddingLeft + paddingRight
            }
            MeasureSpec.AT_MOST -> {
                width = bounds.width() + paddingLeft + paddingRight
            }
        }

        //如果指定了高度，或不限制高度，用可用高度即可，如果是WARP_CONTENT，则用文本高度，再加上上下padding
        when (heightMode) {
            MeasureSpec.UNSPECIFIED,
            MeasureSpec.EXACTLY -> {
                height = heightSize + paddingTop + paddingBottom
            }
            MeasureSpec.AT_MOST -> {
                height = bounds.height() + paddingTop + paddingBottom
            }
        }

        //保存测量的宽高
        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        /**
         * 自定义View时，需要我们自己在onDraw中处理padding，否则是不生效的
         * 自定义ViewGroup时，子view的padding放在onMeasure中处理
         */

        /**
         * 矩形背景
         */
        paint.color = Color.YELLOW
        //计算坐标，因为原点是在文字的左下角，左边要是延伸出去就还要往左边去，所以是减，右边和下边是正，所以是加
        canvas?.drawRect(
            (0 - paddingLeft).toFloat(),
            (0 - paddingTop).toFloat(),
            (measuredWidth + paddingRight).toFloat(),
            (measuredHeight + paddingBottom).toFloat(),
            paint
        )

        /**
         * 文本
         */
        paint.color = mRandomTextColor
        //注意这里的坐标xy不是左上角，而是左下角，所以高度是相加的，在自定义view中，坐标轴右下为正
        //getWidth 等于 measuredWidth
        canvas?.drawText(
            mRandomText,
            (width / 2 - bounds.width() / 2).toFloat(),
            (height / 2 + bounds.height() / 2).toFloat(),
            paint
        )
    }

    /**
     * 根据文本长度 随意数字
     */
    private fun randomText(): String {

        val list = mutableListOf<Int>()
        for (index in mRandomText.indices) {
            list.add(Random.nextInt(10))
        }

        val stringBuffer = StringBuffer()
        for (i in list) {
            stringBuffer.append("" + i)
        }

        return stringBuffer.toString()
    }

}
