package com.example.nguyenthanhtungh.data.source.repository

import com.example.nguyenthanhtungh.data.model.PhotoEntityMapper
import com.example.nguyenthanhtungh.data.source.remote.network.ApiService
import com.example.nguyenthanhtungh.domain.model.Photo
import com.example.nguyenthanhtungh.domain.repository.PhotoRepository
import io.reactivex.Single

class PhotoRepositoryImpl(
    private val apiService: ApiService,
    private val photoEntityMapper: PhotoEntityMapper
) : PhotoRepository {
    override fun searchListPhoto(query: String, page: Int): Single<List<Photo>> {
        return apiService.getSearchPhotos(query, page).map { listPhotos ->
            listPhotos.listPhotos?.map { photoEntityMapper.mapToDomain(it) }
        }
    }

    override fun getListPhoto(id: String, page: Int): Single<List<Photo>> {
        return apiService.getCollectionPhotos(id, page).map { listPhotos ->
            listPhotos.map { photoEntityMapper.mapToDomain(it) }
        }
    }
}
