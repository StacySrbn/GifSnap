package com.example.gifsnap.presentation.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.gifsnap.R
import com.example.gifsnap.presentation.detail.DetailState

@Composable
fun TopSection(
    detailState: DetailState,
    navController: NavHostController
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(top = 70.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Row (
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(30.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Row (
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                text = detailState.gif.title,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}