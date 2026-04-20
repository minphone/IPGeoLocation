package com.minphone.ipgeolocation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minphone.ipgeolocation.model.IpGeolocation
import com.minphone.ipgeolocation.repository.IpGeolocationRepository
import com.minphone.ipgeolocation.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val isLoading: Boolean = false,
    val result: IpGeolocation? = null,
    val error: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: IpGeolocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun searchIp(ip: String) {
        if (ip.isBlank()) {
            _uiState.value = UiState(error = Constants.ERROR_EMPTY_IP)
            return
        }
        
        // Basic validation: IP address or Domain name
        val ipPattern = Constants.IP_PATTERN.toRegex()
        val domainPattern = Constants.DOMAIN_PATTERN.toRegex()
        
        if (!ip.matches(ipPattern) && !ip.matches(domainPattern) && ip != Constants.CHECK_COMMAND) {
            _uiState.value = UiState(error = Constants.ERROR_INVALID_IP)
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            repository.getIpGeolocation(ip).collect { result ->
                result.onSuccess {
                    _uiState.value = UiState(result = it)
                }.onFailure {
                    _uiState.value = UiState(error = it.message ?: Constants.ERROR_UNKNOWN)
                }
            }
        }
    }
}
