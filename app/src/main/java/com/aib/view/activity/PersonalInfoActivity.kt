package com.aib.view.activity

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.os.Bundle
import android.support.v4.util.ArrayMap
import android.view.View

import com.aib.utils.Constants
import com.aib.viewmodel.PersonalInfoVm
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.api.widget.Widget
import com.yanzhenjie.durban.Controller
import com.yanzhenjie.durban.Durban


import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.*
import com.wm.loan.R
import com.wm.loan.databinding.ActivityPersonalInfoBinding

/**
 * 个人信息
 */
class PersonalInfoActivity : BaseActivity<ActivityPersonalInfoBinding>() {
    private lateinit var vm: PersonalInfoVm
    private var dialog: ProgressDialog? = null

    override fun getResId(): Int=R.layout.activity_personal_info

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0,BarUtils.getStatusBarHeight(),0,0)

        vm = getViewModel(PersonalInfoVm::class.java)

        binding.presenter = Presenter()

        dialog = ProgressDialog(this@PersonalInfoActivity)
    }

    inner class Presenter {
        /**
         * 返回
         *
         * @param view
         */
        fun back(view: View) {
            finish()
        }

        /**
         * 打开相册
         *
         * @param view
         */
        fun openAlbum(view: View) {
            album(view)
        }

        /**
         * 推出登录
         *
         * @param view
         */
        fun exit(view: View) {
            userExit()
        }
    }

    /**
     * 用户退出登录
     */
    private fun userExit() {
        dialog!!.setMessage("退出中...")
        dialog!!.show()
        vm.userExit(SPUtils.getInstance().getString(Constants.TOKEN)).observe(this@PersonalInfoActivity, Observer { stringBaseEntity ->
            if (stringBaseEntity!!.code == 1) {
                JPushInterface.deleteAlias(applicationContext, 0)
                ThreadUtils.getIoPool().submit { SPUtils.getInstance().clear() }
                ToastUtils.showShort(stringBaseEntity.msg)
                ActivityUtils.finishAllActivities()
                ActivityUtils.startActivity(LoginActivity::class.java)
            }
            dialog!!.dismiss()
        })
    }

    /**
     * 打开相册
     *
     * @param v
     */
    private fun album(v: View) {
        Album.image(this@PersonalInfoActivity)
                .singleChoice()
                .widget(Widget.newDarkBuilder(this@PersonalInfoActivity)
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
                        cutImage(filePath)
                    }
                }
                .onCancel { SnackbarUtils.with(v).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(Color.WHITE).setMessage("取消").show() }
                .start()
    }

    override fun onStart() {
        super.onStart()
        ThreadUtils.executeByIo(object : ThreadUtils.Task<ArrayMap<String, String>>() {
            @Throws(Throwable::class)
            override fun doInBackground(): ArrayMap<String, String>? {
                val data = ArrayMap<String, String>()
                val head_img_path = SPUtils.getInstance().getString(Constants.HEAD_IMG)
                val account = SPUtils.getInstance().getString(Constants.PHONE)
                data["head_img_path"] = head_img_path
                data["account"] = account
                return data
            }

            override fun onSuccess(result: ArrayMap<String, String>?) {
                Glide.with(applicationContext)
                        .load(result!!["head_img_path"])
                        .apply(RequestOptions().error(R.drawable.ic_img_error).placeholder(R.drawable.ic_img_loading))
                        .apply(RequestOptions().circleCrop()).into(binding.iv)
                binding.tvPhone.text = result["account"]
            }

            override fun onCancel() {

            }

            override fun onFail(t: Throwable) {

            }
        })
    }

    /**
     * 裁剪图片
     *
     * @param filePath
     */
    fun cutImage(vararg filePath: String) {
        Durban.with(this)
                // 裁剪界面的标题。
                .title("编辑")
                .statusBarColor(ContextCompat.getColor(this, R.color.colorAccent))
                .toolBarColor(ContextCompat.getColor(this, R.color.colorAccent))
                .navigationBarColor(ContextCompat.getColor(this, R.color.colorAccent))
                // 图片路径list或者数组。
                .inputImagePaths(*filePath)
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
                .requestCode(200)
                .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            200 -> {
                // 解析剪切结果：
                if (resultCode == RESULT_OK) {
                    val mImageList = Durban.parseResult(data!!)
                    dialog!!.setMessage("上传图片中...")
                    dialog!!.show()
                    vm.uploadSingleFile(mImageList[0]).observe(this@PersonalInfoActivity, Observer { stringBaseEntity ->
                        if (stringBaseEntity!!.code == 1) {
                            Glide.with(applicationContext).load(stringBaseEntity.data).apply(RequestOptions().circleCrop()).into(binding.iv)
                            ThreadUtils.executeByIo(object : ThreadUtils.Task<Boolean>() {
                                @Throws(Throwable::class)
                                override fun doInBackground(): Boolean? {
                                    SPUtils.getInstance().put(Constants.HEAD_IMG, stringBaseEntity.data)
                                    return true
                                }

                                override fun onSuccess(result: Boolean?) {
                                    if (result!!) {
                                        dialog!!.dismiss()
                                    }
                                }

                                override fun onCancel() {

                                }

                                override fun onFail(t: Throwable) {

                                }
                            })
                            ToastUtils.showShort(stringBaseEntity.msg)
                        } else {
                            ToastUtils.showShort(stringBaseEntity.msg)
                            dialog!!.dismiss()
                        }
                    })
                } else {
                    SnackbarUtils.with(binding.rlHeadImg).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(Color.WHITE).setMessage("取消").show()
                }
            }
        }
    }
}