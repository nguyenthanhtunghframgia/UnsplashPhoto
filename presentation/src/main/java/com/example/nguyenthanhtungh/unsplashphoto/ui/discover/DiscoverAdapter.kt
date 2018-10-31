package com.example.nguyenthanhtungh.unsplashphoto.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseRecyclerAdapter
import com.example.nguyenthanhtungh.unsplashphoto.databinding.PhotoItemBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.PhotoItem

class DiscoverAdapter(val onItemClick: (PhotoItem) -> Unit) : BaseRecyclerAdapter<PhotoItem>(
    object : DiffUtil.ItemCallback<PhotoItem>() {

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
) {
    override fun bindFirstTime(viewBinding: ViewDataBinding) {
        if (viewBinding is PhotoItemBinding) viewBinding.photo?.let { onItemClick.invoke(it) }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int?): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.photo_item, parent, false
        )
    }

    override fun bind(binding: ViewDataBinding, item: PhotoItem) {
        if (binding is PhotoItemBinding) binding.photo = item
    }
}
