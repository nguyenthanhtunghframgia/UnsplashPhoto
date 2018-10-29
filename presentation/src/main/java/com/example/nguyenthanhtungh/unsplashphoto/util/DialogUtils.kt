package com.example.nguyenthanhtungh.unsplashphoto.util

import android.content.Context
import android.widget.Toast

object DialogUtils {

    open fun showToast(context: Context?, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
