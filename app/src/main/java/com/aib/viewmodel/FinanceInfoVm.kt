package com.aib.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aib.utils.Constants
import com.aib.entity.BaseEntity
import com.aib.net.ApiService
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.SPUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class FinanceInfoVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 提交金融信息图片
     */
    fun putFinanceImg(filePath: String): MutableLiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        val file = FileUtils.getFileByPath(filePath)
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val build = MultipartBody.Builder()
                .addFormDataPart("imgFile", file.name, requestBody)
                .addFormDataPart("token", SPUtils.getInstance().getString(Constants.TOKEN))
                .setType(MultipartBody.FORM)
                .build()
        apiService.PUT_FINANCE_IMG(build)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(object : Observer<BaseEntity<String>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<String>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        return data
    }

    fun putFinanceInfo(appUserId: Int, imgIdHeld: String?, imgLoanRecord: String?, imgRepayRecord: String?) {
        apiService
                .PUT_FINANCE_INFO(appUserId, imgIdHeld, imgLoanRecord, imgRepayRecord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<String>> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<String>) {
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }
}