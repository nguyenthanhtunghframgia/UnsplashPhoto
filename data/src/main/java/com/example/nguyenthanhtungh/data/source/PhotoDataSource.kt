package com.example.nguyenthanhtungh.data.source

import com.example.nguyenthanhtungh.data.model.PhotoEntity
import io.reactivex.Single

interface PhotoDataSource {
    interface Remote {
        fun getListPhoto(id: String): Single<List<PhotoEntity>>
    }

    interface Local {
        //todo
    }
}
