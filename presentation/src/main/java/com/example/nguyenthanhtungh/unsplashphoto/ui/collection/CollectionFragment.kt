package com.example.nguyenthanhtungh.unsplashphoto.ui.collection

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
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentCollectionBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItem
import com.example.nguyenthanhtungh.unsplashphoto.ui.collectiondetail.CollectionDetailFragment
import com.example.nguyenthanhtungh.unsplashphoto.ui.main.MainActivity
import com.example.nguyenthanhtungh.unsplashphoto.ui.search.SearchFragment
import com.example.nguyenthanhtungh.unsplashphoto.util.DialogUtils
import com.example.nguyenthanhtungh.unsplashphoto.util.ITEM_DECORATION
import com.example.nguyenthanhtungh.unsplashphoto.util.SPAN_COUNT
import org.koin.android.viewmodel.ext.android.viewModel

class CollectionFragment : BaseFragment<FragmentCollectionBinding, CollectionViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TAG = "CollectionFragment"
        fun newInstance() = CollectionFragment()
    }

    override val layoutId = R.layout.fragment_collection
    override val viewModel by viewModel<CollectionViewModel>()
    override val bindingVariable: Int = BR.collectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initComponent(viewDataBinding: FragmentCollectionBinding) {

        setToolbar(viewDataBinding.toolbar,getString(R.string.collection))

        val fragmentHomeAdapter = CollectionAdapter(
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

        viewBinding.swipeLayout.setOnRefreshListener(this@CollectionFragment)

        viewModel.apply {
            listCollectionItem.observe(this@CollectionFragment, Observer {
                fragmentHomeAdapter.submitList(it)
            })
            firstLoad()

            isLoadMore.observe(this@CollectionFragment, Observer {
                if (it == null) return@Observer
                endlessScrollListener.isLoading = it
            })

            isRefresh.observe(this@CollectionFragment, Observer {
                viewBinding.swipeLayout.apply { isRefreshing = it == true }
            })

            errorMessage.observe(this@CollectionFragment, Observer {
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
