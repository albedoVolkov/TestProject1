package com.albedo.testproject1.di

import android.content.Context
import com.albedo.testproject1.AppDataBase
import com.albedo.testproject1.data.source.local.pin.PinDao
import com.albedo.testproject1.data.source.local.services.ServiceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.getDataBase(context)
    }

    @Provides
    fun providePinsDao(appDatabase: AppDataBase): PinDao {
        return appDatabase.pinsDao()
    }

    @Provides
    fun provideServicesDao(appDatabase: AppDataBase): ServiceDao {
        return appDatabase.servicesDao()
    }
}