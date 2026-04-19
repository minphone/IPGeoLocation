package com.minphone.ipgeolocation.data.repository

import com.minphone.ipgeolocation.data.local.IpGeolocationDao
import com.minphone.ipgeolocation.data.model.IpGeolocation
import com.minphone.ipgeolocation.data.remote.IpApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IpGeolocationRepository(
    private val dao: IpGeolocationDao,
    private val apiService: IpApiService
) {
    suspend fun getIpGeolocation(ip: String): Result<IpGeolocation> = withContext(Dispatchers.IO) {
        try {
            val cached = dao.getIpGeolocation(ip)
            val now = System.currentTimeMillis()
            
            if (cached != null && (now - cached.timestamp) < 5 * 60 * 1000) {
                return@withContext Result.success(cached)
            }

            val response = apiService.getIpGeolocation(ip)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status == "success") {
                    val entity = body.copy(timestamp = now)
                    dao.insertIpGeolocation(entity)
                    Result.success(entity)
                } else {
                    Result.failure(Exception(body?.status ?: "Unknown error from API"))
                }
            } else {
                if (cached != null) {
                    Result.success(cached) // Return stale data if API fails
                } else {
                    Result.failure(Exception("API call failed: ${response.code()}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
