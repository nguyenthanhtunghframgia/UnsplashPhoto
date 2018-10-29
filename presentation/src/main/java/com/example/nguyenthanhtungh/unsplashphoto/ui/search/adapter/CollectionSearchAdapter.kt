package com.example.nguyenthanhtungh.unsplashphoto.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseRecyclerAdapter
import com.example.nguyenthanhtungh.unsplashphoto.databinding.CollectionItemSearchBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItem

class CollectionSearchAdapter(val onItemClick: (CollectionItem) -> Unit) : BaseRecyclerAdapter<CollectionItem>(
    object : DiffUtil.ItemCallback<CollectionItem>() {

        override fun areContentsTheSame(oldItem: CollectionItem, newItem: CollectionItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: CollectionItem, newItem: CollectionItem): Boolean {
            return oldItem.title == newItem.title
        }
    }
) {
    override fun bindFirstTime(viewBinding: ViewDataBinding) {
        if (viewBinding is CollectionItemSearchBinding) viewBinding.collection?.let { onItemClick.invoke(it) }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int?): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.collection_item_search, parent, false
        )
    }

    override fun bind(binding: ViewDataBinding, item: CollectionItem) {
        if (binding is CollectionItemSearchBinding) binding.collection = item
    }
}
