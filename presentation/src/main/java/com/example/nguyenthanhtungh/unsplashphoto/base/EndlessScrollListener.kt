package com.example.nguyenthanhtungh.unsplashphoto.base

import androidx.recyclerview.widget.RecyclerView
import com.example.nguyenthanhtungh.unsplashphoto.util.TOTAL_ITEM_PER_PAGE

class EndlessScrollListener(val onLoadMore: () -> Unit) : RecyclerView.OnScrollListener() {
    var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItem = recyclerView.layoutManager?.itemCount ?: 0
        if (!isLoading && !recyclerView.canScrollVertically(1) && totalItem >= TOTAL_ITEM_PER_PAGE) {
            isLoading = true
            onLoadMore.invoke()
        }
    }
}
