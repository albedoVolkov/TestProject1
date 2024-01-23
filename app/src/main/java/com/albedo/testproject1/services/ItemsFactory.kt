package com.albedo.testproject1.services

import android.util.Log
import com.albedo.testproject1.data.models.CoordinateUIState
import com.albedo.testproject1.data.models.PinDataUIState
import com.albedo.testproject1.data.models.PinUIState
import com.albedo.testproject1.data.models.ServiceUIState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemsFactory @Inject constructor() {

    private val TAG = "ItemsFactory"

    @Volatile
    private var serviceId : Int = 100

    @Volatile
    private var coordinateId : Int = 1000


    suspend fun createService(name : String,number : Int,quantity : Int):Result<ServiceUIState?>{
        try {
            serviceId += 1
            return Result.success(ServiceUIState(serviceId.toString(), number.toLong(),quantity.toLong(),name))
        }catch (e : Exception){
            Log.d(TAG, "createService: error - ${e.message}")
            return Result.failure(e)
        }
    }

    suspend fun createPin(item : PinDataUIState):Result<PinUIState?>{
        try {
            coordinateId += 1
            return Result.success(
                PinUIState(
                    item.id,
                    item.service,
                    CoordinateUIState(
                        coordinateId.toString(),
                        item.coordinates.lat,
                        item.coordinates.lng
                    )
                )
            )
        }catch (e : Exception){
            Log.d(TAG, "createService: error - ${e.message}")
            return Result.failure(e)
        }
    }
}