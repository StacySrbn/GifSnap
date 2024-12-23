package com.example.gifsnap.presentation.detail.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.compose.*
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.gifsnap.R
import com.example.gifsnap.domain.models.Gif
import com.example.gifsnap.util.getAverageColor
import com.example.gifsnap.presentation.common.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun GifCard(
    gif: Gif,
    onDominantColorChanged: (Color) -> Unit
) {
    if(gif.id == "-1"){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(100.dp),
                imageVector = Icons.Default.ImageNotSupported,
                contentDescription = null,
                tint = colorResource(id = R.color.beige)
            )
        }
    } else{

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(gif.url)
                    .size(Size.ORIGINAL)
                    .build()
            ).state
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

            if (painter is AsyncImagePainter.State.Loading) {
                ShimmerGifItem(
                    isLoading = true,
                    contentAfterLoading = {}
                )
            }
            if (painter is AsyncImagePainter.State.Error) {
                ErrorLoadingItem(
                    modifier = Modifier
                        .size(400.dp)
                        .background(Color.LightGray)
                        .clip(RoundedCornerShape(24.dp))
                )
            }

            if (painter is AsyncImagePainter.State.Success) {

                val bitmap = painter.result.drawable.toBitmap().asImageBitmap()
                onDominantColorChanged(getAverageColor(bitmap))

                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    model = gif.url,
                    contentDescription = gif.title,
                    imageLoader = imageLoader,
                    contentScale = ContentScale.Crop
                )
            }

    }

}