package com.example.boostar_uaal.ui.screen.arScreen

import android.view.LayoutInflater


import android.view.ViewStub
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.snap.camerakit.Session
import com.snap.camerakit.invoke
import com.snap.camerakit.support.camerax.CameraXImageProcessorSource
import android.Manifest
import android.util.Log
import androidx.compose.material3.IconButton
import android.widget.Button
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import com.example.camera_kit.R
import com.snap.camerakit.lenses.LensesComponent
import com.snap.camerakit.lenses.whenHasFirst

// CameraKitScreen.kt
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ArScreen(
    back: () -> Unit,
    lensId: String,
    lensGroupId: String,
    onPermissionDenied: () -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val imageProcessorSource = remember {
        CameraXImageProcessorSource(context = context, lifecycleOwner = lifecycleOwner)
    }
    val cameraKitSession = remember { mutableStateOf<Session?>(null) }

    DisposableEffect(Unit) {
        onDispose { cameraKitSession.value?.close() }
    }

    Box(){
        IconButton(
            onClick = { back },
            content = { Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Arrow Back",
                tint = Color.Gray
            ) }
        )
        when {
            cameraPermissionState.status.isGranted -> {
                imageProcessorSource.startPreview(true)
                AndroidView(
                    factory = { ctx ->
                        LayoutInflater.from(ctx).inflate(R.layout.camera_layout, null).apply {
                            val viewStub = findViewById<ViewStub>(R.id.camera_kit_stub)

                            cameraKitSession.value = Session(context = ctx) {
                                imageProcessorSource(imageProcessorSource)
                                attachTo(viewStub)
                            }.apply {
                                lenses.repository.observe(
                                    LensesComponent.Repository.QueryCriteria.ById(
                                        id = lensId.trim(),
                                        groupId = lensGroupId.trim(),
                                    )
                                ) { result ->
                                    Log.d("LENSES GroupId", lensGroupId)
                                    Log.d("LENSES LensId", lensId)
                                    Log.d("LENSES result", "$result")
                                    result.whenHasFirst { requestedLens ->
                                        lenses.processor.apply(requestedLens)
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            cameraPermissionState.status.shouldShowRationale -> onPermissionDenied()
            else -> LaunchedEffect(Unit) { cameraPermissionState.launchPermissionRequest() }
        }

    }
}