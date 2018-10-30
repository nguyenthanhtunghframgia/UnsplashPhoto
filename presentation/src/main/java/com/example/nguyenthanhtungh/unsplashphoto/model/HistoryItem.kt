package com.example.nguyenthanhtungh.unsplashphoto.model

import android.os.Parcelable
import com.example.nguyenthanhtungh.domain.model.History
import com.example.nguyenthanhtungh.unsplashphoto.base.ItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.base.ModelItem
import kotlinx.android.parcel.Parcelize

@Parcelize
open class HistoryItem(
    val id: Int,
    val query: String? = null
) : ModelItem(), Parcelable

class HistoryItemMapper : ItemMapper<History, HistoryItem> {

    override fun mapToPresentation(model: History): HistoryItem = HistoryItem(
        id = model.id,
        query = model.query
    )

    override fun mapToDomain(modelItem: HistoryItem): History = History(
        id = modelItem.id,
        query = modelItem.query
    )
}
