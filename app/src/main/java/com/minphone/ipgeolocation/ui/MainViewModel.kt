package com.minphone.ipgeolocation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.minphone.ipgeolocation.data.model.IpGeolocation
import com.minphone.ipgeolocation.data.repository.IpGeolocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UiState(
    val isLoading: Boolean = false,
    val result: IpGeolocation? = null,
    val error: String? = null
)

class MainViewModel(private val repository: IpGeolocationRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun searchIp(ip: String) {
        if (ip.isBlank()) {
            _uiState.value = UiState(error = "IP address or domain cannot be empty")
            return
        }
        
        // Basic validation: IP address or Domain name
        val ipPattern = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}\$".toRegex()
        val domainPattern = "^([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}\$".toRegex()
        
        if (!ip.matches(ipPattern) && !ip.matches(domainPattern) && ip != "check") {
            _uiState.value = UiState(error = "Invalid IP address or domain format")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            val result = repository.getIpGeolocation(ip)
            result.onSuccess {
                _uiState.value = UiState(result = it)
            }.onFailure {
                _uiState.value = UiState(error = it.message ?: "An unknown error occurred")
            }
        }
    }
}

class MainViewModelFactory(private val repository: IpGeolocationRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
