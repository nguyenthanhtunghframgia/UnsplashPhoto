package com.example.nguyenthanhtungh.unsplashphoto.model

import android.os.Parcelable
import com.example.nguyenthanhtungh.unsplashphoto.base.ModelItem
import kotlinx.android.parcel.Parcelize

@Parcelize
open class UserItem(
    val id: String? = null,
    val name: String? = null,
    val location: String? = null,
    val bio: String? = null
) :ModelItem(),  Parcelable
