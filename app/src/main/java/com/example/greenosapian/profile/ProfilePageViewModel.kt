package com.example.greenosapian.profile

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greenosapian.network.ElasticApi
import com.example.greenosapian.network.ElasticApiService
import com.example.greenosapian.network.User
import com.example.greenosapian.network.UserLocation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class ProfilePageViewModel(private val app: Application) : AndroidViewModel(app) {
    private val TAG = "ProfilePageViewModel"

    val profilePicUri = MutableLiveData<Uri>()

    fun insertUser(name: String, address: String, location: Location) {
//        val phoneNumber = Firebase.auth.currentUser?.phoneNumber
        val phoneNumber = "1234567890"
        var profilePicString : String? = null

        profilePicUri.value?.let { uri ->
            val input = app.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(input, null, BitmapFactory.Options())?.let {
                val byteArrayOutputStream = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
                profilePicString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            }

        }


        if (!phoneNumber.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.Main).launch {
                val response = ElasticApi.retrofitService.insertUser(
                    User(
                        name,
                        address,
                        phoneNumber,
                        UserLocation(location.latitude.toString(), location.longitude.toString()),
                        profilePicString
                    )
                )

                Log.i(TAG, "RESPONSE: ${response}")
            }
        } else {
            Log.i(TAG, "phone number not found")
        }
    }
}