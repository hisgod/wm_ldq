package com.aib.adapter

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.aib.utils.Extensions

abstract class BaseRvAdapter<D : ViewDataBinding> : RecyclerView.Adapter<BaseRvAdapter<D>.BaseViewHolder>(), Extensions {
    private lateinit var binding: D
    var ctx: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        ctx = parent.context
        binding = parent.createBinding(getResId(), attachToParent())
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return getCount()
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
