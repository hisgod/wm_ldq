package com.aib.view.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.aib.adapter.PhotoViewAdapter
import com.blankj.utilcode.util.BarUtils
import com.wm.loan.R
import com.wm.loan.databinding.ActivityPhotoViewBinding
import java.util.ArrayList


class PhotoViewActivity : BaseActivity<ActivityPhotoViewBinding>() {

    override fun getResId(): Int=R.layout.activity_photo_view

    override fun initData(savedInstanceState: Bundle?) {
        binding.tb.setPadding(0, 0, BarUtils.getStatusBarHeight(), 0)

        binding.presenter = Presenter()

        val data = intent.extras!!.getSerializable("data") as ArrayList<Any>

        val content = intent.extras.getString("content")

        val adapter = PhotoViewAdapter(data)
        binding.vp.adapter = adapter
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }
    }
}
