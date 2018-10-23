package com.example.nguyenthanhtungh.domain.usecase.collection

import com.example.nguyenthanhtungh.domain.model.Collection
import com.example.nguyenthanhtungh.domain.repository.CollectionRepository
import com.example.nguyenthanhtungh.domain.usecase.UseCase
import io.reactivex.Single

class CollectionUseCase(private val collectionRepository: CollectionRepository) :
    UseCase<CollectionUseCase.Param, Single<List<Collection>>>() {

    override fun createObservable(param: Param?): Single<List<Collection>> {
        param?.let { return collectionRepository.getListCollection() }
        return Single.error(Throwable("Invalid Param"))
    }

    override fun onCleared() {
    }

    class Param()
}
