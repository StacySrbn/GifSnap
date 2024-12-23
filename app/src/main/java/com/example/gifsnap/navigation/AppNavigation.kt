package com.example.gifsnap.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gifsnap.presentation.detail.DetailScreen
import com.example.gifsnap.presentation.detail.DetailViewModel
import com.example.gifsnap.presentation.home.HomeScreen
import com.example.gifsnap.presentation.home.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun AppNavigation(
    startDestination: Screen,
    navController: NavHostController
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val homeState by homeViewModel.gifListState.collectAsState()
    val gifsList = homeState.gifsList.collectAsLazyPagingItems()

    val detailViewModel = hiltViewModel<DetailViewModel>()
    val detailState by detailViewModel.detailState.collectAsState()

    NavHost(
        startDestination = startDestination.route,
        navController = navController
    ) {
        composable(Screen.HomeScreen.route) {

            var isRefreshing by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(isRefreshing) {
                if (isRefreshing) {
                    homeViewModel.refreshData()
                    delay(1000)
                    isRefreshing = false
                }
            }

            HomeScreen(
                homeState = homeState,
                navController = navController,
                isRefreshing = isRefreshing,
                onRefresh = {
                    isRefreshing = true
                },
                gifsList = gifsList
            )
        }
        composable(
            route = Screen.DetailScreen.route + "/{gifId}",
            arguments = listOf(
                navArgument(name = "gifId") { type = NavType.StringType }
            )

        ) {backStackEntry ->
            val gifId = backStackEntry.arguments?.getString("gifId") ?: ""

            detailViewModel.loadGifById(gifId)
            var isRefreshing by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(isRefreshing) {
                if (isRefreshing) {
                    detailViewModel.refreshData(gifId)
                    delay(1000)
                    isRefreshing = false
                }
            }

            DetailScreen(
                detailState = detailState,
                navController = navController,
                isRefreshing = isRefreshing,
                onRefresh = {
                    isRefreshing = true
                }
            )
        }
    }
}