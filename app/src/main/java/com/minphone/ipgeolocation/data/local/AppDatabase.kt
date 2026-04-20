package com.minphone.ipgeolocation.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.minphone.ipgeolocation.data.model.IpGeolocation

@Database(entities = [IpGeolocation::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ipGeolocationDao(): IpGeolocationDao

    companion object {
        private const val DATABASE_NAME = "ip_geolocation_database"

        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}
