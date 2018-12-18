package com.aib.widget

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

class LoanDetailBehavior : CoordinatorLayout.Behavior<RelativeLayout> {

    constructor() {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun layoutDependsOn(parent: CoordinatorLayout, child: RelativeLayout, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: RelativeLayout, dependency: View): Boolean {
        val tb = dependency as AppBarLayout?
        child!!.translationY = -tb!!.y
        return true
    }
}
