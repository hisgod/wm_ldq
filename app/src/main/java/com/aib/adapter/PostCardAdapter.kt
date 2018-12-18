package com.aib.adapter

import android.view.View
import com.wm.loan.R
import com.bumptech.glide.Glide
import com.wm.loan.databinding.ItemAddImgBinding
import java.util.ArrayList

class PostCardAdapter : BaseRvAdapter<ItemAddImgBinding>() {
    private lateinit var data: ArrayList<Any>
    private lateinit var onItemClickListener: OnItemClickListener

    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_add_img

    override fun getCount(): Int {
        if (data == null) {
            return 0
        } else {
            return data.size
        }
    }

    override fun bindView(binding: ItemAddImgBinding, position: Int) {
        if (position == data.size - 1 && data.get(data.size - 1) == R.drawable.ic_add_img) {
            binding.ivDelete.visibility = View.GONE
        } else {
            binding.ivDelete.visibility = View.VISIBLE
        }

        Glide.with(ctx!!).load(data.get(position)).into(binding.iv)

        if (onItemClickListener != null) {
            binding.iv.setOnClickListener {
                onItemClickListener.itemClick(position)
            }

            binding.ivDelete.setOnClickListener {
                onItemClickListener.itemDelete(position)
            }
        }
    }

    fun passData(data: ArrayList<Any>) {
        this.data = data
    }

    abstract class OnItemClickListener {
        abstract fun itemClick(position: Int)

        abstract fun itemDelete(position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}