package com.example.nguyenthanhtungh.unsplashphoto.model

import android.os.Parcelable
import com.example.nguyenthanhtungh.domain.model.CoverPhoto
import com.example.nguyenthanhtungh.unsplashphoto.base.ItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.base.ModelItem
import kotlinx.android.parcel.Parcelize

@Parcelize
class CoverPhotoItem(
    val urlsItem: UrlsItem? = null
) : ModelItem(), Parcelable

class CoverPhotoItemMapper(
    private val urlsItemMapper: UrlsItemMapper
) : ItemMapper<CoverPhoto, CoverPhotoItem> {

    override fun mapToPresentation(model: CoverPhoto): CoverPhotoItem {
        return CoverPhotoItem(
            urlsItem = urlsItemMapper.mapToPresentation(model.urls ?: return CoverPhotoItem())
        )
    }

    override fun mapToDomain(modelItem: CoverPhotoItem): CoverPhoto {
        return CoverPhoto(
            urls = urlsItemMapper.mapToDomain(modelItem.urlsItem ?: return CoverPhoto())
        )
    }
}
