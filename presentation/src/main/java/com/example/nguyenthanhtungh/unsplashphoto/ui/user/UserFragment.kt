package com.example.nguyenthanhtungh.unsplashphoto.ui.user

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseFragment
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentUserBinding
import com.example.nguyenthanhtungh.unsplashphoto.ui.search.SearchFragment
import org.koin.android.viewmodel.ext.android.viewModel

class UserFragment : BaseFragment<FragmentUserBinding, UserViewModel>() {

    companion object {
        const val TAG = "UserFragment"
        fun newInstance() = UserFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override val bindingVariable: Int = BR.userViewModel

    override val viewModel by viewModel<UserViewModel>()

    override val layoutId: Int = R.layout.fragment_user

    override fun initComponent(viewDataBinding: FragmentUserBinding) {

        setToolbar(viewDataBinding.toolbar, getString(R.string.user))

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_view, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchItem.collapseActionView()
                goToSearchFragment(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun goToSearchFragment(query: String?) {
        val searchFragment = SearchFragment.newInstance(query)
        replaceFragment(
            R.id.frame_layout, searchFragment, SearchFragment.TAG, true
        )
    }
}
