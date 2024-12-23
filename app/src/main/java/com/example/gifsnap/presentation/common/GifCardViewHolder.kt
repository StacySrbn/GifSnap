package com.example.gifsnap.presentation.common

import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import coil.request.ImageRequest
import coil.size.Size
import com.example.gifsnap.domain.models.Gif
import com.example.gifsnap.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun GifCardViewHolder(
    gif: Gif,
    navHostController: NavHostController
) {
    Log.d("My Log","Home Gif $gif")
    val id = gif.id
    Log.e("My Log","Home Gif id passed $id")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
            .clickable { navHostController.navigate(Screen.DetailScreen.withArgs(id)) }
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(gif.url)
                .size(Size.ORIGINAL)
                .build()
        )
        val context = LocalContext.current
        val dispatcher = Dispatchers.IO.limitedParallelism(5)
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

        if (painter.state is AsyncImagePainter.State.Loading) {
            ShimmerGifItem(
                isLoading = painter.state is AsyncImagePainter.State.Loading,
                contentAfterLoading = {}
            )
        }
        if (painter.state is AsyncImagePainter.State.Error) {
            ErrorLoadingItem(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.LightGray)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        if (painter.state is AsyncImagePainter.State.Success) {

            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = gif.url,
                contentDescription = gif.title,
                imageLoader = imageLoader,
                contentScale = ContentScale.Crop
            )
        }
    }
}
