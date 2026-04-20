package com.minphone.ipgeolocation.repository

import com.minphone.ipgeolocation.data.local.IpGeolocationDao
import com.minphone.ipgeolocation.mapper.toIpGeolocation
import com.minphone.ipgeolocation.mapper.toIpGeolocationEntity
import com.minphone.ipgeolocation.model.IpGeolocation
import com.minphone.ipgeolocation.repository.restClient.IpGeolocationRestClient
import com.minphone.ipgeolocation.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IpGeolocationRepositoryImpl @Inject constructor(
    private val restClient: IpGeolocationRestClient,
    private val dao: IpGeolocationDao
) : IpGeolocationRepository {
    override fun getIpGeolocation(ip: String): Flow<Result<IpGeolocation>> = flow {
        // Try to get from local database first
        val cachedDataEntity = dao.getIpGeolocation(ip)
        val isCacheValid = cachedDataEntity != null && 
            (System.currentTimeMillis() - cachedDataEntity.timestamp < Constants.CACHE_EXPIRATION_MILLIS)

        if (cachedDataEntity != null) {
            emit(Result.success(cachedDataEntity.toIpGeolocation()))
        }

        // If cache is missing or expired, fetch from remote
        if (!isCacheValid) {
            try {
                val response = restClient.getIpGeolocation(ip)
                if (response.isSuccessful) {
                    val remoteDataResponse = response.body()
                    if (remoteDataResponse != null) {
                        val remoteData = remoteDataResponse.toIpGeolocation()
                        // Cache the new data
                        dao.insertIpGeolocation(remoteData.toIpGeolocationEntity())
                        emit(Result.success(remoteData))
                    } else {
                        emit(Result.failure(Exception(Constants.ERROR_BODY_NULL)))
                    }
                } else {
                    emit(Result.failure(Exception("${Constants.ERROR_API_FAILED}: ${response.message()}")))
                }
            } catch (e: Exception) {
                // Only emit failure if we didn't have any cached data at all
                if (cachedDataEntity == null) {
                    emit(Result.failure(e))
                }
            }
        }
    }
}
