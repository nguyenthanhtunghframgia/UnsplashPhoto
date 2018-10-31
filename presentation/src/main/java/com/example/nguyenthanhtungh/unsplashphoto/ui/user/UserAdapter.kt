package com.example.nguyenthanhtungh.unsplashphoto.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.nguyenthanhtungh.unsplashphoto.R
import com.example.nguyenthanhtungh.unsplashphoto.base.BaseRecyclerAdapter
import com.example.nguyenthanhtungh.unsplashphoto.databinding.HistoryItemBinding
import com.example.nguyenthanhtungh.unsplashphoto.model.HistoryItem

class UserAdapter(val onItemClick: (HistoryItem) -> Unit) : BaseRecyclerAdapter<HistoryItem>(
    object : DiffUtil.ItemCallback<HistoryItem>() {

        override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
) {
    override fun bindFirstTime(viewBinding: ViewDataBinding) {
        if (viewBinding is HistoryItemBinding) viewBinding.history?.let { onItemClick.invoke(it) }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int?): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.history_item, parent, false
        )
    }

    override fun bind(binding: ViewDataBinding, item: HistoryItem) {
        if (binding is HistoryItemBinding) binding.history = item
    }
}
