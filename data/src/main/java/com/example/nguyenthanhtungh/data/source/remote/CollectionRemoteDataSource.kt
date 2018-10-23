package com.example.nguyenthanhtungh.data.source.remote

import com.example.nguyenthanhtungh.data.model.CollectionEntity
import com.example.nguyenthanhtungh.data.source.CollectionDataSource
import com.example.nguyenthanhtungh.data.source.remote.network.ApiService
import io.reactivex.Single

class CollectionRemoteDataSource(private val apiService: ApiService) : CollectionDataSource.Remote {

    override fun getListCollection(): Single<List<CollectionEntity>>
            = apiService.getListCollection()
}
