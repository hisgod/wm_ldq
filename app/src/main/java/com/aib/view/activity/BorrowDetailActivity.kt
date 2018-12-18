package com.aib.view.activity

import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wm.loan.R


/**
 * 借款详情
 */
class BorrowDetailActivity : BaseActivity<ViewDataBinding>() {

    override fun getResId(): Int = R.layout.activity_borrow_detail

    override fun initData(savedInstanceState: Bundle?) {
    }
}
