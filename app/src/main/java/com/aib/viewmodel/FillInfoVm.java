package com.aib.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.ArrayMap;

import com.aib.entity.BaseEntity;
import com.aib.entity.CommitDataEntity;
import com.aib.net.ApiService;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FillInfoVm extends ViewModel {

    @Inject
    ApiService apiService;

    @Inject
    public FillInfoVm() {
    }

    /**
     * 提交资料 ，进行申请贷款
     *
     * @param params
     */
    public LiveData<BaseEntity<CommitDataEntity>> applyLoan(ArrayMap<String, Object> params) {
        final MutableLiveData<BaseEntity<CommitDataEntity>> data = new MutableLiveData<>();
        apiService
                .APPLY_LOAN(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseEntity<CommitDataEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<CommitDataEntity> commitDataEntityBaseEntity) {
                        data.postValue(commitDataEntityBaseEntity);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }
}
