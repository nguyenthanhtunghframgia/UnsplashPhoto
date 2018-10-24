package com.example.nguyenthanhtungh.unsplashphoto.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseActivity
import com.example.nguyenthanhtungh.unsplashphoto.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val bindingVariable: Int = BR.mainViewModel

    override fun initComponent(viewDataBinding: ViewDataBinding, savedInstanceState: Bundle?) {
        //todo
    }

    override val viewModel: MainViewModel = MainViewModel()

    override fun getLayout(): Int = R.layout.activity_main
}
