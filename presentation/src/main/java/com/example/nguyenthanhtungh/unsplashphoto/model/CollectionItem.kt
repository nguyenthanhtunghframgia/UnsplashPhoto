package com.example.nguyenthanhtungh.unsplashphoto.model

import android.os.Parcelable
import com.example.nguyenthanhtungh.domain.model.Collection
import com.example.nguyenthanhtungh.unsplashphoto.base.ItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.base.ModelItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionItem(
    val id: String = "",
    val title: String? = null,
    val totalPhoto: Int? = null,
    val coverPhotoItem: CoverPhotoItem? = null
) : ModelItem(), Parcelable

class CollectionItemMapper(
    private val coverPhotoItemMapper: CoverPhotoItemMapper
) : ItemMapper<Collection, CollectionItem> {

    override fun mapToPresentation(model: Collection): CollectionItem {
        return CollectionItem(
            id = model.id,
            title = model.title,
            totalPhoto = model.totalPhoto,
            coverPhotoItem = coverPhotoItemMapper.mapToPresentation(model.coverPhoto ?: return CollectionItem())
        )
    }

    override fun mapToDomain(modelItem: CollectionItem): Collection {
        return Collection(
            id = modelItem.id,
            title = modelItem.title,
            totalPhoto = modelItem.totalPhoto,
            coverPhoto = coverPhotoItemMapper.mapToDomain(modelItem.coverPhotoItem ?: return Collection())
        )
    }
}
