package com.aib.view.activity

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View

import com.aib.utils.CommonFun
import com.blankj.utilcode.util.ToastUtils
import com.wm.loan.R
import com.wm.loan.databinding.ActivityCustomerBinding

/**
 * 客服中心
 */
class CustomerActivity : BaseActivity<ActivityCustomerBinding>() {

    override fun getResId(): Int = R.layout.activity_customer

    override fun initData(savedInstanceState: Bundle?) {
        binding.presenter = Presenter()
    }

    inner class Presenter {
        /**
         * 销毁页面
         */
        fun back(view: View) {
            finish()
        }

        /**
         * 复制微信公众号
         */
        fun cpyWechat(view: View) {
            if (CommonFun.isWeixinAvilible(applicationContext)) {
                var intent = Intent()
                var cmp = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(cmp);
                startActivity(intent);
                CommonFun.cpyText(applicationContext, "gh_4e2beba4e505", "微信公众号已复制到剪切板");
            } else {
                ToastUtils.showShort("未安装微信");
            }
        }
    }
}
