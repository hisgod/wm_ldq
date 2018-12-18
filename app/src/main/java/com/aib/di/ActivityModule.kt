package com.aib.di

import com.aib.view.activity.*

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * 全部Activity模块
 */
@Module(includes = arrayOf(FragmentModule::class))
internal abstract class ActivityModule {
    /**
     * 填写个人资料
     */
    @ContributesAndroidInjector
    internal abstract fun FillInfoActivity(): FillInfoActivity

    @ContributesAndroidInjector
    internal abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun guideActivity(): GuideActivity

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun loginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun registerActivity(): RegisterActivity

    @ContributesAndroidInjector
    internal abstract fun forgetPwdInfoActivity(): ForgetPwdActivity

    @ContributesAndroidInjector
    internal abstract fun FootprintActivity(): FootprintActivity

    @ContributesAndroidInjector
    internal abstract fun PersonalInfoActivity(): PersonalInfoActivity

    @ContributesAndroidInjector
    internal abstract fun MsgCenterActivity(): MsgCenterActivity

    @ContributesAndroidInjector
    internal abstract fun agreementActivity(): AgreementActivity

    @ContributesAndroidInjector
    internal abstract fun loanDetailActivity(): LoanDetailActivity

    @ContributesAndroidInjector
    internal abstract fun RepaymentActivity(): RepaymentActivity

    @ContributesAndroidInjector
    internal abstract fun PhotoViewActivity(): PhotoViewActivity

    @ContributesAndroidInjector
    internal abstract fun WebActivity(): PayWebActivity

    @ContributesAndroidInjector
    internal abstract fun CreditQueryActivity(): CreditQueryActivity

    @ContributesAndroidInjector
    internal abstract fun ApplyStatusActivity(): ApplyStatusActivity

    @ContributesAndroidInjector
    internal abstract fun WebViewActivity(): WebViewActivity

    @ContributesAndroidInjector
    internal abstract fun CustomerActivity(): CustomerActivity

    /**
     * 贷款产品某个类别列表
     */
    @ContributesAndroidInjector
    internal abstract fun TypeDetailActivity(): TypeListActivity

    /**
     * 人际关系
     *
     * @return
     */
    @ContributesAndroidInjector
    internal abstract fun RelationActivity(): RelationActivity

    /**
     * 绑定银行卡
     *
     * @return
     */
    @ContributesAndroidInjector
    internal abstract fun BindCardActivity(): BindCardActivity

    /**
     * 工作信息
     *
     * @return
     */
    @ContributesAndroidInjector
    internal abstract fun WorkInfoActivity(): WorkInfoActivity

    /**
     * 金融信息
     *
     * @return
     */
    @ContributesAndroidInjector
    internal abstract fun FinanceInfoActivity(): FinanceInfoActivity

    /**
     * 淘宝验证
     *
     * @return
     */
    @ContributesAndroidInjector
    internal abstract fun TbAuthenActivity(): TbAuthenActivity

    /**
     * 借款详情
     *
     * @return
     */
    @ContributesAndroidInjector
    internal abstract fun BorrowDetailActivity(): BorrowDetailActivity

    /**
     * 订单
     *
     * @return
     */
    @ContributesAndroidInjector
    internal abstract fun OrderActivity(): OrderActivity

    @ContributesAndroidInjector
    internal abstract fun TestActivity(): TestActivity

    /**
     * 等待审核
     */
    @ContributesAndroidInjector
    internal abstract fun WaitActivity(): WaitActivity

    /**
     * 延期收款
     */
    @ContributesAndroidInjector
    internal abstract fun DelayRepaymentActivity(): DelayRepaymentActivity

    /**
     * 运营商认证
     */
    @ContributesAndroidInjector
    internal abstract fun OperatorActivity(): OperatorActivity


    /**
     * 缴纳绑定银行卡费用
     */
    @ContributesAndroidInjector
    internal abstract fun PayBindCardActivity(): PayBindCardActivity

    /**
     * 发布帖子
     *
     * @return
     */
    @ContributesAndroidInjector
    internal abstract fun PostCardActivity(): PostCardActivity

    /**
     * 帖子详情
     *
     * @return
     */
    @ContributesAndroidInjector
    internal abstract fun CardDetailActivity(): CardDetailActivity

    /**
     * 记账
     */
    @ContributesAndroidInjector
    internal abstract fun MarkActivity(): MarkActivity

    /**
     * 添加账户
     */
    @ContributesAndroidInjector
    internal abstract fun AccountActivity(): AddAccountActivity

    /**
     * 账户列表
     */
    @ContributesAndroidInjector
    internal abstract fun AccountListActivity(): AccountListActivity

    /**
     * 输入验证码
     */
    @ContributesAndroidInjector
    internal abstract fun InputPwdActivity(): InputPwdActivity
}
