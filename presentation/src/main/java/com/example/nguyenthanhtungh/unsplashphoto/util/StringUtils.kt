package com.example.nguyenthanhtungh.unsplashphoto.util

import android.os.Environment


object StringUtils {
    fun getDownloadPath(): String {
        var builder = StringBuilder()
        builder = builder
            .append(Environment.getExternalStorageDirectory())
            .append(DIVIDE)
            .append(Environment.DIRECTORY_DOWNLOADS)
            .append(DIVIDE)
        return builder.toString()
    }
}
