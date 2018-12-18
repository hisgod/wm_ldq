package com.aib.adapter

import com.aib.entity.AccountEntity
import com.wm.loan.R
import com.wm.loan.databinding.ItemAccountListBinding

class AccountListAdapter : BaseRvAdapter<ItemAccountListBinding>() {
    private var it: List<AccountEntity>? = null

    private var listener: AccountListAdapter.OnItemClickListener? = null

    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_account_list

    override fun getCount(): Int = if (it == null) 0 else it!!.size

    override fun bindView(binding: ItemAccountListBinding, position: Int) {
        binding.entity = it!!.get(position)
        binding.executePendingBindings()

        if (listener != null) {
            binding.root.setOnClickListener {
                listener!!.onItemClick(position, this.it!!.get(position))
            }
        }
    }

    fun passData(it: List<AccountEntity>?) {
        this.it = it
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: AccountEntity)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}