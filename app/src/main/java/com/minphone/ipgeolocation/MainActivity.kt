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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minphone.ipgeolocation.ui.MainViewModel
import com.minphone.ipgeolocation.ui.theme.IPGeoLocationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        setContent {
            IPGeoLocationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IpGeoLocationScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun IpGeoLocationScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
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
            label = { Text(stringResource(R.string.label_ip_address)) },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.searchIp(ipInput) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.button_search))
        }

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        uiState.error?.let {
            Text(
                text = stringResource(R.string.error_message, it),
                color = MaterialTheme.colorScheme.error
            )
        }

        uiState.result?.let { result ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.result_ip, result.query),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(R.string.result_status, result.status))
                    Text(
                        text = stringResource(
                            R.string.result_country,
                            result.country,
                            result.countryCode
                        )
                    )
                    Text(text = stringResource(R.string.result_region, result.regionName))
                    Text(text = stringResource(R.string.result_city, result.city))
                    Text(text = stringResource(R.string.result_zip, result.zip))
                    Text(
                        text = stringResource(
                            R.string.result_lat_lon,
                            result.lat.toString(),
                            result.lon.toString()
                        )
                    )
                    Text(text = stringResource(R.string.result_timezone, result.timezone))
                    Text(text = stringResource(R.string.result_isp, result.isp))
                    Text(text = stringResource(R.string.result_org, result.org))
                    Text(text = stringResource(R.string.result_as, result.asName))
                    Text(
                        text = stringResource(
                            R.string.result_last_updated,
                            java.util.Date(result.timestamp).toString()
                        )
                    )
                }
            }
        }
    }
}
