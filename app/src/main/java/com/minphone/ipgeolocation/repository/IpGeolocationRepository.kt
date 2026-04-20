package com.minphone.ipgeolocation.repository

import com.minphone.ipgeolocation.model.IpGeolocation
import kotlinx.coroutines.flow.Flow

interface IpGeolocationRepository {
    fun getIpGeolocation(ip: String): Flow<Result<IpGeolocation>>
}
