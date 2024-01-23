package com.albedo.testproject1.data.source.remote




import com.albedo.testproject1.data.source.remote.api.DataAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

class RetrofitDataSource @Inject constructor() {

    private val TAG = "RetrofitDataSource"

    private val retrofit by lazy {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val dataAPI : DataAPI by lazy {
        retrofit.create(DataAPI::class.java)
    }


}