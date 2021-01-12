package com.example.greenosapian.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://54.91.137.181:9200/test003/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ElasticApiService {
    @GET("users/{user_id}/_source")
    suspend fun getUser(@Path(value = "user_id") userId: String) : User

    @Headers("Content-Type: application/json")
    @POST("users")
    suspend fun insertUser(@Body user: User):NetworkResponse
}

object ElasticApi {
    val retrofitService: ElasticApiService by lazy {
        retrofit.create(ElasticApiService::class.java)
    }
}