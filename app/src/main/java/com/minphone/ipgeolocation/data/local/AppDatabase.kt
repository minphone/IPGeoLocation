package com.minphone.ipgeolocation.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.minphone.ipgeolocation.data.model.IpGeolocationEntity
import com.minphone.ipgeolocation.util.Constants

@Database(entities = [IpGeolocationEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ipGeolocationDao(): IpGeolocationDao

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                Constants.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
        }
    }
}
