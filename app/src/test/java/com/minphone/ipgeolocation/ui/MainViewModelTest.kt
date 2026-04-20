package com.minphone.ipgeolocation.ui

import com.minphone.ipgeolocation.data.model.IpGeolocation
import com.minphone.ipgeolocation.data.repository.IpGeolocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: IpGeolocationRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(IpGeolocationRepository::class.java)
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchIp with empty string sets error`() {
        viewModel.searchIp("")
        assertEquals("IP address or domain cannot be empty", viewModel.uiState.value.error)
    }

    @Test
    fun `searchIp successful call updates state`() = runTest {
        val ip = "8.8.8.8"
        val mockResult = IpGeolocation(
            query = ip,
            status = "success",
            country = "USA",
            countryCode = "US",
            region = "CA",
            regionName = "California",
            city = "Mountain View",
            zip = "94043",
            lat = 37.4223,
            lon = -122.0847,
            timezone = "America/Los_Angeles",
            isp = "Google LLC",
            org = "Google LLC",
            asName = "AS15169 Google LLC"
        )
        
        `when`(repository.getIpGeolocation(ip)).thenReturn(flowOf(Result.success(mockResult)))

        viewModel.searchIp(ip)

        assertEquals(mockResult, viewModel.uiState.value.result)
        assertEquals(null, viewModel.uiState.value.error)
        assertEquals(false, viewModel.uiState.value.isLoading)
    }

    @Test
    fun `searchIp failure call updates state`() = runTest {
        val ip = "8.8.8.8"
        val errorMessage = "API call failed"
        
        `when`(repository.getIpGeolocation(ip)).thenReturn(flowOf(Result.failure(Exception(errorMessage))))

        viewModel.searchIp(ip)

        assertEquals(null, viewModel.uiState.value.result)
        assertEquals(errorMessage, viewModel.uiState.value.error)
        assertEquals(false, viewModel.uiState.value.isLoading)
    }
}
