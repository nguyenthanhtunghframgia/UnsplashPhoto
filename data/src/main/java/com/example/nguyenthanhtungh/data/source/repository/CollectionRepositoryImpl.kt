package com.example.nguyenthanhtungh.data.source.repository

import com.example.nguyenthanhtungh.data.model.CollectionEntityMapper
import com.example.nguyenthanhtungh.data.source.remote.network.ApiService
import com.example.nguyenthanhtungh.domain.model.Collection
import com.example.nguyenthanhtungh.domain.repository.CollectionRepository
import io.reactivex.Single

class CollectionRepositoryImpl(
    private val apiService: ApiService,
    private val collectionEntityMapper: CollectionEntityMapper
) : CollectionRepository {

    override fun getListCollection(): Single<List<Collection>> {
        return apiService.getListCollection().map { listCollection ->
            listCollection.map { collectionEntityMapper.mapToDomain(it) }
        }
    }
}
