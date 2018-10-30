package com.example.nguyenthanhtungh.unsplashphoto.ui.main

import android.os.Bundle
import android.view.MenuItem
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseActivity
import com.example.nguyenthanhtungh.unsplashphoto.ui.collection.CollectionFragment
import com.example.nguyenthanhtungh.unsplashphoto.ui.discover.DiscoverFragment
import com.example.nguyenthanhtungh.unsplashphoto.ui.user.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel>(), BottomNavigationView.OnNavigationItemSelectedListener {
    override val viewModel by viewModel<MainViewModel>()

    override fun getLayout(): Int = R.layout.activity_main

    override fun initComponent(savedInstanceState: Bundle?) {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            replaceFragment(DiscoverFragment.newInstance(), R.id.frame_layout, DiscoverFragment.TAG, false)
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.item_discover -> replaceFragment(
                DiscoverFragment.newInstance(),
                R.id.frame_layout,
                DiscoverFragment.TAG,
                false
            )
            R.id.item_collection -> replaceFragment(
                CollectionFragment.newInstance(),
                R.id.frame_layout,
                CollectionFragment.TAG,
                false
            )
            R.id.item_user -> replaceFragment(
                UserFragment.newInstance(),
                R.id.frame_layout,
                UserFragment.TAG,
                false
            )
        }
        return true
    }
}
