package com.aib.utils

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Kotlin扩展类
 */
interface Extensions {
    fun <D : ViewDataBinding> ViewGroup.createBinding(resId: Int, attachToParent: Boolean): D {
        var binding: D? = null
        if (attachToParent) {
            binding = DataBindingUtil.inflate<D>(LayoutInflater.from(this.context), resId, null, attachToParent)
        } else {
            binding = DataBindingUtil.inflate<D>(LayoutInflater.from(this.context), resId, this, attachToParent)
        }
        return binding
    }
}