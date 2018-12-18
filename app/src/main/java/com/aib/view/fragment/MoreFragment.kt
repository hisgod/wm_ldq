package com.aib.view.fragment

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.util.ArrayMap
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView

import com.aib.adapter.LoanListAdapter
import com.aib.utils.Constants
import com.aib.entity.MoreLoanEntity
import com.aib.entity.ChoiceMoneyRangeEntity
import com.aib.viewmodel.MoreFragmentVm
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ConvertUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

import java.util.ArrayList

import com.wm.loan.R
import com.wm.loan.databinding.FragmentMoreBinding
import com.wm.loan.databinding.WindowSortBinding

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.aib.utils.RetryCallback
import com.aib.net.Status

/**
 * 更多口子
 */
class MoreFragment : BaseFragment<FragmentMoreBinding>() {
    private var page = 1
    private val data = ArrayList<MoreLoanEntity>()
    private var adapter: LoanListAdapter? = null
    private var paramsTag: String? = null
    private lateinit var vm: MoreFragmentVm

    override fun getResId(): Int = R.layout.fragment_more

    override fun initData(savedInstanceState: Bundle?) {

        binding.tvTitle.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        vm = getViewModel(MoreFragmentVm::class.java)

        binding.presenter = Presenter()
        binding.retry = object : RetryCallback {
            override fun retryClick() {
                getZHSort(page, false)
            }
        }

        binding.tvTitle.text = arguments!!.getString(Constants.TITLE_TEXT)

        binding.rg.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb1 -> binding.rb1.isChecked = true
                R.id.rb2 -> binding.rb2.isChecked = true
                R.id.rb3 -> binding.rb3.isChecked = true
            }
        }

        val pool = RecyclerView.RecycledViewPool()
        pool.setMaxRecycledViews(0, 20)
        val manager = VirtualLayoutManager(activity!!.applicationContext)
        binding.rv.layoutManager = manager
        binding.rv.setRecycledViewPool(pool)
        val delegateAdapter = DelegateAdapter(manager, false)
        adapter = LoanListAdapter()
        delegateAdapter.addAdapter(adapter)
        binding.rv.adapter = delegateAdapter

        binding.srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getZHSort(++page, false)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 0
                data.clear()
                getZHSort(++page, false)
            }
        })

        /**
         * 获取综合搜索列表
         */
        getZHSort(page, true)
    }

    /**
     * 查询贷款列表
     *
     * @param params
     */
    fun queryLoanList(params: ArrayMap<String, Any>) {
        vm.queryProduct(params).observe(activity!!, Observer { arrayListBaseEntity ->
            if (arrayListBaseEntity!!.code == 1) {
                data.clear()
                data.addAll(arrayListBaseEntity.data)
                adapter!!.setData(data)
                adapter!!.notifyDataSetChanged()
            }
        })
    }

    /**
     * 获取综合排序数据
     */
    private fun getZHSort(page: Int, isFirst: Boolean) {
        vm.getDefaultJson(page, 10, isFirst).observe(activity!!, Observer {
            binding.resource = it
            if (it?.status == Status.SUCCESS) {
                if (it.data?.code == 1) {
                    data.addAll(it.data.data)
                    adapter!!.setData(data)
                    adapter!!.notifyDataSetChanged()

                    if (binding.srl.isRefreshing) {
                        binding.srl.finishRefresh(true)
                    }

                    if (binding.srl.isLoading) {
                        binding.srl.finishLoadMore(true)
                    }

                    if (it.data.data.size == 0) {
                        binding.srl.setNoMoreData(true)
                    } else {
                        binding.srl.setNoMoreData(false)
                    }

                }
            }
        })
    }

    inner class Presenter {
        /**
         * 第一个筛选按钮
         *
         * @param view
         */
        fun sort1(view: View) {
            binding.rb1.isSelected = true
            val windowSortBinding = DataBindingUtil.inflate<WindowSortBinding>(LayoutInflater.from(activity!!.applicationContext), R.layout.window_sort, null, false)
            val pw = PopupWindow(windowSortBinding.root, MATCH_PARENT, WRAP_CONTENT, true)
            binding.viewBg.visibility = View.VISIBLE
            pw.showAsDropDown(view)
            pw.setOnDismissListener {
                binding.viewBg.visibility = View.GONE
                binding.rb1.isSelected = false
            }
            windowSortBinding.tv1.setOnClickListener {
                binding.rb1.text = windowSortBinding.tv1.text.toString().trim { it <= ' ' }
                pw.dismiss()
                binding.rb1.isSelected = false

                page = 0
                data.clear()
                getZHSort(++page, false)
            }
            windowSortBinding.tv2.setOnClickListener {
                binding.rb1.text = windowSortBinding.tv2.text.toString().trim { it <= ' ' }
                pw.dismiss()
                binding.rb1.isSelected = false

                page = 0
                data.clear()
                val params = ArrayMap<String, Any>()
                params["loan_FromHighToLow"] = 1
                params["pageNum"] = ++page
                params["pageSize"] = 10
                queryLoanList(params)
            }
            windowSortBinding.tv3.setOnClickListener {
                binding.rb1.text = windowSortBinding.tv3.text.toString().trim { it <= ' ' }
                pw.dismiss()
                binding.rb1.isSelected = false

                page = 0
                data.clear()
                val params = ArrayMap<String, Any>()
                params["loan_FromLowToHigh"] = 1
                params["pageNum"] = ++page
                params["pageSize"] = 10
                queryLoanList(params)
            }
        }

        /**
         * 筛选第二个按钮
         */
        fun sort2(view: View) {
            page = 0
            data.clear()
            val params = ArrayMap<String, Any>()
            params["loanRate"] = 1
            params["pageNum"] = ++page
            params["pageSize"] = 10
            queryLoanList(params)
        }

        /**
         * 筛选第三个按钮
         *
         * @param view
         */
        fun sort3(view: View) {
            val view_choice = View.inflate(context, R.layout.window_choice, null)
            val fl_tag = view_choice.findViewById<TagFlowLayout>(R.id.fl_tag)
            val fl_money = view_choice.findViewById<TagFlowLayout>(R.id.fl_money)
            val et_low = view_choice.findViewById<EditText>(R.id.et_low)
            val et_heigh = view_choice.findViewById<EditText>(R.id.et_heigh)
            val btn_reset = view_choice.findViewById<Button>(R.id.btn_reset)
            val btn_sure = view_choice.findViewById<Button>(R.id.btn_sure)
            val pw_choice = PopupWindow(view_choice, MATCH_PARENT, ConvertUtils.dp2px(300f), true)
            pw_choice.showAsDropDown(view)
            binding.viewBg.visibility = View.VISIBLE
            pw_choice.setOnDismissListener { binding.viewBg.visibility = View.GONE }

            //
            val choice_money_range = ArrayList<ChoiceMoneyRangeEntity>()
            choice_money_range.add(ChoiceMoneyRangeEntity("1000-3000", "30%人选择"))
            choice_money_range.add(ChoiceMoneyRangeEntity("3000-5000", "25%人选择"))
            choice_money_range.add(ChoiceMoneyRangeEntity("5000-9000", "20%人选择"))

            fl_money.adapter = object : TagAdapter<ChoiceMoneyRangeEntity>(choice_money_range) {
                override fun getView(parent: FlowLayout, position: Int, choiceMoneyRangeEntity: ChoiceMoneyRangeEntity): View {
                    val view = View.inflate(parent.context, R.layout.item_choice_money_range, null)
                    val tv_up = view.findViewById<TextView>(R.id.tv_up)
                    val tv_down = view.findViewById<TextView>(R.id.tv_down)
                    tv_up.text = choiceMoneyRangeEntity.tvUp
                    tv_down.text = choiceMoneyRangeEntity.tvDown
                    return view
                }

                override fun onSelected(position: Int, view: View?) {
                    super.onSelected(position, view)
                    val tv_up = view!!.findViewById<TextView>(R.id.tv_up)
                    val tv_down = view.findViewById<TextView>(R.id.tv_down)
                    tv_up.setTextColor(resources.getColor(android.R.color.white))
                    tv_down.setTextColor(resources.getColor(android.R.color.white))
                    when (position) {
                        0 -> {
                            et_low.setText(1000.toString() + "")
                            et_heigh.setText(3000.toString() + "")
                        }
                        1 -> {
                            et_low.setText(3000.toString() + "")
                            et_heigh.setText(5000.toString() + "")
                        }
                        2 -> {
                            et_low.setText(5000.toString() + "")
                            et_heigh.setText(9000.toString() + "")
                        }
                    }
                }

                override fun unSelected(position: Int, view: View?) {
                    super.unSelected(position, view)
                    val tv_up = view!!.findViewById<TextView>(R.id.tv_up)
                    val tv_down = view.findViewById<TextView>(R.id.tv_down)
                    tv_up.setTextColor(resources.getColor(android.R.color.black))
                    tv_down.setTextColor(resources.getColor(android.R.color.black))
                    when (position) {
                        0 -> {
                            et_low.setText("")
                            et_heigh.setText("")
                        }
                        1 -> {
                            et_low.setText("")
                            et_heigh.setText("")
                        }
                        2 -> {
                            et_low.setText("")
                            et_heigh.setText("")
                        }
                    }
                }
            }

            //获取分类列表
            vm.typeList.observe(activity!!, Observer { arrayListBaseEntity ->
                if (arrayListBaseEntity!!.code == 1) {
                    val strs = ArrayList<String>()
                    for (typeEntity in arrayListBaseEntity.data) {
                        strs.add(typeEntity.tagName!!)
                    }
                    fl_tag.adapter = object : TagAdapter<String>(strs) {
                        override fun getView(parent: FlowLayout, position: Int, s: String): View {
                            val view = View.inflate(parent.context, R.layout.item_textview, null)
                            val tv = view.findViewById<TextView>(R.id.tv)
                            tv.text = s
                            return tv
                        }

                        override fun onSelected(position: Int, view: View?) {
                            super.onSelected(position, view)
                            val tv = view as TextView?
                            val getTag = tv!!.text.toString().trim { it <= ' ' }
                            paramsTag = getTag
                        }
                    }
                }
            })

            /**
             * 重置
             */
            btn_reset.setOnClickListener {
                fl_money.adapter.setSelectedList()
                fl_tag.adapter.setSelectedList()

                et_low.setText("")
                et_heigh.setText("")
            }

            /**
             * 确定
             */
            btn_sure.setOnClickListener {
                val getLow = et_low.text.toString().trim { it <= ' ' }
                val getHeigh = et_heigh.text.toString().trim { it <= ' ' }

                if (TextUtils.isEmpty(getLow) || TextUtils.isEmpty(getHeigh) || TextUtils.isEmpty(paramsTag)) {
                    page = 0
                    val params = ArrayMap<String, Any>()
                    params["loanMax"] = getHeigh
                    params["loanMin"] = getLow
                    params["tagKeyword"] = paramsTag
                    params["pageNum"] = ++page
                    params["pageSize"] = 10
                    vm.queryProduct(params).observe(activity!!, Observer { arrayListBaseEntity ->
                        if (arrayListBaseEntity!!.code == 1) {
                            data.clear()
                            data.addAll(arrayListBaseEntity.data)
                            adapter!!.setData(data)
                            adapter!!.notifyDataSetChanged()
                            pw_choice.dismiss()
                        }
                    })
                } else {
                    pw_choice.dismiss()
                }
            }
        }
    }
}