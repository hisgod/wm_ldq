package com.aib.view.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View

import com.aib.utils.Constants
import com.aib.entity.ContactEntity
import com.aib.entity.RelationEntity
import com.aib.viewmodel.RelationVm
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.google.gson.Gson


import com.aib.sdk.bqs.BqsVertification
import com.authreal.api.AuthBuilder
import com.authreal.api.OnResultListener
import com.blankj.utilcode.util.*
import com.bqs.crawler.cloud.sdk.OnLoginResultListener
import com.wm.loan.R
import com.wm.loan.databinding.ActivityRelationBinding
import java.util.*

/**
 * 人际关系
 */
class RelationActivity : BaseActivity<ActivityRelationBinding>() {
    private lateinit var vm: RelationVm
    internal var relations: MutableList<String> = ArrayList()
    private val CONTACTS1 = 0
    private val CONTACTS2 = 1

    /**
     * 查询全部联系人
     */
    private val allContacts: List<ContactEntity>
        get() {
            val cr = contentResolver
            val uri = ContactsContract.Contacts.CONTENT_URI
            val cursor = cr.query(uri, null, null, null, null)
            val data = ArrayList<ContactEntity>()
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))

                    val phoneCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null)
                    var phone: String? = null
                    if (phoneCursor != null) {
                        phoneCursor.moveToFirst()
                        phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        phoneCursor.close()

                        val contactEntity = ContactEntity(name, phone!!)
                        data.add(contactEntity)
                    }
                }
                cursor.close()
            }
            return data
        }

    override fun getResId(): Int =R.layout.activity_relation

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0,BarUtils.getStatusBarHeight(),0,0)

        vm = getViewModel(RelationVm::class.java)

        relations.add("父母")
        relations.add("配偶")
        relations.add("亲戚")

        binding.presenter = Presenter()

        showBackTip()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri = data!!.data
            val contacts = getPhoneContacts(uri)
            if (requestCode == CONTACTS1) {
                binding.tvName1.text = contacts!![0]
                val phone = contacts[1]!!.replace(" ".toRegex(), "")
                val finalPhone = phone.replace("-".toRegex(), "")
                binding.tvPhone1.text = finalPhone
            } else if (requestCode == CONTACTS2) {
                binding.tvName2.text = contacts!![0]
                val phone = contacts[1]!!.replace(" ".toRegex(), "")
                val finalPhone = phone.replace("-".toRegex(), "")
                binding.tvPhone2.text = finalPhone
            }
        }
    }

    private fun getPhoneContacts(uri: Uri?): Array<String?>? {
        val contact = arrayOfNulls<String>(2)
        //得到ContentResolver对象
        val cr = contentResolver
        //取得电话本中开始一项的光标
        val cursor = cr.query(uri!!, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            //取得联系人姓名
            val nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            contact[0] = cursor.getString(nameFieldColumnIndex)
            //取得电话号码
            val ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            val phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null)
            if (phone != null) {
                phone.moveToFirst()
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
            phone!!.close()
            cursor.close()
        } else {
            return null
        }
        return contact
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
         * 填写完资料，下一步
         *
         * @param view
         */
        fun enterStatus(view: View) {
            val getRelation1 = binding.tvRelation1.text.toString().trim { it <= ' ' }
            val getName1 = binding.tvName1.text.toString().trim { it <= ' ' }
            val getPhone1 = binding.tvPhone1.text.toString().trim { it <= ' ' }
            val getRelation2 = binding.tvRelation2.text.toString().trim { it <= ' ' }
            val getName2 = binding.tvName2.text.toString().trim { it <= ' ' }
            val getPhone2 = binding.tvPhone2.text.toString().trim { it <= ' ' }

            if (getRelation1 == "请选择") {
                ToastUtils.showShort("请选择第一联系人关系")
                return
            }

            if (getName1 == "请选择") {
                ToastUtils.showShort("请选择第一联系人")
                return
            }

            if (getRelation2 == "请选择") {
                ToastUtils.showShort("请选择第二联系人关系")
                return
            }

            if (getName2 == "请选择") {
                ToastUtils.showShort("请选择第二联系人")
                return
            }

            val fisrtRelation = RelationEntity(getRelation1, getName1, getPhone1)
            val secondRelation = RelationEntity(getRelation2, getName2, getPhone2)
            val relationEntities = ArrayList<RelationEntity>()
            relationEntities.add(fisrtRelation)
            relationEntities.add(secondRelation)
            val json = Gson().toJson(relationEntities)
            vm.putEmergencyContact(json, SPUtils.getInstance().getString(Constants.TOKEN)).observe(this@RelationActivity, Observer { stringBaseEntity ->
                if (stringBaseEntity!!.code == 1) {
                    ToastUtils.showShort(stringBaseEntity.msg)
                    ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<String>() {
                        override fun onSuccess(result: String?) {

                        }

                        override fun doInBackground(): String? = SPUtils.getInstance().getString(Constants.TOKEN)
                    })
                } else {
                    ToastUtils.showShort(stringBaseEntity.msg)
                }
            })
        }

        /**
         * 第一联系人
         *
         * @param view
         */
        fun showRelation1(view: View) {
            KeyboardUtils.hideSoftInput(this@RelationActivity)
            val pvOptions = OptionsPickerBuilder(this@RelationActivity, OnOptionsSelectListener { options1, options2, options3, v ->
                //返回的分别是三个级别的选中位置
                binding.tvRelation1.text = relations[options1]
            })
                    .setTitleText("家属关系")
                    .setDividerColor(Color.RED)
                    .setTextColorCenter(Color.RED) //设置选中项文字颜色
                    .setContentTextSize(16)
                    .build<String>()

            pvOptions.setPicker(relations)//三级选择器
            pvOptions.show()
        }

        /**
         * 第二联系人关系
         *
         * @param view
         */
        fun showRelation2(view: View) {
            KeyboardUtils.hideSoftInput(this@RelationActivity)
            val pvOptions = OptionsPickerBuilder(this@RelationActivity, OnOptionsSelectListener { options1, options2, options3, v ->
                //返回的分别是三个级别的选中位置
                binding.tvRelation2.text = relations[options1]
            })
                    .setTitleText("家属关系")
                    .setDividerColor(Color.RED)
                    .setTextColorCenter(Color.RED) //设置选中项文字颜色
                    .setContentTextSize(16)
                    .build<String>()

            pvOptions.setPicker(relations)//三级选择器
            pvOptions.show()
        }

        /**
         * 打开联系人列表
         *
         * @param view
         */
        fun openContact(view: View) {
            if (view.id == binding.rlRelation1.id) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = ContactsContract.Contacts.CONTENT_TYPE
                startActivityForResult(intent, CONTACTS1)
            } else if (view.id == binding.rlRelation2.id) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = ContactsContract.Contacts.CONTENT_TYPE
                startActivityForResult(intent, CONTACTS2)
            }
        }
    }

    /**
     * 查询用户下一步
     */
    private fun nextStep(token: String) {
        vm.nextStep(token).observe(this, Observer {
            if (it!!.code == 1) {
                when (it.data.nextStep) {
                    "Identity" -> {
                        //身份认证
                        faceAuth()
                    }
                    "Contacts" -> {
                        //联系人认证
                        ActivityUtils.startActivity(RelationActivity::class.java)
                    }
                    "Operators" -> {
                        //运营商认证
                        BqsVertification.switchVertify("黄廉飘", "440881199508065935", "15360060187", this@RelationActivity as AppCompatActivity, Constants.OPERATOR_TYPE, object : OnLoginResultListener {
                            override fun onLoginSuccess(p0: Int) {
                                vm.postOperator(token).observe(this@RelationActivity, Observer {
                                    if (it!!.code == 1) {
                                        ToastUtils.showShort("授权成功 serviceId=$p0")
                                    }
                                })
                            }

                            override fun onLoginFailure(p0: String?, p1: String?, p2: Int) {
                                //serviceId为服务类型
                                ToastUtils.showShort(String.format(Locale.CHINA, "授权失败 resultCode=%s ,resultDesc=%s,  serviceId=%d", p0, p1, p2))
                            }
                        })
                    }
                    "BindBankCard" -> {
                        //绑定银行卡
                        ActivityUtils.startActivity(BindCardActivity::class.java)
                    }
                    "AccessFee" -> {
                        //缴纳认证费
                        ActivityUtils.startActivity(PayBindCardActivity::class.java)
                    }
                    "Zhima" -> {
                        //芝麻认证
                        BqsVertification.switchVertify("黄廉飘", "440881199508065935", "15360060187", this@RelationActivity as AppCompatActivity, Constants.ZM, object : OnLoginResultListener {
                            override fun onLoginSuccess(p0: Int) {
                                ToastUtils.showShort("授权成功 serviceId=$p0")
                            }

                            override fun onLoginFailure(p0: String?, p1: String?, p2: Int) {
                                //serviceId为服务类型
                                ToastUtils.showShort(String.format(Locale.CHINA, "授权失败 resultCode=%s ,resultDesc=%s,  serviceId=%d", p0, p1, p2))
                            }
                        })
                    }
                    "Work" -> {
                        //工作认证
                        ActivityUtils.startActivity(WorkInfoActivity::class.java)
                    }
                    "Financial" -> {
                        //金融信息认证
                        ActivityUtils.startActivity(FinanceInfoActivity::class.java)
                    }
                }
            }
        })
    }

    private fun showBackTip() {
        val builder = AlertDialog.Builder(this@RelationActivity)
                .setTitle("温馨提示")
                .setCancelable(false)
                .setMessage("您还没完成认证，真的要中途退出吗？完成认证步骤就可以借款了哦！")
        builder.setPositiveButton("继续认证") { dialog, which ->
            val allContacts = allContacts
            ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<String>() {
                @Throws(Throwable::class)
                override fun doInBackground(): String? {
                    return SPUtils.getInstance().getString(Constants.TOKEN)
                }

                override fun onSuccess(result: String?) {
                    if (TextUtils.isEmpty(result)) {
                        ToastUtils.showShort("Token为Null")
                    } else {
                        vm!!.putAlllContacts(Gson().toJson(allContacts), result!!).observe(this@RelationActivity, Observer { stringBaseEntity ->
                            if (stringBaseEntity!!.code == 1) {
                                ToastUtils.showShort(stringBaseEntity.msg)
                            } else {
                                ToastUtils.showShort(stringBaseEntity.msg)
                            }
                        })
                    }
                }
            })
            dialog.dismiss()
        }
        builder.setNegativeButton("取消认证") { dialog, which ->
            dialog.dismiss()
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * 人脸认证
     */
    private fun faceAuth() {
        val id = "demo_" + Date().time
        val mAuthBuilder = AuthBuilder(id, "3ce36316-c66c-401d-b290-8b141eba4a88", "", OnResultListener { s ->
            ToastUtils.showLong("result:$s")
        })
        mAuthBuilder.faceAuth(this)
    }
}
