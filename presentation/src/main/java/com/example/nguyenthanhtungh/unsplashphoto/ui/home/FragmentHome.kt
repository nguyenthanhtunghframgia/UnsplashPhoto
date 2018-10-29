package com.example.nguyenthanhtungh.unsplashphoto.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseFragment
import com.example.nguyenthanhtungh.unsplashphoto.base.EndlessScrollListener
import com.example.nguyenthanhtungh.unsplashphoto.base.RecyclerItemDecoration
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentHomeBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItem
import com.example.nguyenthanhtungh.unsplashphoto.ui.collectiondetail.CollectionDetailFragment
import com.example.nguyenthanhtungh.unsplashphoto.ui.main.MainActivity
import com.example.nguyenthanhtungh.unsplashphoto.ui.search.SearchFragment
import com.example.nguyenthanhtungh.unsplashphoto.util.ITEM_DECORATION
import com.example.nguyenthanhtungh.unsplashphoto.util.SPAN_COUNT
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentHome : BaseFragment<FragmentHomeBinding, FragmentHomeViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TAG = "HomeFragment"
        fun newInstance() = FragmentHome()
    }

    override val layoutId = R.layout.fragment_home
    override val viewModel by viewModel<FragmentHomeViewModel>()
    override val bindingVariable: Int = BR.fragmentHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initComponent(viewDataBinding: FragmentHomeBinding) {

        if (activity is MainActivity) {
            (activity as MainActivity).apply {
                setSupportActionBar(viewDataBinding.toolbar)
                setTitle(getString(R.string.collection))
            }

        }

        val fragmentHomeAdapter = FragmentHomeAdapter(
            onItemClick = {
                goToDetailFragment(it)
            }
        )

        val endlessScrollListener = EndlessScrollListener { viewModel.onLoadMore() }
        val decoration = RecyclerItemDecoration(ITEM_DECORATION)

        viewDataBinding.apply {
            recyclerCollection.apply {
                adapter = fragmentHomeAdapter
                layoutManager = GridLayoutManager(context, SPAN_COUNT)
                addItemDecoration(decoration)
                addOnScrollListener(endlessScrollListener)
            }
        }

        viewBinding.swipeLayout.setOnRefreshListener(this@FragmentHome)

        viewModel.apply {
            listCollectionItem.observe(this@FragmentHome, Observer {
                fragmentHomeAdapter.submitList(it)
            })
            firstLoad()

            isLoadMore.observe(this@FragmentHome, Observer {
                if (it == null) return@Observer
                endlessScrollListener.isLoading = it
            })

            isRefresh.observe(this@FragmentHome, Observer {
                viewBinding.swipeLayout.apply { isRefreshing = it == true }
            })

            errorMessage.observe(this@FragmentHome, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
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

    private fun goToDetailFragment(it: CollectionItem) {
        val movieDetailFragment = CollectionDetailFragment.newInstance(it.id, it.title ?: return)
        replaceFragment(
            R.id.frame_layout, movieDetailFragment, CollectionDetailFragment.TAG, true
        )
    }

    override fun onRefresh() {
        viewModel.refreshData()
    }
}
