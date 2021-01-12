package com.example.greenosapian.network

import android.location.Location
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @Json(name = "name") val name: String,
    @Json(name = "address") val address: String,
    @Json(name = "phone_number") val phoneNumber: String,
    @Json(name="location") val location: UserLocation,
    @Json(name = "profile_img") val profileImage: String? = null
):Parcelable

@Parcelize
data class UserLocation(
    @Json(name = "latitude") val latitude: String,
    @Json(name = "longitude") val longitude: String
):Parcelable

data class NetworkResponse(
    @Json(name = "_index") val _index: String? = null,
    @Json(name = "_type") val _type: String? = null,
    @Json(name = "_id") val _id: String? = null,
    @Json(name = "_version") val version: Int? = null,
    @Json(name = "result") val result: String? = null,
    @Json(name = "_seq_no") val _seq_no: Int? = null,
    @Json(name = "_primary_term") val _primary_term: Int? = null,
    @Json(name = "_shards") val _shards: Shards? = null

)

data class Shards(
    @Json(name = "total") val total: Int? = null,
    @Json(name = "successful") val successful: Int? = null,
    @Json(name = "failed") val failed: Int? = null
)

