package com.albedo.testproject1.data.source.local.services

import android.util.Log
import com.albedo.testproject1.IoDispatcher
import com.albedo.testproject1.data.models.ServiceUIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ServiceLocalDataSource @Inject internal constructor(
    private val dao: ServiceDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ServiceDataSource {

    private val TAG = "ServicesLocalDataSource"

    override fun getListItemsFlow(): Flow<List<ServiceUIState>> = dao.getAllFlow()

    override suspend fun getListItems(): List<ServiceUIState> = withContext(ioDispatcher) {
        try {
            return@withContext dao.getAll()
        } catch (e: Exception) {
            return@withContext listOf()
        }
    }

    override fun getItemByIdFlow(id : String) : Flow<ServiceUIState?> = dao.getItemByIdFlow(id)


    override suspend fun getItemById(id : String) : ServiceUIState? = withContext(ioDispatcher) {
        try {
            return@withContext dao.getItemById(id)
        } catch (e: Exception) {
            return@withContext null
        }
    }

    override suspend fun updateListItems(list: List<ServiceUIState>) : Unit = withContext(ioDispatcher) {
        Log.d(TAG, "list = $list")
        dao.clear()
        dao.insertList(list)
    }

    override suspend fun deleteAllItems(): Unit = withContext(ioDispatcher) {
        dao.clear()
    }
}