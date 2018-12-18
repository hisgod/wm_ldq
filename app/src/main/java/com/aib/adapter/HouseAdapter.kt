package com.aib.adapter

import android.databinding.DataBindingUtil
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import java.util.LinkedHashMap

import com.wm.loan.R
import com.wm.loan.databinding.ItemHouseFootBinding

class HouseAdapter : BaseAdapter() {

    private var months: List<Int>? = null
    private var moneys: List<String>? = null

    override fun getCount(): Int {
        return if (moneys == null && months == null) 0 else moneys!!.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val binding = DataBindingUtil.inflate<ItemHouseFootBinding>(LayoutInflater.from(parent.context), R.layout.item_house_foot, parent, false)
        var vh = ViewHolder(binding)
        if (convertView == null) {
            convertView = binding.root
            convertView.tag = vh
        } else {
            vh = convertView.tag as ViewHolder
        }

        vh.binding.entity = months!![position]
        vh.binding.value = moneys!![position]
        return convertView
    }

    fun data(months: List<Int>, moneys: List<String>) {
        this.months = months
        this.moneys = moneys
    }

    inner class ViewHolder(val binding: ItemHouseFootBinding)
}
