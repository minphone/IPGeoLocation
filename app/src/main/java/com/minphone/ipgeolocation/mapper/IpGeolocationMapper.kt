package com.minphone.ipgeolocation.mapper

import com.minphone.ipgeolocation.model.IpGeolocation
import com.minphone.ipgeolocation.data.model.IpGeolocationEntity
import com.minphone.ipgeolocation.remote.response.IpGeolocationResponse

/**
 * Mapper for [IpGeolocationResponse] to [IpGeolocation] domain model.
 */
fun IpGeolocationResponse.toIpGeolocation(): IpGeolocation {
    return IpGeolocation(
        query = this.query ?: "",
        status = this.status ?: "",
        country = this.country ?: "",
        countryCode = this.countryCode ?: "",
        region = this.region ?: "",
        regionName = this.regionName ?: "",
        city = this.city ?: "",
        zip = this.zip ?: "",
        lat = this.lat ?: 0.0,
        lon = this.lon ?: 0.0,
        timezone = this.timezone ?: "",
        isp = this.isp ?: "",
        org = this.org ?: "",
        asName = this.asName ?: "",
        timestamp = System.currentTimeMillis()
    )
}

/**
 * Mapper for [IpGeolocationResponse] to [IpGeolocationEntity] database model.
 */
fun IpGeolocationResponse.toIpGeolocationEntity(): IpGeolocationEntity {
    return IpGeolocationEntity(
        query = this.query ?: "",
        status = this.status ?: "",
        country = this.country ?: "",
        countryCode = this.countryCode ?: "",
        region = this.region ?: "",
        regionName = this.regionName ?: "",
        city = this.city ?: "",
        zip = this.zip ?: "",
        lat = this.lat ?: 0.0,
        lon = this.lon ?: 0.0,
        timezone = this.timezone ?: "",
        isp = this.isp ?: "",
        org = this.org ?: "",
        asName = this.asName ?: "",
        timestamp = System.currentTimeMillis()
    )
}

/**
 * Mapper for [IpGeolocationEntity] to [IpGeolocation] domain model.
 */
fun IpGeolocationEntity.toIpGeolocation(): IpGeolocation {
    return IpGeolocation(
        query = this.query,
        status = this.status,
        country = this.country,
        countryCode = this.countryCode,
        region = this.region,
        regionName = this.regionName,
        city = this.city,
        zip = this.zip,
        lat = this.lat,
        lon = this.lon,
        timezone = this.timezone,
        isp = this.isp,
        org = this.org,
        asName = this.asName,
        timestamp = this.timestamp
    )
}

/**
 * Mapper for [IpGeolocation] domain model to [IpGeolocationEntity] database model.
 */
fun IpGeolocation.toIpGeolocationEntity(): IpGeolocationEntity {
    return IpGeolocationEntity(
        query = this.query,
        status = this.status,
        country = this.country,
        countryCode = this.countryCode,
        region = this.region,
        regionName = this.regionName,
        city = this.city,
        zip = this.zip,
        lat = this.lat,
        lon = this.lon,
        timezone = this.timezone,
        isp = this.isp,
        org = this.org,
        asName = this.asName,
        timestamp = this.timestamp
    )
}
