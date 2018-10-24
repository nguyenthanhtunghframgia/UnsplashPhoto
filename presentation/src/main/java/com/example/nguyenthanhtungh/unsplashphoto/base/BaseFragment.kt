package com.example.nguyenthanhtungh.unsplashphoto.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {
    abstract val bindingVariable: Int
    lateinit var viewBinding: ViewBinding
    abstract val viewModel: ViewModel
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewBinding.apply {
            root.isClickable = true
            initComponent(viewBinding)
            setVariable(bindingVariable, viewModel)
            setLifecycleOwner(this@BaseFragment)
            executePendingBindings()
        }
        return viewBinding.root
    }

    abstract fun initComponent(viewDataBinding: ViewBinding)
}
