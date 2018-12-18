package com.aib.adapter

import com.wm.loan.R
import com.wm.loan.databinding.ItemCommentBinding
import com.aib.entity.CommentEntity
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import java.util.ArrayList

/**
 * 帖子评论区
 */
class CommentAdapter : BaseDelegateAdapter<ItemCommentBinding>() {
    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_comment

    override fun getCount(): Int = if (data == null) 0 else data!!.size

    override fun getLayoutHelper(): LayoutHelper = LinearLayoutHelper()

    override fun bindView(binding: ItemCommentBinding, position: Int) {
        binding.entity = data!!.get(position)
        binding.executePendingBindings()
    }

    private var data: ArrayList<CommentEntity>? = null

    fun passData(data: ArrayList<CommentEntity>?) {
        this.data = data
    }
}