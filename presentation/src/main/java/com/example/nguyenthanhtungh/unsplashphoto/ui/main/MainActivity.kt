package com.example.nguyenthanhtungh.unsplashphoto.ui.main

import android.os.Bundle
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseActivity
import com.example.nguyenthanhtungh.unsplashphoto.ui.home.FragmentHome
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModel by viewModel<MainViewModel>()

    override fun getLayout(): Int = R.layout.activity_main

    override fun initComponent(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            replaceFragment(FragmentHome.newInstance(), R.id.frame_layout, FragmentHome.TAG, false)
        }
    }
}
