package com.yechaoa.customviews.palette

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Environment
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yechao on 2021/3/12.
 * Describe : 画板，主要涉及paint的常用属性，以及path的用法
 */
class PaletteView : View {

    private val tag = this.javaClass.name

    private var mPaint: Paint = Paint()
    private lateinit var mPath: Path
    private var mPaletteList = mutableListOf<PaletteInfo>()

    //起始位置
    private var startX: Float = 0f
    private var startY: Float = 0f

    //结束位置
    private var stopX = 0f
    private var stopY = 0f

    private lateinit var cacheBitmap: Bitmap  //定义一个内存中的图片，该图片作为缓冲区
    private lateinit var cacheCanvas: Canvas //定义cacheBitmap上的Canvas对象

    class PaletteInfo constructor(var path: Path, var paint: Paint)

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

                if (!::cacheBitmap.isInitialized) {
                    initCache()
                }
                mPath = Path()
                //加到集合里，保存每一次轨迹
                mPaletteList.add(PaletteInfo(Path(mPath), Paint(mPaint)))
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

                cacheCanvas.drawPath(mPath, mPaint)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                //重置
                mPath.reset()
            }
        }
        return true
    }

    private fun initCache() {
        //创建一个相同大小的缓存区
        cacheBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
        //一个新的画布
        cacheCanvas = Canvas()
        cacheCanvas.setBitmap(cacheBitmap)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            it.drawColor(Color.WHITE)//bg
            if (::cacheBitmap.isInitialized) {
                it.drawBitmap(cacheBitmap, 0f, 0f, null)
            }
        }
    }

    /**
     * 设置颜色
     */
    fun setPaintColor(@ColorInt color: Int) {
        initPaint()
        //清除 xfermode 取消橡皮擦效果
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
    }

    /**
     * 撤回 need to do
     */
    fun revert() {
        if (mPaletteList.size > 0) {
            Log.i(tag, "revert---" + mPaletteList.size)
            mPaletteList.removeAt(mPaletteList.size - 1)
//            drawCache()
//            cacheBitmap.eraseColor(Color.TRANSPARENT)
            mPaletteList.forEach {
                cacheCanvas.drawPath(it.path, it.paint)
            }
            invalidate()
        }
    }

    /**
     * 清空
     */
    fun reset() {
        if (mPaletteList.size > 0) {
            cacheBitmap.eraseColor(Color.TRANSPARENT)
            invalidate()
        }
    }

    private fun drawCache() {
        Log.i(tag, "drawCache---" + mPaletteList.size)
        cacheBitmap.eraseColor(Color.TRANSPARENT)
        mPaletteList.forEach {
            cacheCanvas.drawPath(it.path, it.paint)
        }
        invalidate()
    }

    /**
     * 保存 need test
     * \内部存储\Android\data\com.fr.lawappandroid\files\Pictures
     */
    fun save() {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var file: File? = null
        try {
            // prefix 前缀 , suffix 后缀 , directory 目录
            file = File.createTempFile(getFileName(), ".png", storageDir)
            val out = FileOutputStream(file)
            cacheBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Toast.makeText(context, file?.name, Toast.LENGTH_SHORT).show()
    }

    /**
     * 时间戳文件名
     * filename="JPEG_20201222_115650_6934479097884473000.jpg
     */
    private fun getFileName(): String {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return "PIC_" + timeStamp + "_"
    }
}