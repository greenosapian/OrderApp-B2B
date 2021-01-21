package com.example.greenosapian

import android.graphics.BitmapFactory
import com.example.greenosapian.network.ElasticApi
import retrofit2.HttpException
import java.lang.Exception

fun BitmapFactory.Options.calculateInSampleSize(
    reqWidth: Int,
    reqHeight: Int
) {
    // Raw height and width of image
    val height = outHeight
    val width = outWidth
    var sampleSize = 1

    if (reqHeight <= 0 || reqWidth <= 0) {
        inSampleSize = sampleSize
    }

    if (height > reqHeight || width > reqWidth) {

        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / sampleSize >= reqHeight && halfWidth / sampleSize >= reqWidth) {
            sampleSize *= 2
        }
    }

    inSampleSize = sampleSize
}

suspend fun doesUserExist(phoneNumber: String): Boolean = try {
    ElasticApi.retrofitService.getUser(phoneNumber)
    true
} catch (e: HttpException) {
    e.code() != 404
}