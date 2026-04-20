package com.minphone.ipgeolocation.mapper

import com.minphone.ipgeolocation.data.model.IpGeolocationEntity
import com.minphone.ipgeolocation.model.IpGeolocation
import com.minphone.ipgeolocation.remote.response.IpGeolocationResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class IpGeolocationMapperTest {

    @Test
    fun `toIpGeolocation maps IpGeolocationResponse correctly with non-null values`() {
        val response = IpGeolocationResponse(
            query = "8.8.8.8",
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

        val domain = response.toIpGeolocation()

        assertEquals(response.query, domain.query)
        assertEquals(response.status, domain.status)
        assertEquals(response.country, domain.country)
        assertEquals(response.city, domain.city)
        assertEquals(response.lat!!, domain.lat, 0.0)
    }

    @Test
    fun `toIpGeolocation maps IpGeolocationResponse correctly with null values`() {
        val response = IpGeolocationResponse(
            query = null,
            status = null,
            country = null,
            countryCode = null,
            region = null,
            regionName = null,
            city = null,
            zip = null,
            lat = null,
            lon = null,
            timezone = null,
            isp = null,
            org = null,
            asName = null
        )

        val domain = response.toIpGeolocation()

        assertEquals("", domain.query)
        assertEquals("", domain.status)
        assertEquals("", domain.country)
        assertEquals("", domain.city)
        assertEquals(0.0, domain.lat, 0.0)
    }

    @Test
    fun `toIpGeolocationEntity maps IpGeolocationResponse correctly`() {
        val response = IpGeolocationResponse(
            query = "8.8.8.8",
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
            isp = "Google",
            org = "Google",
            asName = "AS15169"
        )

        val entity = response.toIpGeolocationEntity()

        assertEquals(response.query, entity.query)
        assertEquals(response.city, entity.city)
    }

    @Test
    fun `toIpGeolocation maps IpGeolocationEntity correctly`() {
        val entity = IpGeolocationEntity(
            query = "8.8.8.8",
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
            timestamp = 123456789L
        )

        val domain = entity.toIpGeolocation()

        assertEquals(entity.query, domain.query)
        assertEquals(entity.timestamp, domain.timestamp)
    }

    @Test
    fun `toIpGeolocationEntity maps IpGeolocation domain model correctly`() {
        val domain = IpGeolocation(
            query = "8.8.8.8",
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
            timestamp = 123456789L
        )

        val entity = domain.toIpGeolocationEntity()

        assertEquals(domain.query, entity.query)
        assertEquals(domain.timestamp, entity.timestamp)
    }
}
