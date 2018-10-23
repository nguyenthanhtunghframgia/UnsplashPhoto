package com.example.nguyenthanhtungh.data.source.remote.network

import com.example.nguyenthanhtungh.data.COLLECTION_LIST
import com.example.nguyenthanhtungh.data.COLLECTION_PHOTOS
import com.example.nguyenthanhtungh.data.PATH_ID
import com.example.nguyenthanhtungh.data.model.CollectionEntity
import com.example.nguyenthanhtungh.data.model.PhotoEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(COLLECTION_LIST)
    fun getListCollection(): Single<List<CollectionEntity>>

    @GET(COLLECTION_PHOTOS)
    fun getCollectionPhotos(@Path(PATH_ID) id: String): Single<List<PhotoEntity>>
}
