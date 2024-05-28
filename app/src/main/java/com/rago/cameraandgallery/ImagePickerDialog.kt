package com.rago.cameraandgallery

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import java.io.File

@RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
@Composable
fun ImagePickerDialog(
    showDialog: MutableState<Boolean>,
    onImagePicked: (File) -> Unit,
    imageCaptureViewModel: ImageCaptureViewModel = viewModel()
) {
    val context = LocalContext.current
    val launcherImages = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Uri? = result.data?.data
            val file: File? = if (data != null) {
                getFile(context, data)
            } else {
                imageCaptureViewModel.currentPhotoPath?.let { File(it) }
            }
            file?.let {
                onImagePicked(it)
                showDialog.value = false
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            confirmButton = {},
            title = { Text(text = "Subir Foto") },
            text = { Text(text = "elija_forma_subir_foto") },
            dismissButton = {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    TextButton(onClick = {
                        val intent = imageCaptureViewModel.getGalleryIntent()
                        launcherImages.launch(intent)
                    }) {
                        Text(text ="galeria_imagenes")
                    }

                    TextButton(onClick = {
                        val intent = imageCaptureViewModel.getImageCaptureIntent(context)
                        if (intent != null) {
                            launcherImages.launch(intent)
                        }
                    }) {
                        Text(text = "camara_fotografica")
                    }
                }
            }
        )
    }
}

fun getFile(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "temp_image")
    val outputStream = file.outputStream()
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
    return file
}