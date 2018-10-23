package com.example.nguyenthanhtungh.data.source.repository

import com.example.nguyenthanhtungh.data.model.PhotoEntityMapper
import com.example.nguyenthanhtungh.data.source.PhotoDataSource
import com.example.nguyenthanhtungh.domain.model.Photo
import com.example.nguyenthanhtungh.domain.repository.PhotoRepository
import io.reactivex.Single

class PhotoRepositoryImpl(
    val remote: PhotoDataSource.Remote,
    val local: PhotoDataSource.Local,
    private val photoEntityMapper: PhotoEntityMapper
) : PhotoRepository {
    override fun getListPhoto(id: String): Single<List<Photo>> {
        return remote.getListPhoto(id).map {
            it.map { photoEntityMapper.mapToDomain(it) }
        }
    }
}
