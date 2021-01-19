package com.example.greenosapian.profile

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greenosapian.calculateInSampleSize
import com.example.greenosapian.network.ElasticApi
import com.example.greenosapian.network.User
import com.example.greenosapian.network.UserLocation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream

class ProfilePageViewModel(private val app: Application) : AndroidViewModel(app) {
    private val TAG = "ProfilePageViewModel"

    val profilePicUri = MutableLiveData<Uri>()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToHomeFragment = MutableLiveData<String>()
    val navigateToHomeFragment: LiveData<String>
        get() = _navigateToHomeFragment

    fun navigationToHomeFragmentComplete() {
        _navigateToHomeFragment.value = null
    }

    fun insertUser(name: String, address: String, location: Location?) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {


                val phoneNumber = Firebase.auth.currentUser?.phoneNumber
                val profilePicString: String? = getBase64FromUri(profilePicUri.value)

                if (!phoneNumber.isNullOrEmpty()) {
                    val response = ElasticApi.retrofitService.insertUser(
                        phoneNumber,
                        User(
                            name,
                            address,
                            phoneNumber,
                            UserLocation(
                                location?.latitude?.toString() ?: "",
                                location?.longitude?.toString() ?: ""
                            ),
                            profilePicString
                        )
                    )
                    if (response._shards?.successful == 1) {
                        withContext(Dispatchers.Main) {
                            _navigateToHomeFragment.value = phoneNumber
                        }
                    }
                    Log.i(TAG, "RESPONSE: ${response}")
                } else {
                    Log.i(TAG, "Phone number is null")
                }
            }
        }
    }

    private suspend fun getBase64FromUri(uri: Uri?): String? = withContext(Dispatchers.IO) {
        var base64String: String? = null
        uri?.let { uri ->
            var input = app.contentResolver.openInputStream(uri)
            val options = BitmapFactory.Options()

            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(input, null, options)
            options.calculateInSampleSize(200, 200)
            options.inJustDecodeBounds = false

            input?.close()
            input = app.contentResolver.openInputStream(uri)

            BitmapFactory.decodeStream(input, null, options)?.let {
                val byteArrayOutputStream = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
                base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            }
        }
        base64String
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}