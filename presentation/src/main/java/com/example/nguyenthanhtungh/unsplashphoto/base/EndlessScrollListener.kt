package com.example.nguyenthanhtungh.unsplashphoto.base

import androidx.recyclerview.widget.RecyclerView

class EndlessScrollListener(val onLoadMore: () -> Unit) : RecyclerView.OnScrollListener() {
    var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!isLoading && !recyclerView.canScrollVertically(1)) {
            isLoading = true
            onLoadMore.invoke()
        }
    }
}
