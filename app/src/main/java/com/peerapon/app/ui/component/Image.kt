package com.peerapon.app.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import coil.compose.rememberImagePainter
import com.peerapon.app.R

@Composable
fun ArticleImage(
    url: String,
    modifier: Modifier = Modifier,
    height: Dp = dimensionResource(id = R.dimen.image_dimen),
) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                crossfade(true)
            }),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    )
}