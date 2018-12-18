package com.aib.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/**
 * ListView万能适配器
 */
abstract class BaseLvAdapter<D : ViewDataBinding> : BaseAdapter() {
    lateinit var ctx: Context
    lateinit var binding: D
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        ctx = parent!!.context

        var view = convertView

        if (hasParent()) {
            binding = DataBindingUtil.inflate<D>(LayoutInflater.from(ctx), getResId(), parent, false)
        } else {
            binding = DataBindingUtil.inflate<D>(LayoutInflater.from(ctx), getResId(), null, false)
        }

        var vh = BaseViewHolder(binding)
        if (convertView == null) {
            view = binding.root
            view.tag = vh
        } else {
            vh = view!!.tag as BaseLvAdapter<D>.BaseViewHolder
        }

        bindView(vh.binding, position)

        return view
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int = getItemCount()

    inner class BaseViewHolder(var binding: D)

    abstract fun getResId(): Int

    abstract fun hasParent(): Boolean

    abstract fun getItemCount(): Int

    abstract fun bindView(binding: D, position: Int)

}