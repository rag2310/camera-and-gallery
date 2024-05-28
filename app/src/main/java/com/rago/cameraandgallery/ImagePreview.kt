package com.rago.cameraandgallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import java.io.File

@Composable
fun ImagePreview(file: File) {
    Box(
        modifier = Modifier.size(100.dp) // Ajusta el tamaño según sea necesario
    ) {
        Image(
            painter = rememberImagePainter(file),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
    }
}