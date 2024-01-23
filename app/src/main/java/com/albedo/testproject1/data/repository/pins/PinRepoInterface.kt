package com.albedo.testproject1.data.repository.pins

import com.albedo.testproject1.data.models.PinUIState
import kotlinx.coroutines.flow.Flow

interface PinRepoInterface  {

    suspend fun refreshItemsData()

    fun getItemListFlow(): Flow<List<PinUIState>>
    suspend fun getItemList(): List<PinUIState>
    fun getItemByIdFlow(id : String) : Flow<PinUIState?>
    suspend fun getItemById(id : String) : PinUIState?
}