package com.albedo.testproject1.data.repository.pins

import android.util.Log
import com.albedo.testproject1.data.models.PinUIState
import com.albedo.testproject1.data.models.ServiceUIState
import com.albedo.testproject1.data.source.local.pin.PinLocalDataSource
import com.albedo.testproject1.data.source.remote.RetrofitDataSource
import com.albedo.testproject1.services.ItemsFactory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PinsRepository @Inject constructor(
    private val factory: ItemsFactory,
    private val remoteSource: RetrofitDataSource,//main source of data
    private val pinLocalSource: PinLocalDataSource,//just holder of data
) : PinRepoInterface {

    companion object{
        const val TAG = "PinRepository"
    }


    override suspend fun refreshItemsData() {
        try{
            val remoteData = remoteSource.dataAPI.getData()
            Log.d(TAG, "refreshItemsData : pins = $remoteData")

            if (remoteData != null) {
                val list = mutableListOf<PinUIState>()

                for(item in remoteData.pins){
                    val itemData = factory.createPin(item)

                    if(itemData.getOrNull() != null && itemData.isSuccess) {
                        list.add(itemData.getOrNull()!!)
                    }
                }

                pinLocalSource.updateListItems(list)
            }
        } catch (e: Exception) {
            Log.d(TAG, "refreshItemsData: error - ${e.message}")
        }
    }


    suspend fun getQuantityOfPinsForService(name : String) : Int{
        try{
            var count = 0

            for(item in pinLocalSource.getListItems()){
                if(item.service == name){ count+=1 }
            }
            return count

        } catch (e: Exception) {
            Log.d(TAG, "refreshItemsData: error - ${e.message}")
        }
        return 0
    }





    override fun getItemListFlow(): Flow<List<PinUIState>> = pinLocalSource.getListItemsFlow()

    override suspend fun getItemList(): List<PinUIState> = pinLocalSource.getListItems()

    override fun getItemByIdFlow(id: String): Flow<PinUIState?> = pinLocalSource.getItemByIdFlow(id)

    override suspend fun getItemById(id: String): PinUIState? = pinLocalSource.getItemById(id)
}