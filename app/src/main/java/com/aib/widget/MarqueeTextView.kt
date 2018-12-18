package com.aib.widget

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

/**
 * 跑马灯效果
 */
class MarqueeTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {

    /**
     * a.当前控件的焦点,第一次xml加载 的情况
     */
    override fun isFocused(): Boolean {
        return true//告诉我们的系统 ,我这里是一直有焦点的
    }

    //b.在更改焦点时,有别的控件申请焦点的情况下
    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        if (focused) {//有焦点
            super.onFocusChanged(focused, direction, previouslyFocusedRect)
        }
    }

    //c.弹出对话框的情况下
    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus)
        }
    }
}
