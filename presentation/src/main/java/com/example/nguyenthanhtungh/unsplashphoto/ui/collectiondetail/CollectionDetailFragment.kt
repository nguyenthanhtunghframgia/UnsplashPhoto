package com.example.nguyenthanhtungh.unsplashphoto.ui.collectiondetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseFragment
import com.example.nguyenthanhtungh.unsplashphoto.base.EndlessScrollListener
import com.example.nguyenthanhtungh.unsplashphoto.base.RecyclerItemDecoration
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentCollectionDetailBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem
import com.example.nguyenthanhtungh.unsplashphoto.ui.main.MainActivity
import com.example.nguyenthanhtungh.unsplashphoto.ui.photodetail.PhotoDetailFragment
import com.example.nguyenthanhtungh.unsplashphoto.util.ITEM_DECORATION
import com.example.nguyenthanhtungh.unsplashphoto.util.SPAN_COUNT
import org.koin.android.viewmodel.ext.android.viewModel

class CollectionDetailFragment : BaseFragment<FragmentCollectionDetailBinding, CollectionDetailViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val COLLECTION_ID = "COLLECTION_ID"
        const val COLLECTION_TITLE = "COLLECTION_TITLE"
        const val TAG = "CollectionDetailFragment"
        fun newInstance(id: String, title: String) = CollectionDetailFragment().apply {
            arguments = Bundle().apply {
                putString(COLLECTION_ID, id)
                putString(COLLECTION_TITLE, title)
            }
        }
    }

    override val layoutId = R.layout.fragment_collection_detail
    override val viewModel by viewModel<CollectionDetailViewModel>()
    override val bindingVariable: Int = BR.fragmentCollectionDetailViewModel

    override fun initComponent(viewDataBinding: FragmentCollectionDetailBinding) {
        val id = arguments?.getString(COLLECTION_ID) ?: return

        val collectionDetailAdapter = CollectionDetailAdapter(
            onItemClick = {
                goToDetailFragment(it)
            }
        )

        viewDataBinding.onBackPress = View.OnClickListener {
            if (activity is MainActivity) {
                (activity as MainActivity).apply {
                    onBackPressed()
                }
            }
        }

        arguments?.apply {
            getString(COLLECTION_ID)?.apply {
                viewModel.collectionId.value = this
            }

            getString(COLLECTION_TITLE)?.apply {
                viewModel.collectionTitle.value = this
            }
        }

        val endlessScrollListener = EndlessScrollListener { viewModel.onLoadMore(id) }
        val decoration = RecyclerItemDecoration(ITEM_DECORATION)
        viewDataBinding.apply {
            recyclerCollectionPhotos.apply {
                adapter = collectionDetailAdapter
                layoutManager = GridLayoutManager(context, SPAN_COUNT)
                addItemDecoration(decoration)
                addOnScrollListener(endlessScrollListener)
            }
        }
        viewBinding.swipeLayout.setOnRefreshListener(this@CollectionDetailFragment)

        viewModel.apply {
            listCollectionPhotoItem.observe(this@CollectionDetailFragment, Observer {
                collectionDetailAdapter.submitList(it)
            })
            firstLoad(id)

            isLoadMore.observe(this@CollectionDetailFragment, Observer {
                if (it == null) return@Observer
                endlessScrollListener.isLoading = it
            })

            isRefresh.observe(this@CollectionDetailFragment, Observer {
                viewBinding.swipeLayout.apply { isRefreshing = it == true }
            })

            errorMessage.observe(this@CollectionDetailFragment, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun goToDetailFragment(it: PhotoItem) {
        if (activity is MainActivity)
            (activity as MainActivity).apply {
                val photoDetailFragment = PhotoDetailFragment.newInstance(it)
                replaceFragment(
                    photoDetailFragment, R.id.frame_layout, PhotoDetailFragment.TAG, true
                )
            }
    }

    override fun onRefresh() {
        viewModel.refresh(viewModel.collectionId.value ?: return)
    }
}
