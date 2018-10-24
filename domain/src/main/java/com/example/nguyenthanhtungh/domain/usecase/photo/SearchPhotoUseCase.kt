package com.example.nguyenthanhtungh.domain.usecase.photo

import com.example.nguyenthanhtungh.domain.model.Photo
import com.example.nguyenthanhtungh.domain.repository.PhotoRepository
import com.example.nguyenthanhtungh.domain.usecase.UseCase
import io.reactivex.Single

class SearchPhotoUseCase(private val photoRepository: PhotoRepository) :
    UseCase<SearchPhotoUseCase.Param, Single<List<Photo>>>() {

    override fun createObservable(param: Param?): Single<List<Photo>> {
        param?.let { return photoRepository.searchListPhoto(param.query, param.page) }
        return Single.error(Throwable("Invalid Param"))
    }

    override fun onCleared() {
    }

    class Param(val query: String, val page: Int)
}
