package com.aib.view.fragment

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.util.ArrayMap
import android.support.v7.widget.ListPopupWindow
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow.WRAP_CONTENT
import com.wm.loan.R
import com.wm.loan.databinding.FragmentCardBinding
import com.aib.adapter.CardListAdapter
import com.aib.utils.Constants
import com.aib.entity.CardListEntity
import com.aib.net.Status
import com.aib.view.activity.LoginActivity
import com.aib.view.activity.PostCardActivity
import com.aib.viewmodel.CardFragmentVm
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.blankj.utilcode.util.*
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import java.util.ArrayList

/**
 * 帖子模块
 */
class CardFragment : BaseFragment<FragmentCardBinding>() {
    private lateinit var vm: CardFragmentVm

    private lateinit var cardListAdapter: CardListAdapter

    private var pageNum: Int = 1

    private var data = ArrayList<CardListEntity>()

    private var tagTitle = "默认"

    override fun getResId(): Int = R.layout.fragment_card

    override fun initData(savedInstanceState: Bundle?) {
        //标题
        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        if (arguments != null) {
            val title = arguments!!.getString(Constants.TITLE_TEXT)
            binding.tvTitle.text = title
        } else {
            binding.tvTitle.text = "交流区"
        }

        vm = getViewModel(CardFragmentVm::class.java)

        binding.presenter = Presenter()

        //Rv设置
        val manager = VirtualLayoutManager(activity!!)
        var pool = RecyclerView.RecycledViewPool()
        pool.setMaxRecycledViews(0, 20)
        binding.rv.layoutManager = manager
        binding.rv.setRecycledViewPool(pool)
        val adapter = DelegateAdapter(manager)
        cardListAdapter = CardListAdapter()
        adapter.addAdapter(cardListAdapter)
        binding.rv.adapter = adapter

        getCardListJson(pageNum, tagTitle, true)

        /**
         * 刷新
         */
        binding.srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                getCardListJson(++pageNum, tagTitle, false)
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                data.clear()
                pageNum = 1
                when (tagTitle) {
                    "默认" -> getCardListJson(pageNum, tagTitle, false)
                    else -> getCardListJson(pageNum, tagTitle, false)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            binding.srl.autoRefresh()
        }
    }

    /**
     *获取帖子列表数据
     */
    private fun getCardListJson(pageNum: Int, type: String?, isFirst: Boolean) {
        val params = ArrayMap<String, Any>()
        when (type) {
            "默认" -> params.put("isCreateTimeOrderBy_desc", 1)
            else -> params.put("isCommentnumOrderBy_desc", 1)
        }
        params.put("pageNum", pageNum)
        params.put("pageSize", 10)
        vm.getCardList(params, isFirst).observe(this, Observer {
            binding.resource = it
            if (it!!.status == Status.SUCCESS) {
                if (binding.srl.isRefreshing) {
                    binding.srl.finishRefresh(true)
                }

                if (binding.srl.isLoading) {
                    binding.srl.finishLoadMore(true)
                }

                if (it.data!!.data.size == 0) {
                    binding.srl.setNoMoreData(true)
                } else {
                    binding.srl.setNoMoreData(false)
                }

                data.addAll(it.data.data)

                cardListAdapter.passData(data)
                cardListAdapter.notifyDataSetChanged()
            }
        })
    }

    inner class Presenter {
        /**
         * 显示筛选列表
         */
        fun tagScreen(view: View) {
            val screenData = ArrayList<String>()
            screenData.add("默认")
            screenData.add("评论数")
            val listPopupWindow = ListPopupWindow(activity!!)
            listPopupWindow.width = ConvertUtils.dp2px(100f)
            listPopupWindow.height = WRAP_CONTENT
            listPopupWindow.anchorView = binding.tb
            listPopupWindow.setDropDownGravity(Gravity.RIGHT)
            listPopupWindow.setAdapter(ArrayAdapter<String>(activity!!, android.R.layout.simple_list_item_1, screenData))
            listPopupWindow.isModal = false
            listPopupWindow.setOnItemClickListener { parent, view, position, id ->
                binding.tvScreen.text = screenData[position]
                listPopupWindow.dismiss()

                tagTitle = screenData[position]

                binding.srl.autoRefresh()

                binding.rv.scrollToPosition(0)
            }
            listPopupWindow.show()
        }

        /**
         * 发帖
         */
        fun post(view: View) {
            ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<String>() {
                override fun onSuccess(result: String?) {
                    if (result == "") {
                        ActivityUtils.startActivity(LoginActivity::class.java)
                    } else {
                        startActivityForResult(Intent(activity!!, PostCardActivity::class.java), 0)
                    }
                }

                override fun doInBackground(): String? {
                    return SPUtils.getInstance().getString(Constants.TOKEN)
                }
            })
        }
    }
}