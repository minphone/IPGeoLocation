package com.minphone.ipgeolocation.repository.restClient

import com.minphone.ipgeolocation.remote.response.IpGeolocationResponse
import retrofit2.Response

interface IpGeolocationRestClient {
    suspend fun getIpGeolocation(ip: String): Response<IpGeolocationResponse>
}
