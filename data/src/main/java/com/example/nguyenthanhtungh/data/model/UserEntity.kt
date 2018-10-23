package com.example.nguyenthanhtungh.data.model

import com.example.nguyenthanhtungh.data.base.EntityMapper
import com.example.nguyenthanhtungh.data.base.ModelEntity
import com.example.nguyenthanhtungh.domain.model.User
import com.google.gson.annotations.SerializedName

class UserEntity(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("bio")
    val bio: String? = null
) : ModelEntity()

class UserEntityMapper : EntityMapper<User?, UserEntity?> {

    override fun mapToDomain(entity: UserEntity?): User? = User(
        id = entity?.id,
        name = entity?.name,
        location = entity?.location,
        bio = entity?.bio
    )

    override fun mapToEntity(model: User?): UserEntity? = UserEntity(
        id = model?.id,
        name = model?.name,
        location = model?.location,
        bio = model?.bio
    )
}
