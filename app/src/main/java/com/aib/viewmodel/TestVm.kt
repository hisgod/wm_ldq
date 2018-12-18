package com.aib.viewmodel

import android.arch.lifecycle.ViewModel

import com.aib.net.ApiService


import javax.inject.Inject


class TestVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

}
