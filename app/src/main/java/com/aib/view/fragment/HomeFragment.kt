package com.aib.view.fragment

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.ImageView

import com.aib.adapter.RecNotifyAdapter
import com.aib.adapter.RecNowAdapter
import com.aib.adapter.RecProductAdapter
import com.aib.adapter.RecTipAdapter
import com.aib.adapter.RecTypeAdapter
import com.aib.utils.RetryCallback
import com.aib.utils.Constants
import com.aib.entity.HomeEntity
import com.aib.net.Resource
import com.aib.net.Status
import com.aib.view.activity.LoanDetailActivity
import com.aib.view.activity.WebViewActivity
import com.aib.viewmodel.HomeViewModel
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.bumptech.glide.Glide
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader

import java.util.ArrayList

import com.wm.loan.R
import com.wm.loan.databinding.FragmentHomeBinding

/**
 * 首页
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val typeData = ArrayList<HomeEntity.AppHomeCategoryListBean>()
    private val productData = ArrayList<HomeEntity.AppHomeProdlistListBean>()
    private var recTypeAdapter: RecTypeAdapter? = null
    private lateinit var recProductAdapter: RecProductAdapter
    private var recNowAdapter: RecNowAdapter? = null
    private lateinit var vm: HomeViewModel

    override fun getResId(): Int = R.layout.fragment_home

    override fun initData(savedInstanceState: Bundle?) {
        vm = getViewModel(HomeViewModel::class.java)

        binding.tvTitle.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        //标题栏
        val getTitle = arguments!!.getString(Constants.TITLE_TEXT)
        if (TextUtils.isEmpty(getTitle)) {
            binding.tvTitle.text = "标题"
        } else {
            binding.tvTitle.text = getTitle
        }

        val pool = RecyclerView.RecycledViewPool()
        pool.setMaxRecycledViews(0, 20)
        binding.rv.setRecycledViewPool(pool)
        val manager = VirtualLayoutManager(ctx)
        binding.rv.layoutManager = manager

        val recAdAdapter = RecNotifyAdapter() //通知
        recTypeAdapter = RecTypeAdapter()  //分类
        recNowAdapter = RecNowAdapter()    //今日推荐
        recProductAdapter = RecProductAdapter()    //贷款列表
        val recTipAdapter = RecTipAdapter()

        val adapter = DelegateAdapter(manager)
        adapter.addAdapter(recAdAdapter)
        adapter.addAdapter(recTypeAdapter)
        adapter.addAdapter(recNowAdapter)
        adapter.addAdapter(recTipAdapter)
        adapter.addAdapter(recProductAdapter)
        binding.rv.adapter = adapter

        binding.srl.setOnRefreshListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                refreshLayout.finishLoadMore(true)
                refreshLayout.setNoMoreData(true)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                getMainJson(false)
            }
        })

        getMainJson(true)

        /**
         * 重新请求
         */
        binding.retry = object : RetryCallback {
            override fun retryClick() {
                getMainJson(true)
            }
        }
    }

    /**
     * 获取首页数据
     */
    private fun getMainJson(isFirst: Boolean) {
        vm.getHomeJson(isFirst).observe(this, Observer {
            bindData(it)
        })
    }

    /**
     * 绑定数据
     *
     * @param baseEntityResource
     */
    private fun bindData(baseEntityResource: Resource<HomeEntity>?) {
        binding.resource = baseEntityResource

        if (baseEntityResource!!.status === Status.SUCCESS) {
            val homeEntity = baseEntityResource!!.data

            cfgBanner(homeEntity!!.appHomeBannerList!!)

            typeData.clear()
            typeData.addAll(baseEntityResource.data!!.appHomeCategoryList!!)
            recTypeAdapter!!.setData(typeData)
            recTypeAdapter!!.notifyDataSetChanged()

            //今日推荐
            recNowAdapter!!.setData(homeEntity)
            recNowAdapter!!.notifyDataSetChanged()

            productData.clear()
            productData.addAll(baseEntityResource.data.appHomeProdlistList!!)
            recProductAdapter.setData(productData)
            recProductAdapter.notifyDataSetChanged()

            if (binding.srl.isRefreshing) {
                binding.srl.finishRefresh(true)
            }
        }
    }

    /**
     * Banner配置
     *
     * @param baseEntityResource
     */
    private fun cfgBanner(baseEntityResource: List<HomeEntity.AppHomeBannerListBean>) {
        val bannerUrls = ArrayList<String>()
        for (i in baseEntityResource.indices) {
            bannerUrls.add(baseEntityResource[i].icon!!)
        }

        binding.banner.setImages(bannerUrls)
        binding.banner.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                Glide.with(context).load(path).into(imageView)
            }
        })
        binding.banner.setBannerAnimation(Transformer.Stack)
        binding.banner.setIndicatorGravity(BannerConfig.RIGHT)
        binding.banner.start()
        binding.banner.setOnBannerListener { position ->
            if (baseEntityResource[position].isJump == 0) {
                val args = Bundle()
                args.putInt(Constants.LOAN_ID, baseEntityResource[position].id)
                ActivityUtils.startActivity(args, LoanDetailActivity::class.java)
            } else {
                val args = Bundle()
                args.putString(Constants.URL, baseEntityResource[position].requestUrl)
                ActivityUtils.startActivity(args, WebViewActivity::class.java)
            }
        }
    }
}
