package com.minphone.ipgeolocation.util

import com.minphone.ipgeolocation.BuildConfig

object Constants {
    const val BASE_URL = BuildConfig.BASE_URL
    const val DATABASE_NAME = "ip_geolocation_database"
    
    // UI Strings (Note: These should ideally be in strings.xml, 
    // but moving logic-related constants here)
    const val ERROR_EMPTY_IP = "IP address or domain cannot be empty"
    const val ERROR_INVALID_IP = "Invalid IP address or domain format"
    const val ERROR_UNKNOWN = "An unknown error occurred"
    const val ERROR_API_FAILED = "API call failed"
    const val ERROR_BODY_NULL = "Response body is null"
    
    // Validation Patterns
    const val IP_PATTERN = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$"
    const val DOMAIN_PATTERN = "^([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$"
    const val CHECK_COMMAND = "check"

    // Cache Logic
    const val CACHE_EXPIRATION_MILLIS = 5 * 60 * 1000L // 5 minutes
}
