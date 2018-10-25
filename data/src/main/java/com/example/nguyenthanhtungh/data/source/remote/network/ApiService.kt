package com.example.nguyenthanhtungh.data.source.remote.network

import com.example.nguyenthanhtungh.data.*
import com.example.nguyenthanhtungh.data.model.CollectionEntity
import com.example.nguyenthanhtungh.data.model.PhotoEntity
import com.example.nguyenthanhtungh.data.source.remote.response.CollectionResponse
import com.example.nguyenthanhtungh.data.source.remote.response.PhotoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(COLLECTION_LIST)
    fun getListCollection(
        @Query(PATH_PAGE) page: Int
    ): Single<List<CollectionEntity>>

    @GET(COLLECTION_PHOTOS)
    fun getCollectionPhotos(
        @Path(PATH_ID) id: String,
        @Query(PATH_PAGE) page: Int
    ): Single<List<PhotoEntity>>

    @GET(SEARCH_COLLECTION_LIST)
    fun getSearchCollections(
        @Query(PATH_QUERY) query: String,
        @Query(PATH_PAGE) page: Int
    ): Single<CollectionResponse>

    @GET(SEARCH_PHOTOS)
    fun getSearchPhotos(
        @Query(PATH_QUERY) query: String,
        @Query(PATH_PAGE) page: Int
    ): Single<PhotoResponse>
}
