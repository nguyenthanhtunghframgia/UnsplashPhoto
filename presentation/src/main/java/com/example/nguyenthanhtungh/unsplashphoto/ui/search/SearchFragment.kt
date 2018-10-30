package com.example.nguyenthanhtungh.unsplashphoto.ui.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseFragment
import com.example.nguyenthanhtungh.unsplashphoto.base.EndlessScrollListener
import com.example.nguyenthanhtungh.unsplashphoto.base.RecyclerItemDecoration
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentSearchBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItem
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import com.example.nguyenthanhtungh.unsplashphoto.ui.collectiondetail.CollectionDetailFragment
import com.example.nguyenthanhtungh.unsplashphoto.ui.photodetail.PhotoDetailFragment
import com.example.nguyenthanhtungh.unsplashphoto.ui.search.adapter.CollectionSearchAdapter
import com.example.nguyenthanhtungh.unsplashphoto.ui.search.adapter.PhotoSearchAdapter
import com.example.nguyenthanhtungh.unsplashphoto.util.DialogUtils
import com.example.nguyenthanhtungh.unsplashphoto.util.ITEM_DECORATION
import com.example.nguyenthanhtungh.unsplashphoto.util.SPAN_COUNT
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val QUERY_STRING = "QUERY_STRING"
        const val TAG = "SearchFragment"
        fun newInstance(query: String?) = SearchFragment().apply {
            arguments = Bundle().apply {
                putString(QUERY_STRING, query)
            }
        }
    }

    override val bindingVariable: Int = BR.searchViewModel

    override val viewModel by viewModel<SearchViewModel>()

    override val layoutId: Int = R.layout.fragment_search

    override fun initComponent(viewDataBinding: FragmentSearchBinding) {
        val query = arguments?.getString(QUERY_STRING) ?: return

        val searchCollectionAdapter =
            CollectionSearchAdapter(
                onItemClick = {
                    goToCollectionDetailFragment(it)
                }
            )

        val searchPhotoAdapter =
            PhotoSearchAdapter(
                onItemClick = {
                    goToDetailFragment(it)
                }
            )

        viewDataBinding.apply {
            textCollectionEmpty.text = getString(R.string.empty_collection_search)
            textPhotoEmpty.text = getString(R.string.empty_photo_search)
        }

        viewDataBinding.onBackPress = View.OnClickListener {
            onBackPress()
        }

        val endlessScrollListener = EndlessScrollListener { viewModel.onLoadMore(query) }
        val decoration = RecyclerItemDecoration(ITEM_DECORATION)
        viewDataBinding.apply {
            recyclerPhoto.apply {
                adapter = searchPhotoAdapter
                layoutManager = GridLayoutManager(context, SPAN_COUNT)
                addItemDecoration(decoration)
                addOnScrollListener(endlessScrollListener)
            }

            recyclerCollection.apply {
                adapter = searchCollectionAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(decoration)
            }
        }

        viewBinding.searchSwipeLayout.setOnRefreshListener(this@SearchFragment)

        viewModel.apply {

            queryString.value = arguments?.getString(QUERY_STRING)

            listSearchCollection.observe(this@SearchFragment, Observer {
                searchCollectionAdapter.submitList(it)
            })

            listSearchPhotos.observe(this@SearchFragment, Observer {
                searchPhotoAdapter.submitList(it)
            })
            firstLoad(query)

            isLoadMore.observe(this@SearchFragment, Observer {
                if (it == true) return@Observer
                endlessScrollListener.isLoading = it
            })

            isRefresh.observe(this@SearchFragment, Observer {
                viewBinding.searchSwipeLayout.apply { isRefreshing = it == true }
            })

            errorMessage.observe(this@SearchFragment, Observer {
                DialogUtils.showToast(context, it)
            })
        }
    }

    private fun goToDetailFragment(it: PhotoItem) {
        val photoDetailFragment = PhotoDetailFragment.newInstance(it)
        replaceFragment(
            R.id.frame_layout, photoDetailFragment, PhotoDetailFragment.TAG, true
        )
    }

    private fun goToCollectionDetailFragment(it: CollectionItem) {
        val collectionDetailFragment = CollectionDetailFragment.newInstance(it.id, it.title ?: return)
        replaceFragment(
            R.id.frame_layout, collectionDetailFragment, CollectionDetailFragment.TAG, true
        )
    }

    override fun onRefresh() {
        viewModel.refresh(viewModel.queryString.value ?: return)
    }
}
