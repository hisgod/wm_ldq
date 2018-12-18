package com.aib.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View

class SuperView(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

    private var defaultWidth = 100  //如果是wrap_content,就指定默认宽度为100PX
    private var defaultHeight = 50  //如果是wrap_content，就默认指定默认高度为50PX
    private var width: Int? = null  //宽
    private var height: Int? = null //高

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val getWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val getWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val getHeightSize = MeasureSpec.getSize(heightMeasureSpec)
        val getHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        when (getWidthMode) {
            MeasureSpec.UNSPECIFIED -> {
                //match_parent
                width = getWidthSize
            }
            MeasureSpec.AT_MOST -> {
                //wrap_content
                width = defaultWidth
            }
            MeasureSpec.EXACTLY -> {
                //固定大小
                width = getWidthSize
            }
        }

        when (getHeightMode) {
            MeasureSpec.EXACTLY -> {
                height = getHeightSize
            }
            MeasureSpec.UNSPECIFIED -> {
                height = getHeightSize
            }
            MeasureSpec.AT_MOST -> {
                height = defaultHeight
            }
        }

        setMeasuredDimension(width!!, height!!) //将宽高设置进去
    }
}