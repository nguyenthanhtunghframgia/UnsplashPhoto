package com.example.nguyenthanhtungh.unsplashphoto.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.nguyenthanhtungh.unsplashphoto.R

object BindAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, image: String?) {
        Glide.with(imageView.context)
            .load(image)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_launcher_foreground)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
            )
            .into(imageView)
    }

    @BindingAdapter("text")
    @JvmStatic
    fun loadText(textView: TextView, text: String?) {
        textView.setText(text ?: "Unsplash photo")
    }
}
