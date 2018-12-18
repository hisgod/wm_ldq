package com.aib.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class MultipleMenu(ctx: Context, attrs: AttributeSet) : LinearLayout(ctx, attrs) {

    //    private var defaultWidth=
//    private var defaultHeight=
    private var width: Int? = null  //组件宽度
    private var height: Int? = null //组件高度

    init {

    }

    /**
     * 测量方法
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val getWidthSize = MeasureSpec.getSize(widthMeasureSpec)   //测量宽度
        val getWidthMode = MeasureSpec.getMode(widthMeasureSpec)   //测量宽度方式
        val getHeightSize = MeasureSpec.getSize(heightMeasureSpec) //测量高度
        val getHeightMode = MeasureSpec.getMode(heightMeasureSpec) //测量高度方式

        //无子View，父亲宽高无效
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else {
            when (getWidthMode) {
                MeasureSpec.EXACTLY -> {
                    width = getWidthSize
                }

                MeasureSpec.AT_MOST -> {
                    width = getWidthSize
                }

                MeasureSpec.UNSPECIFIED -> {
                    width = getWidthSize
                }
            }

            when (getHeightMode) {
                MeasureSpec.AT_MOST -> {
                    height = getHeightSize
                }
                MeasureSpec.UNSPECIFIED -> {
                    height = getHeightSize
                }
                MeasureSpec.EXACTLY -> {
                    height = getHeightSize
                }
            }

            setMeasuredDimension(width!!, height!!)
        }
    }

    /**
     * 布局
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

    }
}