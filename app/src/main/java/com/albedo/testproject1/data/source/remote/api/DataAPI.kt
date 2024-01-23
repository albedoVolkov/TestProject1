package com.albedo.testproject1.data.source.remote.api

import com.albedo.testproject1.data.models.DataFromServer
import com.albedo.testproject1.data.models.PinUIState
import retrofit2.http.GET

interface DataAPI {

    @GET("21aab51a-9ae2-4d7b-a51b-151e028b5633")
    suspend fun getData(): DataFromServer?
}