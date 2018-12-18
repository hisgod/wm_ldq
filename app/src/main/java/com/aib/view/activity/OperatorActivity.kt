package com.aib.view.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.wm.loan.R

import com.wm.loan.databinding.ActivityOperatorBinding

/**
 * 作者: Administrator on 2017/12/16.
 *
 *
 * 运营商认证
 * TODO：调用sdk demo页面显示 --- 页面显示屏蔽
 */
class OperatorActivity : BaseActivity<ActivityOperatorBinding>() {

    private var mDialogUtil: ProgressDialog? = null

    //    protected void onClick(View view) {
    //        switch (view.getId()) {
    //            case R.id.tv_userProtocol:      //用户协议
    //                break;
    //            case R.id.tv_froget:      //  忘记密码
    //                break;
    //            case R.id.tv_toNext:       //确定
    //                final String servicePwd = edt_servicePswd.getText().toString();
    //                if (TextUtils.isEmpty(servicePwd) || servicePwd.length() < 6) {
    //                    ToastUtils.showShort(this, "请输入有效的服务密码");
    //                } else if (!chb_userProtocol.isChecked()) {
    //                    ToastUtils.showShort(this, "请同意《用户使用协议》");
    //                } else {
    //                    mDialogUtil.showDialog();
    //                    loginAction.login(servicePwd, new OnMnoLoginListener() {
    //
    //                        @Override
    //                        public void onLoginSuccess() {
    //                            //登录成功
    //                            ToastUtils.showShort(OperatorActivity.this, "运营商授权成功");
    //                            saveResult(ConstantUtils.CHECK_PHONE_OPERAT, "", "", "", 1);
    //                        }
    //
    //                        @Override
    //                        public void onInputAuthSmsCode() {
    //                            //登录成功，需要输入鉴权验证码
    //                            mDialogUtil.dismissDialog();
    //                            printLogError("登录成功，需要输入鉴权验证码");
    //                            Intent intent = new Intent(OperatorActivity.this, CheckPhoneActivityTwo.class);
    //                            intent.putExtra("phoneType", 1);      // 鉴权验证码
    //                            startActivityForResult(intent, 100);
    //                        }
    //
    //                        @Override
    //                        public void onInputLoginSmsCode() {
    //                            //输入登录 短信验证码
    //                            mDialogUtil.dismissDialog();
    //                            printLogError("输入登录 短信验证码");
    //                            Intent intent = new Intent(OperatorActivity.this, CheckPhoneActivityTwo.class);
    //                            intent.putExtra("phoneType", 2);      // 短信验证码
    //                            intent.putExtra("servicePswd", servicePwd);      // 短信验证码
    //                            startActivityForResult(intent, 100);
    //                        }
    //
    //                        @Override
    //                        public void onLoginFailure(String resultCode, String resultDesc) {
    //                            //登录失败
    //                            ToastUtils.showShort(OperatorActivity.this, "运营商授权失败");
    //                            saveResult(ConstantUtils.CHECK_PHONE_OPERAT, "", "", "", -1);
    //                        }
    //                    });
    //                }
    //                break;
    //        }
    //    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == 110) {
            setResult(110)
            finish()
        }
    }


    /**
     * @param accessType 认证类型  0身份/1通讯录/2手机运营商/3支付宝/4微信/5淘宝/6京东/7工作/8活跃地址
     * /9银行卡/10信用卡(过期)/11人行/12社保/13公积金/14问卷/15反欺诈
     * /16芝麻信用/17上传借款视频,必须完成0-3才能进行后面认证
     * @param json       认证后返回的json数据 	可选
     * @param openId     认证后返回的openId	可选
     * @param outOrderId 认证后返回的订单号 	可选
     * @param state      认证状态 	可选,1已通过/-1不通过
     */
    //    private void saveResult(int accessType, String json, String openId, String outOrderId, int state) {
    //        HashMap<String, String> hashMap = new HashMap<>();
    //        try {
    //            hashMap.put("token",
    //                    DES3.decryptThreeDESECB(SharedPrefsUtil.getStringValue(this,
    //                            ConstantUtils.LOGIN_CONFIG, ConstantUtils.LOGIN_USER_TOKEN, "")));
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        hashMap.put("accessType", accessType + "");
    //        hashMap.put("json", json);
    //        hashMap.put("openId", "".equals(openId) ? "" : openId + "");
    //        hashMap.put("outOrderId", "".equals(outOrderId) ? "" : outOrderId + "");
    //        hashMap.put("state", state + "");
    //        RequestManager.getInstance(this).requestAsyn(
    //                this,
    //                ConstantUtils.CHECK_RESULT_SAVE,
    //                RequestManager.TYPE_POST_JSON,
    //                hashMap,
    //                new RequestManager.ReqCallBack<String>() {
    //                    @Override
    //                    public void onReqSuccess(String result) {
    //                        Log.e("saveResult", "result=" + result);
    //                        ToastUtils.showShort(OperatorActivity.this, result);
    ////                        timer.start();
    //                        mDialogUtil.dismissDialog();
    //                        setResult(110);
    //                        finish();
    //                    }
    //
    //                    @Override
    //                    public void onReqFailed(String errorMsg) {
    //                        mDialogUtil.dismissDialog();
    //                        ToastUtils.showShort(OperatorActivity.this, errorMsg);
    //                    }
    //                });
    //    }

    override fun getResId(): Int = R.layout.activity_operator

    override fun initData(savedInstanceState: Bundle?) {
        mDialogUtil = ProgressDialog(this@OperatorActivity)
        val str = "忘记运营商服务密码？ <a><font color='#e83737'>点这里</font></a>"

    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }
    }
}
