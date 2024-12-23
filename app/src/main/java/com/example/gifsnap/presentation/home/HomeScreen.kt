@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gifsnap.presentation.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.example.gifsnap.R
import com.example.gifsnap.domain.models.Gif
import com.example.gifsnap.presentation.common.ErrorLoadingItem
import com.example.gifsnap.presentation.common.GifCardViewHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeState: HomeState,
    gifsList: LazyPagingItems<Gif>,
    navController: NavHostController,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
){
    val context = LocalContext.current

    LaunchedEffect (homeState.errorMessage){
        if (!homeState.errorMessage.isNullOrEmpty()) {
            Log.e("MY LOG HomeScreen", "Error message: ${homeState.errorMessage}")
            Toast.makeText(context, "Error: ${homeState.errorMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = state
    ) {
        Column (
            modifier = Modifier
                .background(color = colorResource(id = R.color.main_color_theme))
                .fillMaxSize()
        ){

            Spacer(modifier = Modifier.height(30.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                items(gifsList.itemCount) { index ->
                    val gif = gifsList[index]
                    gif?.let {
                        Log.d("MY LOG HomeScreen", "GIF ${gif.id}")
                        Log.d("MY LOG HomeScreen", "GIF ${gif.title}")
                        Log.d("MY LOG HomeScreen", "GIF ${gif.url}")
                        Log.d("MY LOG HomeScreen", "GIF isLoading: $homeState")

                        GifCardViewHolder(
                            gif,
                            navController
                        )

                    } ?: run {
                        ErrorLoadingItem(
                            modifier = Modifier
                                .size(150.dp)
                                .background(Color.LightGray)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }
        }
    }
}