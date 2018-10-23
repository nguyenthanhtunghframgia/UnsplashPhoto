package com.example.nguyenthanhtungh.unsplashphoto.model

import android.os.Parcelable
import com.example.nguyenthanhtungh.unsplashphoto.base.ModelItem
import kotlinx.android.parcel.Parcelize

@Parcelize
class CollectionItem(
    val id: String? = null,
    val title: String? = null,
    val totalPhoto: Int? = null,
    val coverPhotoItem: CoverPhotoItem? = null
) : ModelItem(), Parcelable
