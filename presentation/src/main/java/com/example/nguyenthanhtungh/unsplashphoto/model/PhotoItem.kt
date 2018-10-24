package com.example.nguyenthanhtungh.unsplashphoto.model

import android.os.Parcelable
import com.example.nguyenthanhtungh.domain.model.Photo
import com.example.nguyenthanhtungh.unsplashphoto.base.ItemMapper
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

class PhotoItemMapper(
    private val userItemMapper: UserItemMapper,
    private val urlsItemMapper: UrlsItemMapper
) : ItemMapper<Photo, PhotoItem> {

    override fun mapToPresentation(model: Photo): PhotoItem {
        return PhotoItem(
            id = model.id,
            description = model.description,
            user = userItemMapper.mapToPresentation(model.user ?: return PhotoItem()),
            urls = urlsItemMapper.mapToPresentation(model.urls ?: return PhotoItem()),
            width = model.width,
            height = model.height
        )
    }

    override fun mapToDomain(modelItem: PhotoItem): Photo {
        return Photo(
            id = modelItem.id,
            description = modelItem.description,
            user = userItemMapper.mapToDomain(modelItem.user ?: return Photo()),
            urls = urlsItemMapper.mapToDomain(modelItem.urls ?: return Photo()),
            width = modelItem.width,
            height = modelItem.height
        )
    }
}
