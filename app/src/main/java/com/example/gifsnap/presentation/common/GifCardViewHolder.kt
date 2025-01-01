package com.example.gifsnap.presentation.common

import android.os.Build
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.*
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.gifsnap.domain.models.Gif
import com.example.gifsnap.navigation.Screen
import kotlinx.coroutines.Dispatchers
@Composable
fun GifCardViewHolder(
    gif: Gif,
    navHostController: NavHostController
) {
    val id = gif.id
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
            .clickable { navHostController.navigate(Screen.DetailScreen.withArgs(id)) }
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {

        val context = LocalContext.current
        val dispatcher = Dispatchers.IO
        val imageLoader = ImageLoader.Builder(context)
            .dispatcher(dispatcher)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .respectCacheHeaders(false)
            .build()

            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = gif.url,
                imageLoader = imageLoader,
                loading = {
                    ShimmerGifItem(
                        isLoading = true,
                        contentAfterLoading = {}
                    )
                },
                error = {
                    ErrorLoadingItem(
                        modifier = Modifier
                            .size(150.dp)
                            .background(Color.LightGray)
                            .clip(RoundedCornerShape(12.dp))
                    )
                },
                contentDescription = gif.title,
                contentScale = ContentScale.Crop
            )

    }
}
