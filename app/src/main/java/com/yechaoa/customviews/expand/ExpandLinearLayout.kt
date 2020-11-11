package com.yechaoa.customviews.expand

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Created by yechao on 2020/11/11.
 * Describe : 可收起展开的LinearLayout
 */
class ExpandLinearLayout : LinearLayout {

    //是否展开，默认展开
    private var isOpen = true

    //第一个子view的高度，即收起保留高度
    private var firstChildHeight = 0

    //所有子view高度，即总高度
    private var allChildHeight = 0

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initView()
    }

    /**
     * 值改变的时候 请求重新布局
     */
    private var animPercent: Float = 0f
        set(value) {
            field = value
            requestLayout()
        }

    private fun initView() {
        orientation = VERTICAL
        animPercent = 1f
        isOpen = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        //重置高度
        allChildHeight = 0
        firstChildHeight = 0

        if (childCount > 0) {

            //遍历计算高度
            for (index in 0 until childCount) {
                if (index == 0) {
                    firstChildHeight = getChildAt(index).measuredHeight
                }
                allChildHeight += getChildAt(index).measuredHeight
            }

            // 根据是否展开设置高度
            if (isOpen) {
                setMeasuredDimension(
                    widthMeasureSpec,
                    firstChildHeight + ((allChildHeight - firstChildHeight) * animPercent).toInt()
                )
            } else {
                setMeasuredDimension(
                    widthMeasureSpec,
                    allChildHeight - ((allChildHeight - firstChildHeight) * animPercent).toInt()
                )
            }
        }
    }

    fun toggle(): Boolean {
        isOpen = !isOpen
        startAnim()
        return isOpen
    }

    /**
     * 执行动画的时候 更改 animPercent 属性的值 即从0-1
     */
    @SuppressLint("AnimatorKeep")
    private fun startAnim() {
        //ofFloat，of xxxX 根据参数类型来确定
        //1，动画对象，即当前view。2.动画属性名。3，起始值。4，目标值。
        val animator = ObjectAnimator.ofFloat(this, "animPercent", 0f, 1f)
        animator.duration = 500
        animator.start()
    }
}