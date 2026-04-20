package com.minphone.ipgeolocation.di

import android.content.Context
import com.minphone.ipgeolocation.data.local.AppDatabase
import com.minphone.ipgeolocation.data.local.IpGeolocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideIpGeolocationDao(database: AppDatabase): IpGeolocationDao {
        return database.ipGeolocationDao()
    }
}
