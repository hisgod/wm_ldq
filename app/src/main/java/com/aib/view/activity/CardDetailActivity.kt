package com.aib.view.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING
import android.text.TextUtils
import android.view.View

import com.aib.adapter.CardContentAdapter
import com.aib.adapter.CardDetailImgAdapter
import com.aib.adapter.CommentAdapter
import com.aib.adapter.CommentTitleAdapter
import com.aib.utils.Constants
import com.aib.entity.CommentEntity
import com.aib.net.Status
import com.aib.viewmodel.CardDetailVm
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.blankj.utilcode.util.*
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.wm.loan.R
import com.wm.loan.databinding.ActivityCardDetailBinding
import java.util.ArrayList

/**
 * 帖子详情
 */
class CardDetailActivity : BaseActivity<ActivityCardDetailBinding>() {
    private lateinit var vm: CardDetailVm
    private var id: Int? = null
    private lateinit var cardContentAdapter: CardContentAdapter
    private lateinit var cardDetailImgAdapter: CardDetailImgAdapter
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var commentTitleAdapter: CommentTitleAdapter
    private var commentData = ArrayList<CommentEntity>()    //评论列表数据
    private var pageNum: Int = 1

    override fun getResId(): Int = R.layout.activity_card_detail

    override fun initData(savedInstanceState: Bundle?) {
        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        binding.presenter = Presenter()

        vm = getViewModel(CardDetailVm::class.java)

        id = intent.extras.getInt("id")

        //Rv设置
        val manager = VirtualLayoutManager(this)
        val pool = RecyclerView.RecycledViewPool()
        pool.setMaxRecycledViews(0, 20)
        binding.rv.layoutManager = manager
        binding.rv.setRecycledViewPool(pool)
        val adapter = DelegateAdapter(manager)
        cardContentAdapter = CardContentAdapter()
        cardDetailImgAdapter = CardDetailImgAdapter()
        commentTitleAdapter = CommentTitleAdapter()
        commentAdapter = CommentAdapter()
        adapter.addAdapter(cardContentAdapter)
        adapter.addAdapter(cardDetailImgAdapter)
        adapter.addAdapter(commentTitleAdapter)
        adapter.addAdapter(commentAdapter)
        binding.rv.adapter = adapter

        /**
         * RV滑动事件
         */
        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_DRAGGING) {
                    binding.llInputPanel.visibility = View.GONE
                    binding.llShowPanel.visibility = View.VISIBLE
                    KeyboardUtils.hideSoftInput(binding.etContent)
                }
            }
        })

        vm.getCardDetail(id!!, true).observe(this, Observer {
            binding.resource = it
            if (it!!.status == Status.SUCCESS) {
                cardContentAdapter.passData(it.data!!.data)
                cardContentAdapter.notifyDataSetChanged()

                cardDetailImgAdapter.passData(it.data.data)
                cardDetailImgAdapter.notifyDataSetChanged()
            }
        })

        showCommentList(1)

        /**
         * 刷新，加载更多
         */
        binding.srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                showCommentList(++pageNum)
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                commentData.clear()
                showCommentList(1)
            }
        })
    }

    /**
     * 显示评论列表
     */
    private fun showCommentList(pageNum: Int) {
        vm.lookCardComment(pageNum, 10, id!!).observe(this, Observer {
            if (it!!.code == 1) {

                if (binding.srl.isRefreshing) {
                    binding.srl.finishRefresh(true)
                }

                if (binding.srl.isLoading) {
                    binding.srl.finishLoadMore(true)
                }

                if (it.data.size == 0) {
                    binding.srl.setNoMoreData(true)
                } else {
                    binding.srl.setNoMoreData(false)
                }

                commentData.addAll(it.data)
                commentAdapter.passData(commentData)
                commentAdapter.notifyDataSetChanged()
            } else {
                ToastUtils.showShort(it.msg)
            }
        })
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }

        /**
         * 展示编辑器
         */
        fun showEdit(view: View) {
            if (binding.llShowPanel.visibility == View.VISIBLE) {
                binding.llShowPanel.visibility = View.GONE
                binding.llInputPanel.visibility = View.VISIBLE
                KeyboardUtils.showSoftInput(binding.etContent)
            } else {
                binding.llShowPanel.visibility = View.VISIBLE
                binding.llInputPanel.visibility = View.GONE
            }
        }

        /**
         * 取消评论
         */
        fun cancelComment(view: View) {
            binding.llInputPanel.visibility = View.GONE
            binding.llShowPanel.visibility = View.VISIBLE
            KeyboardUtils.hideSoftInput(binding.etContent)
        }

        /**
         * 发表评论
         *先检测用户是否登录
         */
        fun sendComment(view: View) {
            ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<String>() {
                override fun onSuccess(result: String?) {
                    if (result == "") {
                        ActivityUtils.startActivity(LoginActivity::class.java)
                    } else {
                        val getContent = binding.etContent.text.toString().trim()

                        if (TextUtils.isEmpty(getContent)) {
                            ToastUtils.showShort("内容为空")
                            return
                        }

                        if (getContent.length in 50..100) {
                            ToastUtils.showShort("字数不达标")
                            return
                        }

                        vm.sendComment(getContent, id!!, SPUtils.getInstance().getString(Constants.TOKEN)).observe(this@CardDetailActivity, Observer {
                            if (it!!.code == 1) {
                                binding.llInputPanel.visibility = View.GONE
                                binding.llShowPanel.visibility = View.VISIBLE
                                KeyboardUtils.hideSoftInput(binding.etContent)
                                binding.etContent.text.clear()
                                binding.srl.autoRefresh()
                                binding.rv.scrollToPosition(2)
                            } else {
                                ToastUtils.showShort(it.msg)
                            }
                        })
                    }
                }

                override fun doInBackground(): String? {
                    return SPUtils.getInstance().getString(Constants.TOKEN)
                }
            })
        }
    }
}
