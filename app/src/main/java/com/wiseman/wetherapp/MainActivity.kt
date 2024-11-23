package com.wiseman.wetherapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wiseman.wetherapp.presentation.screen.HomeScreen
import com.wiseman.wetherapp.presentation.viewmodel.WeatherViewModel
import com.wiseman.wetherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

        setContent {
            WeatherAppTheme {
                HomeScreen(viewModel.state)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {}
}