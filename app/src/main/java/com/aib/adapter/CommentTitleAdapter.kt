package com.aib.adapter

import com.wm.loan.R
import com.wm.loan.databinding.ItemCommentTitleBinding
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.StickyLayoutHelper

/**
 * 评论标题
 */
class CommentTitleAdapter : BaseDelegateAdapter<ItemCommentTitleBinding>() {
    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_comment_title

    override fun getCount(): Int = 1

    override fun getLayoutHelper(): LayoutHelper = StickyLayoutHelper()

    override fun bindView(binding: ItemCommentTitleBinding, position: Int) {
    }
}