package com.example.nguyenthanhtungh.unsplashphoto.ui.user

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseFragment
import com.example.nguyenthanhtungh.unsplashphoto.base.RecyclerItemDecoration
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentUserBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.HistoryItem
import com.example.nguyenthanhtungh.unsplashphoto.ui.search.SearchFragment
import com.example.nguyenthanhtungh.unsplashphoto.util.DialogUtils
import com.example.nguyenthanhtungh.unsplashphoto.util.ITEM_DECORATION
import com.example.nguyenthanhtungh.unsplashphoto.util.SPAN_COUNT
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

        showBottomView()

        setToolbar(viewDataBinding.toolbar, getString(R.string.user))

        val userAdapter =
            UserAdapter(
                onItemClick = {
                    goToSearchFragment(it)
                }
            )

        val decoration = RecyclerItemDecoration(ITEM_DECORATION)
        viewDataBinding.apply {
            recyclerRecentSearch.apply {
                adapter = userAdapter
                layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, LinearLayoutManager.HORIZONTAL)
                addItemDecoration(decoration)
            }
        }

        viewDataBinding.onClearClick = View.OnClickListener {
            viewModel.apply {

                deleteHistory()

                isDelete.observe(viewLifecycleOwner, Observer { delete ->
                    when (delete) {
                        true -> DialogUtils.showToast(context, getString(R.string.delete_complete))
                        false -> DialogUtils.showToast(context, getString(R.string.delete_fail))
                    }
                })
            }
        }

        viewDataBinding.onDownloadedPhotoClick = View.OnClickListener {
            DialogUtils.showToast(context, getString(R.string.coming_soon))
        }

        viewDataBinding.onLikedPhotoClick = View.OnClickListener {
            DialogUtils.showToast(context, getString(R.string.coming_soon))
        }

        viewModel.apply {

            listHistory.observe(viewLifecycleOwner, Observer {
                userAdapter.submitList(it)
            })

            getListHistory()

            isInsertComplete.observe(viewLifecycleOwner, Observer {
                //todo
            })

            errorInsertMessage.observe(viewLifecycleOwner, Observer {
                DialogUtils.showToast(context, it)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_view, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchItem.collapseActionView()
                viewModel.insertHistory(query ?: "")
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

    private fun goToSearchFragment(it: HistoryItem) {
        val searchFragment = SearchFragment.newInstance(it.query)
        replaceFragment(
            R.id.frame_layout, searchFragment, SearchFragment.TAG, true
        )
    }
}
