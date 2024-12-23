package com.example.gifsnap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.gifsnap.navigation.AppNavigation
import com.example.gifsnap.navigation.Screen
import com.example.gifsnap.ui.theme.GifSnapTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GifSnapTheme {
                SetBarColor(color = MaterialTheme.colorScheme.inverseOnSurface)

                val navController = rememberNavController()
                val startDestination: Screen = Screen.HomeScreen

                AppNavigation(
                    startDestination = startDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun SetBarColor(color: Color){
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = color) {
        systemUiController.setSystemBarsColor(color)
    }
}
