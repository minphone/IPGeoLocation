package com.minphone.ipgeolocation.data.repository.restClient

import com.minphone.ipgeolocation.data.model.IpGeolocation
import com.minphone.ipgeolocation.data.remote.IpApiService
import retrofit2.Response
import javax.inject.Inject

class IpGeolocationRestClientImpl @Inject constructor(
    private val ipApiService: IpApiService
) : IpGeolocationRestClient {
    override suspend fun getIpGeolocation(ip: String): Response<IpGeolocation> {
        return ipApiService.getIpGeolocation(ip)
    }
}
