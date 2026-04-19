package com.minphone.ipgeolocation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.minphone.ipgeolocation.data.model.IpGeolocation

@Dao
interface IpGeolocationDao {
    @Query("SELECT * FROM ip_geolocations WHERE `query` = :ip")
    suspend fun getIpGeolocation(ip: String): IpGeolocation?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIpGeolocation(ipGeolocation: IpGeolocation)
}
