package com.minphone.ipgeolocation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minphone.ipgeolocation.data.local.AppDatabase
import com.minphone.ipgeolocation.data.remote.RetrofitInstance
import com.minphone.ipgeolocation.data.repository.IpGeolocationRepository
import com.minphone.ipgeolocation.ui.MainViewModel
import com.minphone.ipgeolocation.ui.MainViewModelFactory
import com.minphone.ipgeolocation.ui.theme.IPGeoLocationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = AppDatabase.getDatabase(this)
        val repository = IpGeolocationRepository(database.ipGeolocationDao(), RetrofitInstance.api)
        val factory = MainViewModelFactory(repository)

        enableEdgeToEdge()
        setContent {
            IPGeoLocationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IpGeoLocationScreen(
                        modifier = Modifier.padding(innerPadding),
                        factory = factory
                    )
                }
            }
        }
    }
}

@Composable
fun IpGeoLocationScreen(
    modifier: Modifier = Modifier,
    factory: MainViewModelFactory
) {
    val viewModel: MainViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()
    var ipInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = ipInput,
            onValueChange = { ipInput = it },
            label = { Text("IP Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.searchIp(ipInput) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        uiState.error?.let {
            Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
        }

        uiState.result?.let { result ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "IP: ${result.query}", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Status: ${result.status}")
                    Text(text = "Country: ${result.country} (${result.countryCode})")
                    Text(text = "Region: ${result.regionName}")
                    Text(text = "City: ${result.city}")
                    Text(text = "ZIP: ${result.zip}")
                    Text(text = "Lat: ${result.lat}, Lon: ${result.lon}")
                    Text(text = "Timezone: ${result.timezone}")
                    Text(text = "ISP: ${result.isp}")
                    Text(text = "Org: ${result.org}")
                    Text(text = "AS: ${result.asName}")
                    Text(text = "Last updated: ${java.util.Date(result.timestamp)}")
                }
            }
        }
    }
}
