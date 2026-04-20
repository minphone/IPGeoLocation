package com.minphone.ipgeolocation.repository.restClient

import com.minphone.ipgeolocation.remote.response.IpGeolocationResponse
import com.minphone.ipgeolocation.remote.IpApiService
import retrofit2.Response
import javax.inject.Inject

class IpGeolocationRestClientImpl @Inject constructor(
    private val ipApiService: IpApiService
) : IpGeolocationRestClient {
    override suspend fun getIpGeolocation(ip: String): Response<IpGeolocationResponse> {
        return ipApiService.getIpGeolocation(ip)
    }
}
