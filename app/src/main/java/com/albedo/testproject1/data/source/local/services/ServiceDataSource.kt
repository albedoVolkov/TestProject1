package com.albedo.testproject1.data.source.local.services

import com.albedo.testproject1.data.models.ServiceUIState
import kotlinx.coroutines.flow.Flow

interface ServiceDataSource {

    fun getListItemsFlow(): Flow<List<ServiceUIState>>
    suspend fun getListItems(): List<ServiceUIState>
    fun getItemByIdFlow(id : String) : Flow<ServiceUIState?>
    suspend fun getItemById(id : String) : ServiceUIState?
    suspend fun updateListItems(list: List<ServiceUIState>) : Unit
    suspend fun deleteAllItems(): Unit

}