package com.example.nguyenthanhtungh.unsplashphoto.ui

import android.os.Bundle
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModel: MainViewModel = MainViewModel()

    override fun initComponent(savedInstanceState: Bundle?) {
        //todo
    }

    override fun getLayout(): Int = R.layout.activity_main
}
