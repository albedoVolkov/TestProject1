package com.albedo.testproject1.data.source.local.services

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.albedo.testproject1.data.models.ServiceUIState
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Insert(ServiceUIState::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(categories: List<ServiceUIState>)

    @Query("delete from services")
    suspend fun clear()


    @Query("SELECT * FROM services")
    fun getAllFlow(): Flow<List<ServiceUIState>>

    @Query("SELECT * FROM services")
    suspend fun getAll(): List<ServiceUIState>

    @Query("SELECT * FROM services WHERE id =:id LIMIT 1")
    fun getItemByIdFlow(id : String): Flow<ServiceUIState?>

    @Query("SELECT * FROM services WHERE id =:id LIMIT 1")
    suspend fun getItemById(id : String): ServiceUIState?

}