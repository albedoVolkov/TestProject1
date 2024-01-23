package com.albedo.testproject1.data.source.local.pin

import android.util.Log
import com.albedo.testproject1.IoDispatcher
import com.albedo.testproject1.data.models.PinUIState
import com.albedo.testproject1.data.models.ServiceUIState
import com.albedo.testproject1.data.source.local.pin.PinDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class PinLocalDataSource @Inject internal constructor(
    private val pinDao: PinDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : PinDataSource {

    private val TAG = "PinLocalDataSource"

    override fun getListItemsFlow(): Flow<List<PinUIState>> = pinDao.getAllFlow()

    override suspend fun getListItems(): List<PinUIState> = withContext(ioDispatcher) {
        try {
            return@withContext pinDao.getAll()
        } catch (e: Exception) {
            return@withContext listOf()
        }
    }




    override fun getItemByIdFlow(id : String) : Flow<PinUIState?> = pinDao.getItemByIdFlow(id)


    override suspend fun getItemById(id : String) : PinUIState? = withContext(ioDispatcher) {
        try {
            return@withContext pinDao.getItemById(id)
        } catch (e: Exception) {
            return@withContext null
        }
    }

    override suspend fun updateListItems(list: List<PinUIState>) : Unit = withContext(ioDispatcher) {
        Log.d(TAG, "list = $list")
        pinDao.clear()
        pinDao.insertList(list)
    }

    override suspend fun deleteAllItems(): Unit = withContext(ioDispatcher) {
        pinDao.clear()
    }
}