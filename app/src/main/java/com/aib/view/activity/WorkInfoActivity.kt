package com.aib.view.activity

import android.app.Activity
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.ContextCompat
import android.support.v4.util.ArrayMap
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView

import com.aib.utils.Constants
import com.aib.viewmodel.WorkInfoVm
import com.blankj.utilcode.util.*
import com.bumptech.glide.Glide
import com.wm.loan.R
import com.wm.loan.databinding.ActivityWorkInfoBinding
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.api.widget.Widget
import com.yanzhenjie.durban.Controller
import com.yanzhenjie.durban.Durban
import kotlinx.android.synthetic.main.dialog_work_year.*

/**
 * 工作信息
 */
class WorkInfoActivity : BaseActivity<ActivityWorkInfoBinding>() {
    var vm: WorkInfoVm? = null
    var fileUrlLeft: String? = null
    var fileUrlRight: String? = null

    override fun getResId(): Int = R.layout.activity_work_info

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        vm = getViewModel(WorkInfoVm::class.java)

        binding.presenter = Presenter()
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
                    vm!!.putWorkInfoImg(result.get(0)).observe(this@WorkInfoActivity, Observer {
                        if (it!!.code == 1) {
                            dialog.dismiss()
                            ToastUtils.showShort(it.msg)
                            Glide.with(this@WorkInfoActivity).load(it.data).into(binding.ivLeft)
                            fileUrlLeft = it.data
                        } else {
                            ToastUtils.showShort(it.msg)
                        }
                    })
                }
                201 -> {
                    val dialog = ProgressDialog(this)
                    dialog.setMessage("添加中...")
                    dialog.show()
                    vm!!.putWorkInfoImg(result.get(0)).observe(this@WorkInfoActivity, Observer {
                        if (it!!.code == 1) {
                            dialog.dismiss()
                            ToastUtils.showShort(it.msg)
                            Glide.with(this@WorkInfoActivity).load(it.data).into(binding.ivRight)
                            fileUrlRight = it.data
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
    fun cutImage(filePath: String, i: Int) {
        Durban.with(this)
                // 裁剪界面的标题。
                .title("裁剪")
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
                .requestCode(i)
                .start()
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }

        /**
         * 显示工作年限
         */
        fun showWorkYear(view: View) {
            val workYears = resources.getStringArray(R.array.work_year)
            val dialog = BottomSheetDialog(this@WorkInfoActivity)
            dialog.setContentView(R.layout.dialog_work_year)
            dialog.lv.adapter = ArrayAdapter<String>(this@WorkInfoActivity, android.R.layout.simple_list_item_1, workYears)
            dialog.lv.setOnItemClickListener { parent, view, position, id ->
                val tv = view as TextView
                binding.tvWorkYear.text = tv.text.toString().trim()
                dialog.dismiss()
            }
            dialog.show()
        }

        /**
         * 显示学历
         */
        fun showEducation(view: View) {
            val educations = resources.getStringArray(R.array.education)
            val dialog = BottomSheetDialog(this@WorkInfoActivity)
            dialog.setContentView(R.layout.dialog_work_year)
            dialog.lv.adapter = ArrayAdapter<String>(this@WorkInfoActivity, android.R.layout.simple_list_item_1, educations)
            dialog.lv.setOnItemClickListener { parent, view, position, id ->
                val tv = view as TextView
                binding.tvEducation.text = tv.text.toString().trim()
                dialog.dismiss()
            }
            dialog.show()
        }

        /**
         * 打开相册
         */
        fun openAlbum(view: View) {
            Album.image(this@WorkInfoActivity)
                    .singleChoice()
                    .widget(Widget.newDarkBuilder(this@WorkInfoActivity)
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
                    .onResult({ result ->
                        if (!result.isEmpty()) {
                            val filePath = result[0].path
                            when (view.id) {
                                binding.ivLeft.id -> {
                                    cutImage(filePath, 200)
                                }
                                binding.ivRight.id -> {
                                    cutImage(filePath, 201)
                                }
                            }
                        }
                    })
                    .onCancel({
                        ToastUtils.showShort("取消")
                    })
                    .start()
        }

        /**
         * 下一步
         */
        fun next(view: View) {
            val getCompanyName = binding.etCompanyName.text.toString().trim()
            val getCompanyAddress = binding.etCompanyAddress.text.toString().trim()
            val getPhone = binding.etPhone.text.toString().trim()
            val getIncome = binding.etIncome.text.toString().trim()
            val getWorkYear = binding.tvWorkYear.text.toString().trim()
            val getEducation = binding.tvEducation.text.toString().trim()


            if (TextUtils.isEmpty(getCompanyName)) {
                ToastUtils.showShort("请输入公司名称")
                return
            }

            if (TextUtils.isEmpty(getCompanyAddress)) {
                ToastUtils.showShort("请输入公司地址")
                return
            }

            if (TextUtils.isEmpty(getPhone)) {
                ToastUtils.showShort("请输入手机号")
                return
            }

            if (!RegexUtils.isMobileExact(getPhone)) {
                ToastUtils.showShort("请输入正确手机号")
                return
            }

            if (TextUtils.isEmpty(getIncome)) {
                ToastUtils.showShort("请输入月收入")
                return
            }

            if (getWorkYear.equals("请选择")) {
                ToastUtils.showShort("请选择工作年限")
                return
            }

            if (getEducation.equals("请选择")) {
                ToastUtils.showShort("请选择学历")
                return
            }

            if (TextUtils.isEmpty(fileUrlLeft)) {
                ToastUtils.showShort("请添加工作或收入证明")
                return
            }

            var map = ArrayMap<String, Any>()
            map.put("appUserId", SPUtils.getInstance().getInt(Constants.USER_ID))
            map.put("companyAddress", getCompanyAddress)
            map.put("companyName", getCompanyName)
            map.put("education", binding.tvEducation.text.toString().trim())
            map.put("monthIncome", getIncome)
            map.put("telephone", getPhone)
            map.put("workImg", fileUrlLeft)
            map.put("workLife", binding.tvWorkYear.text.toString().trim())
            if (!TextUtils.isEmpty(fileUrlRight)) {
                map.put("workOtherImg", fileUrlRight)
            }
            vm!!.putWorkInfo(map).observe(this@WorkInfoActivity, Observer {
                if (it?.code == 1) {
                    ToastUtils.showShort(it.msg)
                    ActivityUtils.startActivity(FinanceInfoActivity::class.java)
                } else {
                    ToastUtils.showShort(it?.msg)
                }
            })
        }
    }
}
