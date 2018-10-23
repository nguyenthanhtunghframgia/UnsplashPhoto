package com.example.nguyenthanhtungh.domain.repository

import com.example.nguyenthanhtungh.domain.model.Collection
import io.reactivex.Single

interface CollectionRepository {
    fun getListCollection(): Single<List<Collection>>
}
