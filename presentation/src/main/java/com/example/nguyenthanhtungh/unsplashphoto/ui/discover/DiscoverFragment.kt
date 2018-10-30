package com.example.nguyenthanhtungh.unsplashphoto.ui.discover

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseFragment
import com.example.nguyenthanhtungh.unsplashphoto.base.EndlessScrollListener
import com.example.nguyenthanhtungh.unsplashphoto.base.RecyclerItemDecoration
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentDiscoverBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.HistoryItem
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import com.example.nguyenthanhtungh.unsplashphoto.ui.photodetail.PhotoDetailFragment
import com.example.nguyenthanhtungh.unsplashphoto.ui.search.SearchFragment
import com.example.nguyenthanhtungh.unsplashphoto.util.DialogUtils
import com.example.nguyenthanhtungh.unsplashphoto.util.ITEM_DECORATION
import com.example.nguyenthanhtungh.unsplashphoto.util.SPAN_COUNT
import org.koin.android.viewmodel.ext.android.viewModel

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding, DiscoverViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TAG = "DiscoverFragment"
        fun newInstance() = DiscoverFragment()
    }

    override val layoutId = R.layout.fragment_discover
    override val viewModel by viewModel<DiscoverViewModel>()
    override val bindingVariable: Int = BR.discoverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initComponent(viewDataBinding: FragmentDiscoverBinding) {

        setToolbar(viewDataBinding.toolbar, getString(R.string.discover))

        val discoverAdapter = DiscoverAdapter(
            onItemClick = {
                goToDetailFragment(it)
            }
        )

        val endlessScrollListener = EndlessScrollListener { viewModel.onLoadMore() }
        val decoration = RecyclerItemDecoration(ITEM_DECORATION)
        viewDataBinding.apply {
            recyclerDiscover.apply {
                adapter = discoverAdapter
                layoutManager = GridLayoutManager(context, SPAN_COUNT)
                addItemDecoration(decoration)
                addOnScrollListener(endlessScrollListener)
            }
        }
        viewBinding.swipeLayout.setOnRefreshListener(this@DiscoverFragment)

        viewModel.apply {

            listDiscoverPhotoItem.observe(this@DiscoverFragment, Observer {
                discoverAdapter.submitList(it)
            })
            firstLoad()

            isLoadMore.observe(this@DiscoverFragment, Observer {
                if (it == null) return@Observer
                endlessScrollListener.isLoading = it
            })

            isRefresh.observe(this@DiscoverFragment, Observer {
                viewBinding.swipeLayout.apply { isRefreshing = it == true }
            })

            errorMessage.observe(this@DiscoverFragment, Observer {
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
                viewModel.insertHistory(HistoryItem(0, query ?: ""))
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

    private fun goToDetailFragment(it: PhotoItem) {
        val photoDetailFragment = PhotoDetailFragment.newInstance(it)
        replaceFragment(
            R.id.frame_layout, photoDetailFragment, PhotoDetailFragment.TAG, true
        )
    }

    override fun onRefresh() {
        viewModel.refresh()
    }
}
