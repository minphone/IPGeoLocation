package com.minphone.ipgeolocation.data.repository

import com.minphone.ipgeolocation.data.local.IpGeolocationDao
import com.minphone.ipgeolocation.data.model.IpGeolocation
import com.minphone.ipgeolocation.data.repository.restClient.IpGeolocationRestClient
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class IpGeolocationRepositoryImplTest {

    @Mock
    private lateinit var restClient: IpGeolocationRestClient

    @Mock
    private lateinit var dao: IpGeolocationDao

    private lateinit var repository: IpGeolocationRepositoryImpl

    private val ip = "8.8.8.8"
    private val mockGeolocation = IpGeolocation(
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

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = IpGeolocationRepositoryImpl(restClient, dao)
    }

    @Test
    fun `getIpGeolocation returns cached data and then fresh data from remote`() = runTest {
        val remoteGeolocation = mockGeolocation.copy(city = "New York")
        
        `when`(dao.getIpGeolocation(ip)).thenReturn(mockGeolocation)
        `when`(restClient.getIpGeolocation(ip)).thenReturn(Response.success(remoteGeolocation))

        val results = repository.getIpGeolocation(ip).toList()

        assertEquals(2, results.size)
        assertEquals(Result.success(mockGeolocation), results[0])
        assertEquals(Result.success(remoteGeolocation), results[1])
    }

    @Test
    fun `getIpGeolocation returns only remote data when cache is empty`() = runTest {
        `when`(dao.getIpGeolocation(ip)).thenReturn(null)
        `when`(restClient.getIpGeolocation(ip)).thenReturn(Response.success(mockGeolocation))

        val results = repository.getIpGeolocation(ip).toList()

        assertEquals(1, results.size)
        assertEquals(Result.success(mockGeolocation), results[0])
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
