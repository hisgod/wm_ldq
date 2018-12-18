package com.aib.view.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View

import com.aib.adapter.ApplyStatusAdapter
import com.aib.utils.Constants
import com.aib.net.Status
import com.aib.viewmodel.ApplyStatusVm
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.wm.loan.R
import com.wm.loan.databinding.ActivityApplyStatusBinding

/**
 * 查看审核状态
 *
 * 配合老版本OldCertainFragment申请贷款页面，旧业务代码
 */
class ApplyStatusActivity : BaseActivity<ActivityApplyStatusBinding>() {

    private var applyStatusAdapter: ApplyStatusAdapter? = null
    private var vm: ApplyStatusVm? = null

    override fun getResId(): Int = R.layout.activity_apply_status


    override fun initData(savedInstanceState: Bundle?) {
        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        vm = getViewModel(ApplyStatusVm::class.java)

        binding.presenter = Presenter()

        val pool = RecyclerView.RecycledViewPool()
        pool.setMaxRecycledViews(0, 10)
        binding.rv.setRecycledViewPool(pool)
        val manager = VirtualLayoutManager(applicationContext)
        binding.rv.layoutManager = manager
        val adapter = DelegateAdapter(manager)
        applyStatusAdapter = ApplyStatusAdapter()
        adapter.addAdapter(applyStatusAdapter)
        binding.rv.adapter = adapter

        //查询申请状态
        ThreadUtils.getIoPool().submit(object : ThreadUtils.Task<String>() {
            @Throws(Throwable::class)
            override fun doInBackground(): String? {
                return SPUtils.getInstance().getString(Constants.TOKEN)
            }

            override fun onSuccess(result: String?) {
                if (TextUtils.isEmpty(result)) {
                    ToastUtils.showShort("token为空")
                } else {
                    queryApplyStatus(result!!)
                }
            }

            override fun onCancel() {

            }

            override fun onFail(t: Throwable) {

            }
        })


        /**
         * 获取广告
         */
        vm!!.getApplyInfo().observe(this, Observer {
            binding.resource = it
            if (it!!.status == Status.SUCCESS) {
                applyStatusAdapter!!.passData(it.data!!.data);
                applyStatusAdapter!!.notifyDataSetChanged();
            }
        })
    }

    /**
     * 查询个人状态
     *
     * @param result
     */
    private fun queryApplyStatus(result: String) {
        vm!!.isApply(result).observe(this@ApplyStatusActivity, Observer { applyStatusEntityBaseEntity ->
            if (applyStatusEntityBaseEntity!!.code == 1) {
                binding.entity = applyStatusEntityBaseEntity.data
            } else {
                ToastUtils.showShort("获取数据失败")
            }
        })
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }
    }
}
