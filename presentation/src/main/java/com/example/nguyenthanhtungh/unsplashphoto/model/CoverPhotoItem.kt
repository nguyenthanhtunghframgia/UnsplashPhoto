package com.example.nguyenthanhtungh.unsplashphoto.model

import android.os.Parcelable
import com.example.nguyenthanhtungh.unsplashphoto.base.ModelItem
import kotlinx.android.parcel.Parcelize

@Parcelize
class CoverPhotoItem(
    val urlsItem: UrlsItem? = null
) : ModelItem(), Parcelable
