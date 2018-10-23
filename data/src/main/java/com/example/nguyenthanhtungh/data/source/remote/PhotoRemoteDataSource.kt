package com.example.nguyenthanhtungh.data.source.remote

import com.example.nguyenthanhtungh.data.model.PhotoEntity
import com.example.nguyenthanhtungh.data.source.PhotoDataSource
import com.example.nguyenthanhtungh.data.source.remote.network.ApiService
import io.reactivex.Single

class PhotoRemoteDataSource(private val apiService: ApiService) : PhotoDataSource.Remote {

    override fun getListPhoto(id: String): Single<List<PhotoEntity>>
            = apiService.getCollectionPhotos(id)
}
