package com.albedo.testproject1.data.repository.services

import android.util.Log
import com.albedo.testproject1.data.models.ServiceUIState
import com.albedo.testproject1.data.repository.pins.PinsRepository
import com.albedo.testproject1.data.source.local.services.ServiceLocalDataSource
import com.albedo.testproject1.data.source.remote.RetrofitDataSource
import com.albedo.testproject1.services.ItemsFactory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ServiceRepository @Inject constructor(
    private val factory: ItemsFactory,
    private val remoteSource: RetrofitDataSource,//main source of data
    private val serviceLocalSource: ServiceLocalDataSource,//just holder of data
    private val pinsRepository: PinsRepository,
) : ServiceRepoInterface {

    private val TAG = "ServiceRepository"

    @Volatile
    private var countService : Int = 0

    override suspend fun refreshItemsData() {
        try {
            countService = 0

            val remoteData = remoteSource.dataAPI.getData()
            Log.d(TAG, "refreshItemsData : remoteData = $remoteData")

            if (remoteData != null) {
                val listServices = mutableListOf<ServiceUIState>()

                for(item in remoteData.services) {

                    countService++

                    val newItem = factory.createService(
                        item,
                        countService,
                        pinsRepository.getQuantityOfPinsForService(item)
                    )

                    if(newItem.isSuccess && newItem.getOrNull() != null) {
                        listServices.add(newItem.getOrNull()!!)
                    }else{ Log.d(TAG, "refreshItemsData: error in creating item") }
                }
                serviceLocalSource.updateListItems(listServices)
            }

        } catch (e: Exception) {
            Log.d(TAG, "refreshItemsData: error - ${e.message}")
        }
    }


    override fun getItemListFlow(): Flow<List<ServiceUIState>> = serviceLocalSource.getListItemsFlow()

    override suspend fun getItemList(): List<ServiceUIState> = serviceLocalSource.getListItems()

    override fun getItemByIdFlow(id: String): Flow<ServiceUIState?> = serviceLocalSource.getItemByIdFlow(id)

    override suspend fun getItemById(id: String): ServiceUIState? = serviceLocalSource.getItemById(id)
}