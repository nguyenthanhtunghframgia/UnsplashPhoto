package com.example.nguyenthanhtungh.unsplashphoto.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.nguyenthanhtungh.unsplashphoto.ui.main.MainActivity

abstract class BaseFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {
    abstract val bindingVariable: Int
    lateinit var viewBinding: ViewBinding
    abstract val viewModel: ViewModel
    abstract val layoutId: Int
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is MainActivity) {
            mainActivity = activity as MainActivity
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewBinding.apply {
            root.isClickable = true
            initComponent(viewBinding)
            setVariable(bindingVariable, viewModel)
            setLifecycleOwner(viewLifecycleOwner)
            executePendingBindings()
        }
        return viewBinding.root
    }

    fun replaceFragment(container: Int, fragment: Fragment, tag: String, addToBackStack: Boolean = false) {
        fragmentManager?.beginTransaction()?.replace(container, fragment)?.apply {
            if (addToBackStack) addToBackStack(tag)
        }?.commit()
    }

    fun onBackPress() {
        mainActivity.onBackPressed()
    }

    fun setToolbar(toolbar: Toolbar, title: String) {
        mainActivity.apply {
            setSupportActionBar(toolbar)
            setTitle(title)
        }
    }

    fun showBottomView() {
        mainActivity.showBottom()
    }

    fun hideBottomView() {
        mainActivity.hideBottom()
    }

    abstract fun initComponent(viewDataBinding: ViewBinding)

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onActivityDestroyed()
    }
}
