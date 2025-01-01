package com.example.gifsnap.presentation.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gifsnap.presentation.detail.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    detailState: DetailState,
    navController: NavHostController,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
){
    val context = LocalContext.current

    LaunchedEffect (detailState.errorMessage){
        if (!detailState.errorMessage.isNullOrEmpty()) {
            Toast.makeText(context, "Error: ${detailState.errorMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    val state = rememberPullToRefreshState()
    val scrollState = rememberScrollState()

    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    var dominantColor by remember {
        mutableStateOf(defaultColor)
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = state
    ) {
        Box {
            Column(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                defaultColor,
                                dominantColor
                            )
                        )
                    )
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

               TopSection(
                    detailState = detailState,
                    navController = navController
               )
                
                Spacer(modifier = Modifier.height(32.dp))


                GifCard(
                    gif = detailState.gif,
                    onDominantColorChanged = { color -> dominantColor = color }
                )      

            }
        }
    }
}