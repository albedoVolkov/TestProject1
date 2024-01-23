package com.albedo.testproject1.data.source.local.pin

import com.albedo.testproject1.data.models.PinUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface PinDataSource {

        fun getListItemsFlow(): Flow<List<PinUIState>>
        suspend fun getListItems(): List<PinUIState>

        fun getItemByIdFlow(id : String) : Flow<PinUIState?>
        suspend fun getItemById(id : String) : PinUIState?

        suspend fun updateListItems(list: List<PinUIState>) : Unit
        suspend fun deleteAllItems(): Unit

    }