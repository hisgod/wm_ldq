package com.aib.widget

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

/**
 * 推荐首页顶部Toolbar的Behavior
 */
class HomeToolbarBehavior : CoordinatorLayout.Behavior<View> {
    private var deltaY: Float = 0.toFloat()

    constructor() {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is RecyclerView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        if (deltaY == 0f) {
            deltaY = dependency.y - child.height
        }

        var dy = dependency.y - child.height
        dy = if (dy < 0) 0F else dy
        val y = -(dy / deltaY) * child.height
        child.translationY = y

        //设置透明度
        val alpha = 1 - dy / deltaY
        child.alpha = alpha

        return true
    }
}
