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
    override fun searchListCollection(query: String, page: Int): Single<List<Collection>> {
        return apiService.getSearchCollections(query, page).map { listCollection ->
            listCollection.listCollection?.map { collectionEntityMapper.mapToDomain(it) }
        }
    }

    override fun getListCollection(page: Int): Single<List<Collection>> {
        return apiService.getListCollection(page).map { listCollection ->
            listCollection.map { collectionEntityMapper.mapToDomain(it) }
        }
    }
}
