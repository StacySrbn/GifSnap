package com.example.gifsnap.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gifsnap.presentation.detail.*
import com.example.gifsnap.presentation.home.*
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