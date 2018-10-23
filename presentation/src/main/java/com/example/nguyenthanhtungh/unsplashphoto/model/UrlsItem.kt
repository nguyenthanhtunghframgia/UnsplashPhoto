package com.example.nguyenthanhtungh.unsplashphoto.model

import android.os.Parcelable
import com.example.nguyenthanhtungh.unsplashphoto.base.ModelItem
import kotlinx.android.parcel.Parcelize

@Parcelize
open class UrlsItem(
    val small: String? = null,
    val thumb: String? = null,
    val raw: String? = null,
    val regular: String? = null,
    val full: String? = null
) : ModelItem(), Parcelable
