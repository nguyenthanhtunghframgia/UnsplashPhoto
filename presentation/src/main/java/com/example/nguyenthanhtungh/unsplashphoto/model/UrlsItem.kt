package com.example.nguyenthanhtungh.unsplashphoto.model

import android.os.Parcelable
import com.example.nguyenthanhtungh.domain.model.Urls
import com.example.nguyenthanhtungh.unsplashphoto.base.ItemMapper
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

class UrlsItemMapper : ItemMapper<Urls, UrlsItem> {

    override fun mapToPresentation(model: Urls): UrlsItem = UrlsItem(
        small = model.small,
        thumb = model.thumb,
        raw = model.raw,
        regular = model.regular,
        full = model.full
    )

    override fun mapToDomain(modelItem: UrlsItem): Urls = Urls(
        small = modelItem.small,
        thumb = modelItem.thumb,
        raw = modelItem.raw,
        regular = modelItem.regular,
        full = modelItem.full
    )
}
