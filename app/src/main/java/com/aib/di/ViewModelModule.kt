package com.aib.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.aib.viewmodel.*

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    /**
     * 填写资料
     */
    @Binds
    @IntoMap
    @ViewModelKey(FillInfoVm::class)
    internal abstract fun FillInfoVm(vm: FillInfoVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterVm::class)
    internal abstract fun RegisterViewModel(vm: RegisterVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForgetPwdVm::class)
    internal abstract fun ForgetPwdViewModel(vm: ForgetPwdVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginVm::class)
    internal abstract fun LoginViewModel(vm: LoginVm): ViewModel

    /**
     * MainActivity
     */
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun MainViewModel(viewModel: MainViewModel): ViewModel

    /**
     * 个人信息
     */
    @Binds
    @IntoMap
    @ViewModelKey(PersonalInfoVm::class)
    internal abstract fun PersonalInfoVm(vm: PersonalInfoVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MsgCenterViewModel::class)
    internal abstract fun MsgCenterViewModel(vm: MsgCenterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashVm::class)
    internal abstract fun SplashVm(vm: SplashVm): ViewModel

    /**
     * 贷款平台详情
     *
     * @param vm
     * @return
     */
    @Binds
    @IntoMap
    @ViewModelKey(LoanDetailVm::class)
    internal abstract fun LoanDetailVm(vm: LoanDetailVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AgreementVm::class)
    internal abstract fun AgreementVm(vm: AgreementVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WebVm::class)
    internal abstract fun WebVm(vm: WebVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ApplyStatusVm::class)
    internal abstract fun ApplyStatusVm(vm: ApplyStatusVm): ViewModel

    /**
     * 更多口子
     *
     * @param vm
     * @return
     */
    @Binds
    @IntoMap
    @ViewModelKey(MoreFragmentVm::class)
    internal abstract fun MoreFragmentVm(vm: MoreFragmentVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CertainFragmentVm::class)
    internal abstract fun CertainFragmentVm(vm: CertainFragmentVm): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CreditQueryVm::class)
    internal abstract fun CreditQueryVm(vm: CreditQueryVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreditFragmentVm::class)
    internal abstract fun CreditFragmentVm(vm: CreditFragmentVm): ViewModel

    /**
     * 首页
     */
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun HomeViewModel(vm: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CenterFragmentVm::class)
    internal abstract fun CenterFragmentVm(vm: CenterFragmentVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FootprintVm::class)
    internal abstract fun FootprintVm(vm: FootprintVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TypeListVm::class)
    internal abstract fun TypeListVm(vm: TypeListVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RelationVm::class)
    internal abstract fun RelationVm(vm: RelationVm): ViewModel

    /**
     * 工作信息
     *
     * @param vm
     * @return
     */
    @Binds
    @IntoMap
    @ViewModelKey(WorkInfoVm::class)
    internal abstract fun WorkInfoVm(vm: WorkInfoVm): ViewModel

    /**
     * 金融信息
     *
     * @param vm
     * @return
     */
    @Binds
    @IntoMap
    @ViewModelKey(FinanceInfoVm::class)
    internal abstract fun FinanceInfoVm(vm: FinanceInfoVm): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TestVm::class)
    internal abstract fun TestVm(vm: TestVm): ViewModel

    /**
     *等待审核
     */
    @Binds
    @IntoMap
    @ViewModelKey(VertifyVm::class)
    internal abstract fun VertifyVm(vm: VertifyVm): ViewModel

    /**
     * 发布帖子VM
     *
     * @param vm
     * @return
     */
    @Binds
    @IntoMap
    @ViewModelKey(PostCardVm::class)
    internal abstract fun PostCardVm(vm: PostCardVm): ViewModel

    /**
     * 帖子列表
     *
     * @param vm
     * @return
     */
    @Binds
    @IntoMap
    @ViewModelKey(CardFragmentVm::class)
    internal abstract fun CardFragmentVm(vm: CardFragmentVm): ViewModel

    /**
     * 帖子详情VM
     *
     * @param vm
     * @return
     */
    @Binds
    @IntoMap
    @ViewModelKey(CardDetailVm::class)
    internal abstract fun CardDetailVm(vm: CardDetailVm): ViewModel

    /**
     * 银行卡账户
     */
    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    internal abstract fun AccountViewModel(vm: AccountViewModel): ViewModel

    /**
     * 记账
     */
    @Binds
    @IntoMap
    @ViewModelKey(MarkViewModel::class)
    internal abstract fun MarkViewModel(vm: MarkViewModel): ViewModel

    /**
     * 账户列表
     */
    @Binds
    @IntoMap
    @ViewModelKey(AccountListViewModel::class)
    internal abstract fun AccountListViewModel(vm: AccountListViewModel): ViewModel

    /**
     * 记账本选项卡
     */
    @Binds
    @IntoMap
    @ViewModelKey(BookTabViewModel::class)
    internal abstract fun BookTabViewModel(vm: BookTabViewModel): ViewModel

    /**
     * 记账本
     */
    @Binds
    @IntoMap
    @ViewModelKey(BookViewModel::class)
    internal abstract fun BookViewModel(vm: BookViewModel): ViewModel

    /**
     * 绑定银行卡
     */
    @Binds
    @IntoMap
    @ViewModelKey(BandCardViewModel::class)
    internal abstract fun BandCardViewModel(vm: BandCardViewModel): ViewModel

    /**
     * 支付绑卡费用
     */
    @Binds
    @IntoMap
    @ViewModelKey(PayBindCardViewModel::class)
    internal abstract fun PayBindCardViewModel(vm: PayBindCardViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
