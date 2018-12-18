package com.aib.di

import android.app.Application


import com.aib.LoanApplication

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, ActivityModule::class, AppModule::class))
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(baseApplication: LoanApplication)
}
