package com.example.gifsnap.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp

@Composable
fun ErrorLoadingItem(
    modifier: Modifier
){
    Box (
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Icon(
            modifier = Modifier
                .size(70.dp),
            imageVector = Icons.Default.ImageNotSupported,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.inverseOnSurface
        )
    }
}