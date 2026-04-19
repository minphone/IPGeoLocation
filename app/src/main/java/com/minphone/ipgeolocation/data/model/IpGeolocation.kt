package com.minphone.ipgeolocation.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ip_geolocations")
data class IpGeolocation(
    @PrimaryKey
    @SerializedName("query")
    val query: String, // The IP address
    val status: String?,
    val country: String?,
    val countryCode: String?,
    val region: String?,
    val regionName: String?,
    val city: String?,
    val zip: String?,
    val lat: Double?,
    val lon: Double?,
    val timezone: String?,
    val isp: String?,
    val org: String?,
    @SerializedName("as")
    val asName: String?,
    val timestamp: Long = System.currentTimeMillis()
)
