package com.minphone.ipgeolocation.data.repository

import com.minphone.ipgeolocation.data.local.IpGeolocationDao
import com.minphone.ipgeolocation.data.model.IpGeolocation
import com.minphone.ipgeolocation.data.repository.restClient.IpGeolocationRestClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IpGeolocationRepositoryImpl @Inject constructor(
    private val restClient: IpGeolocationRestClient,
    private val dao: IpGeolocationDao
) : IpGeolocationRepository {
    override fun getIpGeolocation(ip: String): Flow<Result<IpGeolocation>> = flow {
        // Try to get from local database first
        val cachedData = dao.getIpGeolocation(ip)
        if (cachedData != null) {
            emit(Result.success(cachedData))
        }

        try {
            val response = restClient.getIpGeolocation(ip)
            if (response.isSuccessful) {
                val remoteData = response.body()
                if (remoteData != null) {
                    // Cache the new data
                    dao.insertIpGeolocation(remoteData)
                    emit(Result.success(remoteData))
                } else {
                    emit(Result.failure(Exception("Response body is null")))
                }
            } else {
                emit(Result.failure(Exception("API call failed: ${response.message()}")))
            }
        } catch (e: Exception) {
            // Only emit failure if we didn't have cached data
            if (cachedData == null) {
                emit(Result.failure(e))
            }
        }
    }
}
