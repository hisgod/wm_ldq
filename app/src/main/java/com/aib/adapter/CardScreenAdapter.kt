package com.aib.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wm.loan.R
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper

/**
 * 帖子列表-筛选功能
 */

//class CardScreenAdapter : DelegateAdapter.Adapter<CardScreenAdapter.ViewHolder>() {
//    private lateinit var ctx: Context
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        ctx = parent.context
//        LayoutInflater.from(ctx).inflate(R.layout.item_card_screen,parent,false)
//    }
//
//    override fun getItemCount(): Int = 1
//
//    override fun onCreateLayoutHelper(): LayoutHelper = SingleLayoutHelper()
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//    }
//}