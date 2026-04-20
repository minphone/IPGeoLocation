package com.minphone.ipgeolocation.data.repository

import com.minphone.ipgeolocation.data.local.IpGeolocationDao
import com.minphone.ipgeolocation.data.model.IpGeolocationEntity
import com.minphone.ipgeolocation.remote.response.IpGeolocationResponse
import com.minphone.ipgeolocation.mapper.toIpGeolocation
import com.minphone.ipgeolocation.repository.IpGeolocationRepositoryImpl
import com.minphone.ipgeolocation.repository.restClient.IpGeolocationRestClient
import com.minphone.ipgeolocation.util.Constants
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response

class IpGeolocationRepositoryImplTest {

    @Mock
    private lateinit var restClient: IpGeolocationRestClient

    @Mock
    private lateinit var dao: IpGeolocationDao

    private lateinit var repository: IpGeolocationRepositoryImpl

    private val ip = "8.8.8.8"
    
    private val mockResponseEntity = IpGeolocationResponse(
        query = ip,
        status = "success",
        country = "United States",
        countryCode = "US",
        region = "VA",
        regionName = "Virginia",
        city = "Ashburn",
        zip = "20149",
        lat = 39.03,
        lon = -77.5,
        timezone = "America/New_York",
        isp = "Google LLC",
        org = "Google LLC",
        asName = "AS15169 Google LLC"
    )

    private fun createMockEntity(timestamp: Long) = IpGeolocationEntity(
        query = ip,
        status = "success",
        country = "United States",
        countryCode = "US",
        region = "VA",
        regionName = "Virginia",
        city = "Ashburn",
        zip = "20149",
        lat = 39.03,
        lon = -77.5,
        timezone = "America/New_York",
        isp = "Google LLC",
        org = "Google LLC",
        asName = "AS15169 Google LLC",
        timestamp = timestamp
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = IpGeolocationRepositoryImpl(restClient, dao)
    }

    @Test
    fun `getIpGeolocation returns only cached data when cache is valid`() = runTest {
        val validEntity = createMockEntity(System.currentTimeMillis())
        
        `when`(dao.getIpGeolocation(ip)).thenReturn(validEntity)

        val results = repository.getIpGeolocation(ip).toList()

        assertEquals(1, results.size)
        assertEquals(Result.success(validEntity.toIpGeolocation()), results[0])
        verify(restClient, never()).getIpGeolocation(anyString())
    }

    @Test
    fun `getIpGeolocation returns cached data and then fresh data from remote when cache is expired`() = runTest {
        val expiredTimestamp = System.currentTimeMillis() - (Constants.CACHE_EXPIRATION_MILLIS + 1000)
        val expiredEntity = createMockEntity(expiredTimestamp)
        val remoteResponse = mockResponseEntity.copy(city = "New York")
        
        `when`(dao.getIpGeolocation(ip)).thenReturn(expiredEntity)
        `when`(restClient.getIpGeolocation(ip)).thenReturn(Response.success(remoteResponse))

        val results = repository.getIpGeolocation(ip).toList()

        assertEquals(2, results.size)
        assertEquals(Result.success(expiredEntity.toIpGeolocation()), results[0])
        
        val actualRemoteResult = results[1].getOrNull()
        val expectedDomainFromRemote = remoteResponse.toIpGeolocation()
        assertEquals(expectedDomainFromRemote.query, actualRemoteResult?.query)
        assertEquals(expectedDomainFromRemote.city, actualRemoteResult?.city)
        verify(restClient, times(1)).getIpGeolocation(ip)
    }

    @Test
    fun `getIpGeolocation returns only remote data when cache is empty`() = runTest {
        `when`(dao.getIpGeolocation(ip)).thenReturn(null)
        `when`(restClient.getIpGeolocation(ip)).thenReturn(Response.success(mockResponseEntity))

        val results = repository.getIpGeolocation(ip).toList()

        assertEquals(1, results.size)
        val expectedDomain = mockResponseEntity.toIpGeolocation()
        assertEquals(expectedDomain.query, results[0].getOrNull()?.query)
        verify(restClient, times(1)).getIpGeolocation(ip)
    }

    @Test
    fun `getIpGeolocation returns failure when both cache and remote fail`() = runTest {
        val errorMessage = "Network Error"
        `when`(dao.getIpGeolocation(ip)).thenReturn(null)
        `when`(restClient.getIpGeolocation(ip)).thenThrow(RuntimeException(errorMessage))

        val results = repository.getIpGeolocation(ip).toList()

        assertEquals(1, results.size)
        assertEquals(errorMessage, results[0].exceptionOrNull()?.message)
    }
}
