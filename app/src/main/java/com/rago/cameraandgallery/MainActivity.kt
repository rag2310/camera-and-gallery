package com.rago.cameraandgallery

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rago.cameraandgallery.ui.theme.CameraAndGalleryTheme
import java.io.File

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CameraAndGalleryTheme {
                val showDialog = remember { mutableStateOf(false) }
                val imageCaptureViewModel: ImageCaptureViewModel = viewModel()
                var selectedFile by remember { mutableStateOf<File?>(null) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Button(onClick = { showDialog.value = true }) {
                                Text("Pick Image")
                            }
                            selectedFile?.let {
                                ImagePreview(file = it)
                            }
                        }
                    }

                    ImagePickerDialog(
                        showDialog = showDialog,
                        onImagePicked = { file ->
                            // Handle the picked image file here
                            Log.i("RODO", "onCreate: ${file.path}")
                            selectedFile = file
                        },
                        imageCaptureViewModel = imageCaptureViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CameraAndGalleryTheme {
        Greeting("Android")
    }
}