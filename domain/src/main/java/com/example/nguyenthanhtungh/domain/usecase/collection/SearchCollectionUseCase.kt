package com.example.nguyenthanhtungh.domain.usecase.collection

import com.example.nguyenthanhtungh.domain.model.Collection
import com.example.nguyenthanhtungh.domain.repository.CollectionRepository
import com.example.nguyenthanhtungh.domain.usecase.UseCase
import io.reactivex.Single

open class SearchCollectionUseCase(private val collectionRepository: CollectionRepository) :
    UseCase<SearchCollectionUseCase.Param, Single<List<Collection>>>() {

    override fun createObservable(param: Param?): Single<List<Collection>> {
        param?.let { return collectionRepository.searchListCollection(param.query, param.page) }
        return Single.error(Throwable("Invalid Param"))
    }

    override fun onCleared() {
    }

    class Param(val query: String, val page: Int)
}
