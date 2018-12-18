package com.aib.view.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View

import com.aib.adapter.MsgCenterAdapter
import com.aib.entity.MsgCenterEntity
import com.aib.viewmodel.MsgCenterViewModel
import com.blankj.utilcode.util.BarUtils
import com.wm.loan.R
import com.wm.loan.databinding.ActivityMsgCenterBinding

import java.util.ArrayList


/**
 * 消息中心
 */
class MsgCenterActivity : BaseActivity<ActivityMsgCenterBinding>() {

    private var vm: MsgCenterViewModel? = null
    private val page: Int = 0
    private val data = ArrayList<MsgCenterEntity.ListBean>()

    override fun getResId(): Int = R.layout.activity_msg_center

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        vm = getViewModel(MsgCenterViewModel::class.java)

        binding.presenter = Presenter()

        binding.rv.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = MsgCenterAdapter()
        binding.rv.adapter = adapter

        getMsgJson()
    }

    /**
     * 获取消息Json
     */
    private fun getMsgJson() {
        //        viewModel.getMsgList(SPUtils.getInstance().getString(Constants.TOKEN), ++page, 10).observe(this, new Observer<Resource<BaseEntity<MsgCenterEntity>>>() {
        //            @Override
        //            public void onChanged(@Nullable Resource<BaseEntity<MsgCenterEntity>> baseEntityResource) {
        //
        //            }
        //        });
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }
    }
}
