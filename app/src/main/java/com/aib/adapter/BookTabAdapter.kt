package com.aib.adapter

import android.databinding.ViewDataBinding
import com.aib.entity.AccountEntity
import com.aib.entity.MarkEntity
import com.wm.loan.R
import com.wm.loan.databinding.ItemBookTabBinding

class BookTabAdapter : BaseRvAdapter<ItemBookTabBinding>() {
    private var it1: List<MarkEntity>? = null
    private var code: Int? = null
    private var it2: List<AccountEntity>? = null


    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_book_tab

    override fun getCount(): Int {
        if (code == 0) {
            return if (it1 == null) 0 else it1!!.size
        } else {
            return if (it2 == null) 0 else it2!!.size
        }
    }

    override fun bindView(binding: ItemBookTabBinding, position: Int) {
        when (code) {
            0 -> {
                val entity = it1!!.get(position)
                binding.tv1.text = entity.date
                binding.tv2.text = "金额(元)"
                binding.tv3.text = entity.type
                binding.tv4.text = entity.money
            }
            1 -> {
                val entity = it2!!.get(position)
                binding.tv1.text = entity.accountName
                binding.tv2.text = "金额(元)"
                binding.tv3.text = entity.cardId
                binding.tv4.text = entity.money
            }
        }
    }

    fun passFragment1Data(code: Int, it: List<MarkEntity>?) {
        this.code = code
        this.it1 = it
    }

    fun passFragment2Data(i: Int, it: List<AccountEntity>?) {
        this.code = i
        this.it2 = it
    }
}