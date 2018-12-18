package com.aib.view.activity;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

import com.aib.entity.BaseEntity;
import com.aib.utils.Constants;
import com.aib.entity.CityEntity;
import com.aib.entity.CommitDataEntity;
import com.aib.viewmodel.FillInfoVm;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wm.loan.R;
import com.wm.loan.databinding.ActivityFillInfoBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * 填写应用 父母 配偶 亲戚
 */
public class FillInfoActivity extends BaseActivity<ActivityFillInfoBinding> {
    private List<CityEntity> options1Items = new ArrayList<>();
    private List<List<String>> options2Items = new ArrayList<>();
    //    private List<List<List<String>>> options3Items = new ArrayList<>();   //区
    private FillInfoVm vm;
    private String provinceName;    //省份
    private String cityName;    //城市

    @Override
    public int getResId() {
        return R.layout.activity_fill_info;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0);

        vm = getViewModel(FillInfoVm.class);

        getBinding().setPresenter(new Presenter());

        getBinding().cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getBinding().btnCommit.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    getBinding().btnCommit.setTextColor(Color.WHITE);
                } else {
                    getBinding().btnCommit.setBackgroundColor(getResources().getColor(R.color.color_adacac));
                    getBinding().btnCommit.setTextColor(Color.WHITE);
                }
            }
        });
    }

    public class Presenter {
        /**
         * 返回
         *
         * @param view
         */
        public void back(View view) {
            finish();
        }

        /**
         * 是否隐藏
         */
        public void isVisibleBasic(View view) {
            if (getBinding().llChild1.getVisibility() == View.VISIBLE) {
                getBinding().llChild1.setVisibility(View.GONE);
                getBinding().tvTip1.setSelected(false);
            } else {
                getBinding().llChild1.setVisibility(View.VISIBLE);
                getBinding().tvTip1.setSelected(true);
            }
        }

        public void isVisibleContact(View view) {
            if (getBinding().llChild2.getVisibility() == View.VISIBLE) {
                getBinding().llChild2.setVisibility(View.GONE);
                getBinding().tvTip2.setSelected(false);
            } else {
                getBinding().llChild2.setVisibility(View.VISIBLE);
                getBinding().tvTip2.setSelected(true);
            }
        }

        public void isVisibleWork(View view) {
            if (getBinding().llChild3.getVisibility() == View.VISIBLE) {
                getBinding().llChild3.setVisibility(View.GONE);
                getBinding().tvTip3.setSelected(false);
            } else {
                getBinding().llChild3.setVisibility(View.VISIBLE);
                getBinding().tvTip3.setSelected(true);
            }
        }

        /**
         * 填写完资料，进入申请状态
         *
         * @param view
         */
        public void enterStatus(View view) {
            String getApplyMoney = getBinding().etApplyMoney.getText().toString().trim();   //申请资金
            String getName = getBinding().etName.getText().toString().trim();   //名字
            String getPhone = getBinding().etPhone.getText().toString().trim();   //名字
            String getCId = getBinding().etCid.getText().toString().trim();   //身份证
            String getAddress = getBinding().tvAddress.getText().toString().trim();   //户籍
            String getRelationName1 = getBinding().etRelationName1.getText().toString().trim();   //常用联系人名字1
            String getRelation1 = getBinding().tvRelation1.getText().toString().trim();   //常用联系人关系
            String getRelationPhone1 = getBinding().etRelationPhone1.getText().toString().trim();   //常用联系人手机
            String getRelationName2 = getBinding().etRelationName2.getText().toString().trim();   //第二联系人名字
            String getRelation2 = getBinding().tvRelation2.getText().toString().trim();   //第二联系人关系
            String getRelationPhone2 = getBinding().etRelationPhone2.getText().toString().trim();   //第二联系人手机
            String getCompanyName = getBinding().etCompanyName.getText().toString().trim();   //公司名字
            String getCompanyPhone = getBinding().etCompanyPhone.getText().toString().trim();   //公司电话
            String getCompanyAddress = getBinding().etCompanyAddress.getText().toString().trim();   //芝麻分
            String getMonthMoney = getBinding().etMonthMoney.getText().toString().trim();   //月收入

            if (TextUtils.isEmpty(getApplyMoney)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("申请资金不能为空").show();
                return;
            }

            if (Double.valueOf(getApplyMoney) <= 0 || Double.valueOf(getApplyMoney) > 50000) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("请保证申请额度在1-50000").show();
                return;
            }

            if (TextUtils.isEmpty(getName)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("申请人姓名不能为空").show();
                return;
            }

            if (TextUtils.isEmpty(getPhone)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("请填写手机号").show();
                return;
            }

            if (!RegexUtils.isMobileExact(getPhone)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("请填写正确手机号").show();
                return;
            }

            if (TextUtils.isEmpty(getCId)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("申请人身份证号不能为空").show();
                return;
            }

            if (!RegexUtils.isIDCard18(getCId)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("请正确填写身份证号").show();
                return;
            }

            if (getAddress.equals("请选择")) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("申请人请选择户籍地址").show();
                return;
            }

            if (TextUtils.isEmpty(getRelationName1)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("常用联系人名字不能为空").show();
                return;
            }

            if (getRelation1.equals("请选择")) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("请选择常用联系人关系").show();
                return;
            }

            if (TextUtils.isEmpty(getRelationPhone1)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("常用联系人手机号不能为空").show();
                return;
            }

            if (!RegexUtils.isMobileExact(getRelationPhone1)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("请正确填写常用联系人手机号").show();
                return;
            }

            if (TextUtils.isEmpty(getRelationName2)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("第二联系人名字不能为空").show();
                return;
            }

            if (getRelation2.equals("请选择")) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("请选择第二联系人关系").show();
                return;
            }

            if (TextUtils.isEmpty(getRelationPhone2)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("第二联系人手机号不能为空").show();
                return;
            }

            if (!RegexUtils.isMobileExact(getRelationPhone2)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("请正确填写第二联系人手机号").show();
                return;
            }

            if (TextUtils.isEmpty(getCompanyName)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("请填写公司名字").show();
                return;
            }

            if (TextUtils.isEmpty(getCompanyPhone)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("请填写公司电话").show();
                return;
            }

            if (TextUtils.isEmpty(getCompanyAddress)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("请填写月收入额").show();
                return;
            }

            if (TextUtils.isEmpty(getCompanyAddress)) {
                SnackbarUtils.with(view).setMessageColor(Color.WHITE).setBgColor(getResources().getColor(R.color.colorAccent)).setMessage("请输入芝麻分").show();
                return;
            }

            if (!getBinding().cb.isChecked()) {
                ToastUtils.showShort("请勾选协议");
                return;
            }

            ArrayMap<String, Object> params = new ArrayMap<>();
            params.put("applyMoney", Integer.valueOf(getApplyMoney));//申请金额
            params.put("city", cityName); //城市
            params.put("companyAddress", getCompanyAddress);    //公司地址
            params.put("companyName", getCompanyName);  //公司名字
            params.put("companyPhone", getCompanyPhone);    //公司联系电话
            params.put("identityNum", getCId);   //身份证号
            params.put("name", getName);   //姓名
            params.put("phone", getPhone);   //电话
            params.put("province", provinceName);    //省份
            params.put("regularlyName", getRelationName1);   //常联系人姓名
            params.put("regularlyPhone", getRelationPhone1);  //常联系人电话
            params.put("regularlyRelation", getRelation1);   //常联系人关系
            params.put("salary", Double.valueOf(getMonthMoney));   //月薪
            params.put("secondName", getRelationName2);   //	第二联系人姓名
            params.put("secondPhone", getRelationPhone2);   //第二联系人电话
            params.put("secondRelation", getRelation2);   //第二联系人关系
            params.put("token", SPUtils.getInstance().getString(Constants.INSTANCE.getTOKEN()));   //token
            vm.applyLoan(params).observe(FillInfoActivity.this, new Observer<BaseEntity<CommitDataEntity>>() {
                @Override
                public void onChanged(@Nullable BaseEntity<CommitDataEntity> commitDataEntityBaseEntity) {
                    if (commitDataEntityBaseEntity.getData().getStatus() == 1) {
                        ToastUtils.showShort(commitDataEntityBaseEntity.getMsg());
                        setResult(RESULT_OK, getIntent().putExtra("showText", "查看进度"));
                        ActivityUtils.startActivity(ApplyStatusActivity.class);
                        finish();
                    } else {
                        ToastUtils.showShort("获取数据失败");
                    }
                }
            });
        }

        /**
         * 申请金额
         *
         * @return
         */
        public TextWatcher founds() {
            return new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
        }

        /**
         * 户籍所在地
         */
        public void selectAddress(View view) {
            KeyboardUtils.hideSoftInput(FillInfoActivity.this);

            String addressJson = ResourceUtils.readAssets2String("province.json");
            List<CityEntity> entity = new Gson().fromJson(addressJson, new TypeToken<List<CityEntity>>() {
            }.getType());

            options1Items = entity;

            for (int i = 0; i < entity.size(); i++) {
                //遍历省份
                List<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
                List<List<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                for (int c = 0; c < entity.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                    String CityName = entity.get(i).getCityList().get(c).getName();
                    CityList.add(CityName);//添加城市
                    List<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (entity.get(i).getCityList().get(c).getArea() == null
                            || entity.get(i).getCityList().get(c).getArea().size() == 0) {
                        City_AreaList.add("");
                    } else {
                        City_AreaList.addAll(entity.get(i).getCityList().get(c).getArea());
                    }
                    Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                }

                /**
                 * 添加城市数据
                 */
                options2Items.add(CityList);

                /**
                 * 添加地区数据
                 */
//                options3Items.add(Province_AreaList);

            }
            OptionsPickerView pvOptions = new OptionsPickerBuilder(FillInfoActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    String tx = options1Items.get(options1).getPickerViewText() +
                            options2Items.get(options1).get(options2);
//                            + options3Items.get(options1).get(options2).get(options3);
                    getBinding().tvAddress.setText(tx);

                    provinceName = options1Items.get(options1).getPickerViewText();
                    cityName = options2Items.get(options1).get(options2);

                    KeyboardUtils.showSoftInput(getBinding().etCid);
                }
            })
                    .setTitleText("城市选择")
                    .setDividerColor(Color.RED)
                    .setTextColorCenter(Color.RED) //设置选中项文字颜色
                    .setContentTextSize(18)
                    .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
            pvOptions.setPicker(options1Items, options2Items);//三级选择器
            pvOptions.show();
        }

        /**
         * 第一联系人
         *
         * @param view
         */
        public void showRelation1(View view) {
            KeyboardUtils.hideSoftInput(FillInfoActivity.this);
            final List<String> relation = new ArrayList<>();
            relation.add("父母");
            relation.add("配偶");
            relation.add("亲戚");
            OptionsPickerView pvOptions = new OptionsPickerBuilder(FillInfoActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置

                    getBinding().tvRelation1.setText(relation.get(options1));
                    KeyboardUtils.showSoftInput(getBinding().etRelationPhone1);
                }
            })
                    .setTitleText("家属关系")
                    .setDividerColor(Color.RED)
                    .setTextColorCenter(Color.RED) //设置选中项文字颜色
                    .setContentTextSize(18)
                    .build();

            pvOptions.setPicker(relation);//三级选择器
            pvOptions.show();
        }

        /**
         * 第二联系人关系
         *
         * @param view
         */
        public void showRelation2(View view) {
            KeyboardUtils.hideSoftInput(FillInfoActivity.this);
            final List<String> relation = new ArrayList<>();
            relation.add("父母");
            relation.add("配偶");
            relation.add("亲戚");
            OptionsPickerView pvOptions = new OptionsPickerBuilder(FillInfoActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    getBinding().tvRelation2.setText(relation.get(options1));

                    KeyboardUtils.showSoftInput(getBinding().etRelationPhone2);
                }
            })
                    .setTitleText("家属关系")
                    .setDividerColor(Color.RED)
                    .setTextColorCenter(Color.RED) //设置选中项文字颜色
                    .setContentTextSize(18)
                    .build();

            pvOptions.setPicker(relation);//三级选择器
            pvOptions.show();
        }

        /**
         * 投保协议
         *
         * @param view
         */
        public void agreement(View view) {
            Bundle args = new Bundle();
            args.putString("AGREEMENT_TYPE", "INSTRUCTIONS");
            ActivityUtils.startActivity(args, AgreementActivity.class);
        }

        /**
         * 关掉提示
         *
         * @param view
         */
        public void isShow(View view) {
            view.setVisibility(View.GONE);
        }
    }
}
