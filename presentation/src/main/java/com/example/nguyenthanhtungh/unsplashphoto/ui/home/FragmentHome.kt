package com.example.nguyenthanhtungh.unsplashphoto.ui.home

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nguyenthanhtungh.unsplashphoto.BR
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseFragment
import com.example.nguyenthanhtungh.unsplashphoto.base.RecyclerItemDecoration
import com.example.nguyenthanhtungh.unsplashphoto.databinding.FragmentHomeBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItem
import com.example.nguyenthanhtungh.unsplashphoto.util.ITEM_DECORATION
import com.example.nguyenthanhtungh.unsplashphoto.util.SPAN_COUNT
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, FragmentHomeViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TAG = "HomeFragment"
        fun newInstance() = HomeFragment()
    }

    override val layoutId = R.layout.fragment_home
    override val viewModel by viewModel<FragmentHomeViewModel>()
    override val bindingVariable: Int = BR.fragmentHomeViewModel

    override fun initComponent(viewDataBinding: FragmentHomeBinding) {

        val fragmentHomeAdapter = FragmentHomeAdapter(
            onItemClick = {
                goToDetailFragment(it)
            }
        )

        val decoration = RecyclerItemDecoration(ITEM_DECORATION)
        viewDataBinding.apply {
            recyclerCollection.apply {
                adapter = fragmentHomeAdapter
                layoutManager = GridLayoutManager(context, SPAN_COUNT)
                addItemDecoration(decoration)
            }
        }
        viewBinding.swipeLayout.setOnRefreshListener(this@HomeFragment)

        viewModel.apply {
            listCollectionItem.observe(this@HomeFragment, Observer {
                fragmentHomeAdapter.submitList(it)
            })
            getListCollectionItem()

            isRefresh.observe(this@HomeFragment, Observer {
                viewBinding.swipeLayout.apply { isRefreshing = it == true }
            })

            errorMessage.observe(this@HomeFragment, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun goToDetailFragment(it: CollectionItem) {
    }

    override fun onRefresh() {
        viewModel.refreshData()
    }
}
