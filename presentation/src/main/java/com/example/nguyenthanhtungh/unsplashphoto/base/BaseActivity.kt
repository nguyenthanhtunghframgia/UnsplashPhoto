package com.example.nguyenthanhtungh.unsplashphoto.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseActivity<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : AppCompatActivity() {
    abstract val viewModel: ViewModel
    lateinit var viewBinding: ViewBinding
    abstract val bindingVariable: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, getLayout())
        viewBinding.apply {
            setVariable(bindingVariable, viewModel)
            setLifecycleOwner(this@BaseActivity)
            executePendingBindings()
        }
        initComponent(viewBinding, savedInstanceState)
    }

    abstract fun initComponent(viewDataBinding: ViewDataBinding, savedInstanceState: Bundle?)
    abstract fun getLayout(): Int

    open fun addFragment(fragment: Fragment, container: Int, tag: String, addBackStack: Boolean) {
        supportFragmentManager.beginTransaction().add(container, fragment).apply {
            if (addBackStack) addToBackStack(tag)
        }.commit()
    }

    open fun replaceFragment(fragment: Fragment, container: Int, tag: String, addBackStack: Boolean) {
        supportFragmentManager.beginTransaction().replace(container, fragment).apply {
            if (addBackStack) addToBackStack(tag)
        }.commit()
    }
}
