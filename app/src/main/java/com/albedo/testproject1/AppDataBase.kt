package com.albedo.testproject1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.albedo.testproject1.data.models.CoordinateUIState
import com.albedo.testproject1.data.models.PinUIState
import com.albedo.testproject1.data.models.ServiceUIState
import com.albedo.testproject1.data.source.ListTypeConverter
import com.albedo.testproject1.data.source.local.pin.PinDao
import com.albedo.testproject1.data.source.local.services.ServiceDao


@Database(entities = [ServiceUIState::class, PinUIState::class, CoordinateUIState::class], version = 1, exportSchema = true)
@TypeConverters(ListTypeConverter::class)
abstract class AppDataBase : RoomDatabase() {


        abstract fun servicesDao(): ServiceDao
        abstract fun pinsDao(): PinDao

        companion object {
            @Volatile
            private var INSTANCE: AppDataBase? = null

            fun getDataBase(context: Context): AppDataBase {
                val tempInstance = INSTANCE
                if (tempInstance != null) {
                    return tempInstance
                }
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context,
                        AppDataBase::class.java,
                        "application_data_base"
                    ).build()
                    INSTANCE = instance
                    return  instance
                }
            }
        }
    }