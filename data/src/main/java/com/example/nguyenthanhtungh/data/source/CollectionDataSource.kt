package com.example.nguyenthanhtungh.data.source

import com.example.nguyenthanhtungh.data.model.CollectionEntity
import io.reactivex.Single

interface CollectionDataSource {
    interface Remote {
        fun getListCollection(): Single<List<CollectionEntity>>
    }

    interface Local {
        //todo
    }
}
