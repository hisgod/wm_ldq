package com.aib.di

import com.aib.view.fragment.*

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Fragment模块
 */
@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun recFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun allFragment(): MoreFragment

    @ContributesAndroidInjector
    internal abstract fun certainFragment(): CertainFragment

    @ContributesAndroidInjector
    internal abstract fun creditFragment(): CreditFragment

    @ContributesAndroidInjector
    internal abstract fun centerFragment(): CenterFragment

    @ContributesAndroidInjector
    internal abstract fun HouseFragment(): HouseFragment

    /**
     * 记账本
     */
    @ContributesAndroidInjector
    internal abstract fun BookFragment(): BookFragment

    /**
     * 记账本选项卡
     */
    @ContributesAndroidInjector
    internal abstract fun BookTabFragment(): BookTabFragment

    /**
     * 帖子模块
     *
     * @return
     */
    @ContributesAndroidInjector
    internal abstract fun CardFragment(): CardFragment

    /**
     * 旧版一定下贷模块
     */
    @ContributesAndroidInjector
    internal abstract fun OldCertainFragment(): OldCertainFragment
}
