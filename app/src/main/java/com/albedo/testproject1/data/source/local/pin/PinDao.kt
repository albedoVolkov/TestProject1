package com.albedo.testproject1.data.source.local.pin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.albedo.testproject1.data.models.PinUIState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Dao
interface PinDao  {
    @Insert(PinUIState::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(categories: List<PinUIState>)
    //getItemsByServiceFlow
    @Query("delete from pins")
    suspend fun clear()


    @Query("SELECT * FROM pins")
    fun getAllFlow(): Flow<List<PinUIState>>

    @Query("SELECT * FROM pins")
    suspend fun getAll(): List<PinUIState>

    @Query("SELECT * FROM pins WHERE id =:id LIMIT 1")
    fun getItemByIdFlow(id : String): Flow<PinUIState?>

    @Query("SELECT * FROM pins WHERE id =:id LIMIT 1")
    suspend fun getItemById(id : String): PinUIState?

}