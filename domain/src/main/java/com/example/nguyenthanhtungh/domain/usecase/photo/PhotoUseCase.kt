package com.example.nguyenthanhtungh.domain.usecase.photo

import com.example.nguyenthanhtungh.domain.model.Photo
import com.example.nguyenthanhtungh.domain.repository.PhotoRepository
import com.example.nguyenthanhtungh.domain.usecase.UseCase
import io.reactivex.Single

open class PhotoUseCase(private val photoRepository: PhotoRepository) : UseCase<PhotoUseCase.Param, Single<List<Photo>>>() {

    override fun createObservable(param: Param?): Single<List<Photo>> {
        param?.let { return photoRepository.getListPhoto(param.id, param.page) }
        return Single.error(Throwable("Invalid Param"))
    }

    override fun onCleared() {
    }

    class Param(val id: String, val page: Int)
}
