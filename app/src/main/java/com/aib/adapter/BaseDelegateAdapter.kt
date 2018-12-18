package com.aib.adapter

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.aib.utils.Extensions
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper

/**
 * 阿里Vlayout适配器基类
 */
abstract class BaseDelegateAdapter<D : ViewDataBinding> : DelegateAdapter.Adapter<BaseDelegateAdapter<D>.BaseViewHolder>(), Extensions {
    private lateinit var binding: D
    var ctx: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        ctx = parent.context
        val binding = parent.createBinding<D>(getResId(), attachToParent())
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return getCount()
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        return getLayoutHelper()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bindView(holder.binding, position)
    }

    /**
     * layout的时候是否考虑父布局
     */
    abstract fun attachToParent(): Boolean

    /**
     * 获取布局资源文件ID
     */
    abstract fun getResId(): Int

    /**
     * 返回Item条目
     */
    abstract fun getCount(): Int

    /**
     * Vlayout指定布局类型
     */
    abstract fun getLayoutHelper(): LayoutHelper

    /**
     * 绑定数据
     */
    abstract fun bindView(binding: D, position: Int)

    /**
     * BaseViewHolder内部类
     */
    inner class BaseViewHolder(binding: D) : RecyclerView.ViewHolder(binding.root) {
        var binding = binding
    }
}
