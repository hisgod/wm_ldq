package com.aib.view.fragment;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aib.entity.BaseEntity;
import com.aib.utils.Constants;
import com.aib.entity.ApplyStatusEntity;
import com.aib.view.activity.ApplyStatusActivity;
import com.aib.view.activity.FillInfoActivity;
import com.aib.view.activity.LoginActivity;
import com.aib.viewmodel.CertainFragmentVm;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wm.loan.R;
import com.wm.loan.databinding.FragmentOldCertainBinding;


import static android.app.Activity.RESULT_OK;

/**
 * 一定下款
 */
public class OldCertainFragment extends BaseFragment<FragmentOldCertainBinding> {

    private CertainFragmentVm vm;
    private Boolean isFirst = false;

    @Override
    public int getResId() {
        return R.layout.fragment_old_certain;
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        vm = getViewModel(CertainFragmentVm.class);

        getBinding().setPresenter(new Presenter());

        isApply();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String showText = data.getStringExtra("showText");
            getBinding().btnStart.setText(showText);
        }
    }

    public class Presenter {
        /**
         * 进入填写信息页面1
         *
         * @param view
         */
        public void cash(View view) {
            if (SPUtils.getInstance().getString(Constants.INSTANCE.getTOKEN()) != "") {
                isFirst = true;
                isApply();
            } else {
                ActivityUtils.startActivity(LoginActivity.class);
            }
        }
    }

    private void isApply() {
        vm.isApply(SPUtils.getInstance().getString(Constants.INSTANCE.getTOKEN())).observe(this, new Observer<BaseEntity<ApplyStatusEntity>>() {
            @Override
            public void onChanged(@Nullable BaseEntity<ApplyStatusEntity> applyStatusEntityBaseEntity) {
                if (applyStatusEntityBaseEntity.getCode() == 1) {
                    if (applyStatusEntityBaseEntity.getData().getStatus() == 0) {
                        ActivityUtils.startActivity(FillInfoActivity.class);
                    } else {
                        getBinding().btnStart.setText("查看进度");
                        if (isFirst) {
                            isFirst = false;
                            ActivityUtils.startActivity(ApplyStatusActivity.class);
                            ToastUtils.showShort(applyStatusEntityBaseEntity.getData().getStatusName());
                        }
                    }
                } else {
                    ToastUtils.showShort(applyStatusEntityBaseEntity.getMsg());
                }
            }
        });
    }
}
