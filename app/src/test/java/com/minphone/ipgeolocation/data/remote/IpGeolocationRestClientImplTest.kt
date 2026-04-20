package com.minphone.ipgeolocation.data.remote

import com.minphone.ipgeolocation.data.model.IpGeolocation
import com.minphone.ipgeolocation.data.repository.restClient.IpGeolocationRestClientImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class IpGeolocationRestClientImplTest {

    @Mock
    private lateinit var apiService: IpApiService

    private lateinit var restClient: IpGeolocationRestClientImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        restClient = IpGeolocationRestClientImpl(apiService)
    }

    @Test
    fun `getIpGeolocation returns successful response`() = runTest {
        val ip = "8.8.8.8"
        val expectedResult = IpGeolocation(
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
        val response = Response.success(expectedResult)

        `when`(apiService.getIpGeolocation(ip)).thenReturn(response)

        val result = restClient.getIpGeolocation(ip)

        assertEquals(true, result.isSuccessful)
        assertEquals(expectedResult, result.body())
    }
}
