package com.minphone.ipgeolocation.data.repository.restClient

import com.minphone.ipgeolocation.data.model.IpGeolocation
import retrofit2.Response

interface IpGeolocationRestClient {
    suspend fun getIpGeolocation(ip: String): Response<IpGeolocation>
}
