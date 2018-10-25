package com.example.nguyenthanhtungh.unsplashphoto.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<out T : ViewDataBinding> constructor(val binding: T) : RecyclerView.ViewHolder(binding.root)
