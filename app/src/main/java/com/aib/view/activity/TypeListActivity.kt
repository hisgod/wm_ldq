package com.aib.view.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View

import com.aib.adapter.HomeTypeAdapter
import com.aib.entity.TypeLoanEntity
import com.aib.net.Status
import com.aib.viewmodel.TypeListVm
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.bumptech.glide.Glide
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.wm.loan.R
import com.wm.loan.databinding.ActivityTypeListBinding
import java.util.ArrayList

/**
 * 分类列表
 */
class TypeListActivity : BaseActivity<ActivityTypeListBinding>() {
    private lateinit var vm: TypeListVm
    var typeTitle: String? = null
    var bgUrl: String? = null
    var bgIcon: String? = null
    var des: String? = null
    var typeId: Int? = null
    var page: Int = 1
    var homeTypeAdapter: HomeTypeAdapter? = null
    var data: ArrayList<TypeLoanEntity> = ArrayList()
    var isFirst = true

    override fun getResId(): Int = R.layout.activity_type_list

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        vm = getViewModel(TypeListVm::class.java)

        binding.presenter = Presenter()

        typeTitle = intent.extras.getString("title")
        typeId = intent.extras.getInt("typeId")
        bgUrl = intent.extras.getString("bgUrl")
        bgIcon = intent.extras.getString("bgIcon")
        des = intent.extras.getString("des2")
        binding.tvTitle.text = typeTitle
        Glide.with(this).load(bgIcon).into(binding.ivIcon)
        binding.tvDes2.text = des
        binding.tvDes1.text = typeTitle

        Glide.with(this)
                .load(bgUrl)
                .into(binding.iv)

        val pool = RecyclerView.RecycledViewPool()
        pool.setMaxRecycledViews(0, 10)
        binding.rv.setRecycledViewPool(pool)
        val manager = VirtualLayoutManager(applicationContext)
        binding.rv.layoutManager = manager
        val adapter = DelegateAdapter(manager)
        homeTypeAdapter = HomeTypeAdapter()
        adapter.addAdapter(homeTypeAdapter)
        binding.rv.adapter = adapter

        getTypeListJson(true)

        binding.srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                ++page
                getTypeListJson(false)
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                data.clear()
                page = 1
                getTypeListJson(false)
            }
        })
    }

    /**
     * 获取Json数据
     */
    fun getTypeListJson(isFirstRefresh: Boolean) {
        vm.queryLoanListByKey(typeId!!, page, 10, isFirstRefresh).observe(this, Observer {
            binding.resource = it
            if (it!!.status == Status.SUCCESS) {
                if (it.data!!.code == 1) {

                    data.addAll(it.data.data)
                    homeTypeAdapter!!.setData(data)
                    homeTypeAdapter!!.notifyDataSetChanged()

                    if (binding.srl.isRefreshing) {
                        binding.srl.finishRefresh()
                    }

                    if (binding.srl.isLoading) {
                        binding.srl.finishLoadMore()
                    }

                    if (it.data.data.size == 0) {
                        binding.srl.setNoMoreData(true)
                    } else {
                        binding.srl.setNoMoreData(false)
                    }

                    if (isFirst) {
                        binding.apl.setExpanded(true)
                    }

                    isFirst = false

                } else {

                }
            }
        })
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }
    }
}
