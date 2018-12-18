package com.aib.view.activity

import android.app.Activity
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View

import com.aib.utils.Constants
import com.aib.viewmodel.FinanceInfoVm
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.wm.loan.R
import com.wm.loan.databinding.ActivityFinanceInfoBinding
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.api.widget.Widget
import com.yanzhenjie.durban.Controller
import com.yanzhenjie.durban.Durban

/**
 * 金融信息
 */
class FinanceInfoActivity : BaseActivity<ActivityFinanceInfoBinding>() {
    var vm: FinanceInfoVm? = null
    var topUrl: String? = null
    var centerUrl: String? = null
    var endUrl: String? = null

    override fun getResId(): Int = R.layout.activity_finance_info

    override fun initData(savedInstanceState: Bundle?) {
        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        binding.presenter = Presenter()

        vm = getViewModel(FinanceInfoVm::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val result = Durban.parseResult(data!!)
            when (requestCode) {
                200 -> {
                    val dialog = ProgressDialog(this)
                    dialog.setMessage("添加中...")
                    dialog.show()
                    vm!!.putFinanceImg(result.get(0)).observe(this@FinanceInfoActivity, Observer {
                        if (it!!.code == 1) {
                            dialog.dismiss()
                            ToastUtils.showShort(it.msg)
                            Glide.with(this@FinanceInfoActivity).load(it.data).into(binding.iv1)
                            topUrl = it.data
                        } else {
                            ToastUtils.showShort(it.msg)
                        }
                    })
                }
                201 -> {
                    val dialog = ProgressDialog(this)
                    dialog.setMessage("添加中...")
                    dialog.show()
                    vm!!.putFinanceImg(result.get(0)).observe(this@FinanceInfoActivity, Observer {
                        if (it!!.code == 1) {
                            dialog.dismiss()
                            ToastUtils.showShort(it.msg)
                            Glide.with(this@FinanceInfoActivity).load(it.data).into(binding.iv2)
                            centerUrl = it.data
                        } else {
                            ToastUtils.showShort(it.msg)
                        }
                    })
                }

                202 -> {
                    val dialog = ProgressDialog(this)
                    dialog.setMessage("添加中...")
                    dialog.show()
                    vm!!.putFinanceImg(result.get(0)).observe(this@FinanceInfoActivity, Observer {
                        if (it!!.code == 1) {
                            dialog.dismiss()
                            ToastUtils.showShort(it.msg)
                            Glide.with(this@FinanceInfoActivity).load(it.data).into(binding.iv3)
                            endUrl = it.data
                        } else {
                            ToastUtils.showShort(it.msg)
                        }
                    })
                }
            }
        }
    }

    /**
     * 裁剪图片
     */
    fun cutImage(filePath: String, code: Int) {
        Durban.with(this)
                // 裁剪界面的标题。
                .title("编辑")
                .statusBarColor(ContextCompat.getColor(this, R.color.colorAccent))
                .toolBarColor(ContextCompat.getColor(this, R.color.colorAccent))
                .navigationBarColor(ContextCompat.getColor(this, R.color.colorAccent))
                // 图片路径list或者数组。
                .inputImagePaths(filePath)
                // 图片输出文件夹路径。
                .outputDirectory(cacheDir.absolutePath + "/img")
                // 裁剪图片输出的最大宽高。
                .maxWidthHeight(120, 120)
                // 裁剪时的宽高比。
                .aspectRatio(1f, 1f)
                // 图片压缩格式：JPEG、PNG。
                .compressFormat(Durban.COMPRESS_JPEG)
                // 图片压缩质量，请参考：Bitmap#compress(Bitmap.CompressFormat, int, OutputStream)
                .compressQuality(90)
                // 裁剪时的手势支持：ROTATE, SCALE, ALL, NONE.
                .gesture(Durban.GESTURE_ALL)
                .controller(
                        Controller.newBuilder()
                                .enable(true) // 是否开启控制面板。
                                .rotation(true) // 是否有旋转按钮。
                                .rotationTitle(true) // 旋转控制按钮上面的标题。
                                .scale(true) // 是否有缩放按钮。
                                .scaleTitle(true) // 缩放控制按钮上面的标题。
                                .build()) // 创建控制面板配置。
                .requestCode(code)
                .start()
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }

        fun next(view: View) {
            if (TextUtils.isEmpty(topUrl)) {
                ToastUtils.showShort("请添加近期借款记录")
                return
            }

            if (TextUtils.isEmpty(centerUrl)) {
                ToastUtils.showShort("请添加还款记录")
                return
            }

            if (TextUtils.isEmpty(endUrl)) {
                ToastUtils.showShort("请添加手持身份证照")
                return
            }

            ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<Int>() {
                override fun onSuccess(result: Int?) {
                    if (result != -1) {
                        vm?.putFinanceInfo(result!!, endUrl, topUrl, centerUrl)
                    }
                }

                override fun doInBackground(): Int? {
                    return SPUtils.getInstance().getInt(Constants.USER_ID)
                }

            })

        }

        /**
         * 打开相册
         */
        fun openAlbum(view: View) {
            Album.image(this@FinanceInfoActivity)
                    .singleChoice()
                    .widget(Widget.newDarkBuilder(this@FinanceInfoActivity)
                            .title("相册")
                            .statusBarColor(resources.getColor(R.color.colorAccent))
                            .toolBarColor(resources.getColor(R.color.colorAccent))
                            .navigationBarColor(resources.getColor(R.color.colorAccent))
                            .bucketItemCheckSelector(Color.BLACK, resources.getColor(R.color.colorAccent))
                            .mediaItemCheckSelector(Color.BLACK, resources.getColor(R.color.colorAccent))
                            .build())
                    .camera(true)
                    .columnCount(4)
                    .filterSize(null)
                    .filterMimeType(null)
                    .afterFilterVisibility(false)
                    .onResult { result ->
                        if (!result.isEmpty()) {
                            val filePath = result[0].path
                            when (view.id) {
                                binding.iv1.id -> {
                                    cutImage(filePath, 200)
                                }
                                binding.iv2.id -> {
                                    cutImage(filePath, 201)
                                }
                                binding.iv3.id -> {
                                    cutImage(filePath, 202)
                                }
                            }
                        }
                    }
                    .onCancel {
                        ToastUtils.showShort("取消")
                    }
                    .start()
        }
    }
}
