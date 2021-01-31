package com.example.greenosapian.homepage

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.annotation.WorkerThread
import androidx.core.content.FileProvider
import com.example.greenosapian.database.Dao
import com.example.greenosapian.network.ElasticApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File

class HomePageRepository(dao: Dao) {

    @WorkerThread
    suspend fun getProfilePicUri(app: Application): Uri? = withContext(Dispatchers.IO){
        val user = ElasticApi.retrofitService.getUser(Firebase.auth.currentUser?.phoneNumber!!)

        if (user.profileImage.isNullOrBlank())
            return@withContext null


        val imageBytes = Base64.decode(user.profileImage, 0)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        if(bitmap != null){
            val file = File(app.applicationContext.filesDir , "profile_pic")
            file.outputStream().use {
                it.write(bytes.toByteArray())
            }
            return@withContext FileProvider.getUriForFile(
                    app.applicationContext,
                    app.applicationContext.packageName + ".provider",
                    file
                )
        }

        null
    }

}