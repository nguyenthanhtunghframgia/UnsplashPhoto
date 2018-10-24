package com.example.nguyenthanhtungh.unsplashphoto.model

import android.os.Parcelable
import com.example.nguyenthanhtungh.unsplashphoto.base.ModelItem
import kotlinx.android.parcel.Parcelize

@Parcelize
class PhotoItem(
    val id: String = "",
    val description: String? = null,
    val user: UserItem? = null,
    val urls: UrlsItem? = null,
    val width: Int? = null,
    val height: Int? = null
) : ModelItem(), Parcelable
