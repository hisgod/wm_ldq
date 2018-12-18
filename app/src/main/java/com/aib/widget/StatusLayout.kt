package com.aib.widget

import android.content.Context
import android.databinding.BindingAdapter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView

import com.aib.utils.RetryCallback

import com.wm.loan.R

/**
 * 页面状态控件
 */
class StatusLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_network_loading, this, true)
        inflater.inflate(R.layout.view_network_error, this, true)
        inflater.inflate(R.layout.view_network_empty, this, true)
    }

    companion object {

        /**
         * 是否显示加载中
         *
         * @param isLoading
         */
        @JvmStatic
        @BindingAdapter("isLoading")
        fun showLoadingView(view: ViewGroup, isLoading: Boolean) {
            val loadingView = view.getChildAt(0)
            if (isLoading) {
                loadingView.visibility = View.VISIBLE
            } else {
                loadingView.visibility = View.GONE
            }
        }

        /**
         * 加载成功
         *
         * @param view
         * @param isSuccess
         */
        @JvmStatic
        @BindingAdapter("isSuccess")
        fun showContentView(view: ViewGroup, isSuccess: Boolean) {
            val contentView = view.getChildAt(3)
            if (isSuccess) {
                contentView.visibility = View.VISIBLE
            } else {
                contentView.visibility = View.GONE
            }
        }

        /**
         * 加载出错
         *
         * @param view
         * @param isError
         */
        @JvmStatic
        @BindingAdapter("isError")
        fun showErrorView(view: ViewGroup, isError: Boolean) {
            val errorView = view.getChildAt(1)
            if (isError) {
                errorView.visibility = View.VISIBLE
            } else {
                errorView.visibility = View.GONE
            }
        }

        /**
         * 空数据
         *
         * @param view
         * @param isEmpty
         */
        @JvmStatic
        @BindingAdapter("isEmpty")
        fun showEmptyView(view: ViewGroup, isEmpty: Boolean) {
            val emptyView = view.getChildAt(2)
            if (isEmpty) {
                emptyView.visibility = View.VISIBLE
            } else {
                emptyView.visibility = View.GONE
            }
        }

        /**
         * 提示MSG
         *
         * @param view
         * @param msg
         */
        @JvmStatic
        @BindingAdapter("showMsg")
        fun showMsg(view: ViewGroup, msg: String?) {
            val errorView = view.getChildAt(1)
            val tvMsg = errorView.findViewById<TextView>(R.id.tv_msg)
            tvMsg.text = msg
        }

        /**
         * 重试
         *
         * @param view
         * @param callback
         */
        @JvmStatic
        @BindingAdapter("retry")
        fun retry(view: ViewGroup, callback: RetryCallback?) {
            val errorView = view.getChildAt(1)
            val emptyView = view.getChildAt(2)
            val errorBtn = errorView.findViewById<Button>(R.id.btn)
            val emptyBtn = emptyView.findViewById<Button>(R.id.btn)

            errorBtn.setOnClickListener {
                if (callback != null) callback.retryClick()
            }

            emptyBtn.setOnClickListener {
                if (callback != null) callback.retryClick()
            }
        }
    }
}
