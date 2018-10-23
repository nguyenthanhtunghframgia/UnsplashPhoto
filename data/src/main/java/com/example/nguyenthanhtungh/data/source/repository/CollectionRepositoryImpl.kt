package com.example.nguyenthanhtungh.data.source.repository

import com.example.nguyenthanhtungh.data.model.CollectionEntityMapper
import com.example.nguyenthanhtungh.data.source.CollectionDataSource
import com.example.nguyenthanhtungh.domain.model.Collection
import com.example.nguyenthanhtungh.domain.repository.CollectionRepository
import io.reactivex.Single

class CollectionRepositoryImpl(
    val remote: CollectionDataSource.Remote,
    val local: CollectionDataSource.Local,
    private val collectionEntityMapper: CollectionEntityMapper
) : CollectionRepository {

    override fun getListCollection(): Single<List<Collection>> {
        return remote.getListCollection().map {
            it.map { collectionEntityMapper.mapToDomain(it) }
        }
    }
}
