package com.example.nguyenthanhtungh.unsplashphoto.model

import android.os.Parcelable
import com.example.nguyenthanhtungh.domain.model.User
import com.example.nguyenthanhtungh.unsplashphoto.base.ItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.base.ModelItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserItem(
    val id: String? = null,
    val name: String? = null,
    val location: String? = null,
    val bio: String? = null
) : ModelItem(), Parcelable

class UserItemMapper : ItemMapper<User, UserItem> {

    override fun mapToPresentation(model: User): UserItem = UserItem(
        id = model.id,
        name = model.name,
        location = model.location,
        bio = model.bio
    )

    override fun mapToDomain(modelItem: UserItem): User = User(
        id = modelItem.id,
        name = modelItem.name,
        location = modelItem.location,
        bio = modelItem.bio
    )
}
