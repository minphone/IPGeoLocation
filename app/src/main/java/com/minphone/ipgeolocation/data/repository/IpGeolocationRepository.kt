package com.minphone.ipgeolocation.data.repository

import com.minphone.ipgeolocation.data.model.IpGeolocation
import kotlinx.coroutines.flow.Flow

interface IpGeolocationRepository {
    fun getIpGeolocation(ip: String): Flow<Result<IpGeolocation>>
}
