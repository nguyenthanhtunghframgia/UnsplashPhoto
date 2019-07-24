package com.example.nguyenthanhtungh.unsplashphoto.model

import android.os.Parcelable
import com.example.nguyenthanhtungh.domain.model.Links
import com.example.nguyenthanhtungh.unsplashphoto.base.ItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.base.ModelItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LinksItem(
    val self: String? = null,
    val html: String? = null,
    val download: String? = null,
    val download_location: String? = null
) : ModelItem(), Parcelable

class LinksItemMapper : ItemMapper<Links, LinksItem> {

    override fun mapToPresentation(model: Links): LinksItem = LinksItem(
        self = model.self,
        html = model.html,
        download = model.download,
        download_location = model.download_location
    )

    override fun mapToDomain(modelItem: LinksItem): Links = Links(
        self = modelItem.self,
        html = modelItem.html,
        download = modelItem.download,
        download_location = modelItem.download_location
    )
}
