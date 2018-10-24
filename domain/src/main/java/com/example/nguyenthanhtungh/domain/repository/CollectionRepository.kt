package com.example.nguyenthanhtungh.domain.repository

import com.example.nguyenthanhtungh.domain.model.Collection
import io.reactivex.Single

interface CollectionRepository {
    fun getListCollection(page: Int): Single<List<Collection>>

    fun searchListCollection(query: String, page: Int): Single<List<Collection>>
}
