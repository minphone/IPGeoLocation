package com.minphone.ipgeolocation.remote

import com.minphone.ipgeolocation.remote.response.IpGeolocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IpApiService {
    @GET("json/{ip}")
    suspend fun getIpGeolocation(@Path("ip") ip: String): Response<IpGeolocationResponse>
}
