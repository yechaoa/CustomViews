package com.yechaoa.customviews.palette

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt

/**
 * Created by yechao on 2021/3/12.
 * Describe : 画板，主要涉及paint的常用属性，以及path的用法
 */
class PaletteView : View {

    private var mPaint: Paint = Paint()
    private lateinit var mPath: Path
    private var mPathList = mutableListOf<Path>()

    //起始位置
    private var startX: Float = 0f
    private var startY: Float = 0f

    //结束位置
    private var stopX = 0f
    private var stopY = 0f

    constructor(context: Context) : super(context) {
        init()
    }

    //如果只是在xml文件中使用，实现这一个构造即可
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        init()
    }

    private fun init() {
        initPaint()
    }

    private fun initPaint() {
        //设置颜色
        mPaint.color = Color.RED
        //线条宽度
        mPaint.strokeWidth = 10f
        //画线模式
        mPaint.style = Paint.Style.STROKE
        //线头形状 圆头
        mPaint.strokeCap = Paint.Cap.ROUND
        //拐角形状 圆角
        mPaint.strokeJoin = Paint.Join.ROUND
        //开启抗锯齿
        mPaint.isAntiAlias = true
        //防抖动
        mPaint.isDither = true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val currX = event.x
        val currY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = currX
                startY = currY

                mPath = Path()
                //加到集合里，保存每一次轨迹
                mPathList.add(mPath)

                //每一次开始的起始位置
                mPath.moveTo(currX, currY)
            }
            MotionEvent.ACTION_MOVE -> {
                stopX = currX
                stopY = currY
                //二次贝塞尔曲线
                mPath.quadTo(startX, startY, stopX, stopY)

                //再次赋值 更新位置
                startX = currX
                startY = currY
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            it.drawColor(Color.WHITE)
            mPathList.forEach { path ->
                it.drawPath(path, mPaint)
            }
            it.save()
        }
    }

    /**
     * 设置颜色
     */
    fun setPaintColor(@ColorInt color: Int) {
        initPaint()
        //取消橡皮擦效果
        mPaint.xfermode = null
        mPaint.color = color
    }

    /**
     * 设置线条宽度
     */
    fun setStrokeWidth(width: Float) {
        mPaint.strokeWidth = width
    }

    /**
     * 橡皮擦
     */
    fun clear() {
        //设置颜色策略（源图像绘制到目标图像处时应该怎样确定二者结合后的颜色）
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        //橡皮擦的时候可以适当加大，实际项目中应该是SeekBar调节
        mPaint.strokeWidth = 50f
        mPaint.color = Color.TRANSPARENT
    }

    /**
     * 撤回
     */
    fun revert() {
        if (mPathList.size > 0) {
            mPathList.last { mPathList.remove(it) }
            invalidate()
        }
    }

    /**
     * 清空
     */
    fun reset() {
        if (mPathList.size > 0) {
            mPathList.removeAll {
                invalidate()
                return@removeAll true
            }
        }
    }
}