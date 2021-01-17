package com.example.greenosapian.network

import com.squareup.moshi.Json

data class VegetablesResponse(
    val took:Long,
    @Json(name="hits") val outerHits : HitsOuter
)

data class HitsOuter(
    val total:Long,
    @Json(name="max_score")val maxScore:Float,
    @Json(name="hits")val vegies:List<NetworkVegie>
)

data class NetworkVegie(
    @Json(name="_id")val id:String,
    @Json(name="_source") val value: VegValue
)

data class VegValue(
    @Json(name = "name") val name: String,
    @Json(name = "image_url") val imageUrl:String,
    @Json(name = "price") val price:Long
)