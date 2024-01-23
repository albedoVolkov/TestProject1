package com.albedo.testproject1.data.repository.services

import com.albedo.testproject1.data.models.ServiceUIState
import kotlinx.coroutines.flow.Flow


interface ServiceRepoInterface {

    suspend fun refreshItemsData()
    fun getItemListFlow(): Flow<List<ServiceUIState>>
    suspend fun getItemList(): List<ServiceUIState>
    fun getItemByIdFlow(id : String) : Flow<ServiceUIState?>
    suspend fun getItemById(id : String) : ServiceUIState?
}