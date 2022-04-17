package com.lindevhard.felia.ui.wallet.component

import android.Manifest
import android.view.View
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.lindevhard.felia.R
import com.lindevhard.felia.ui.components.button.PrimaryButton
import com.lindevhard.felia.ui.utils.OnClick
import com.lindevhard.felia.ui.utils.OnValueChange
import com.lindevhard.felia.utils.QrCodeAnalyzer
import com.lindevhard.felia.utils.goAppPermissions

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrScreen(
    onQrCodeRecognized: OnValueChange
) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    PermissionRequired(
        permissionState = cameraPermissionState,
        permissionNotGrantedContent = {
            CameraPermissionNotGrantedContent(
                R.string.msg_please_grant_camera_permission_qr,
                cameraPermissionState::launchPermissionRequest
            )
        },
        permissionNotAvailableContent = {
            CameraPermissionNotAvailableContent(
                R.string.msg_please_grant_camera_permission_from_settings,
                context::goAppPermissions
            )
        },
        content = {
            QrCameraPreview(
                modifier = Modifier.fillMaxSize(),
                onQrCodeRecognized = onQrCodeRecognized
            )
        }
    )
}

@Composable
fun QrCameraPreview(
    modifier: Modifier = Modifier,
    onQrCodeRecognized: OnValueChange
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraPreview = remember {
        PreviewView(context).apply {
            id = View.generateViewId()
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }
    val cameraProviderFeature = remember { ProcessCameraProvider.getInstance(context) }
    val executor = remember { ContextCompat.getMainExecutor(context) }
    val preview = remember {
        Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
//            .setTargetResolution(Size(1280, 720))
            .build().also {
                it.setSurfaceProvider(cameraPreview.surfaceProvider)
            }
    }
    val imageCapture = remember {
        ImageCapture.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }
    val imageAnalysis = remember {
        ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(
                    executor,
                    QrCodeAnalyzer { qrCode -> onQrCodeRecognized(qrCode) }
                )
            }
    }
    val cameraSelector = remember { CameraSelector.DEFAULT_BACK_CAMERA }
    DisposableEffect(Unit) {
        val cameraProvider = cameraProviderFeature.get()

        cameraProviderFeature.addListener(
            {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture,
                    imageAnalysis
                )
            },
            executor
        )

        onDispose { cameraProvider.unbindAll() }
    }

    AndroidView({ cameraPreview }, modifier)
}

@Composable
fun CameraPermissionNotGrantedContent(explanationStringId: Int, onClick: OnClick) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(explanationStringId),
            color = Color.White,
            fontSize = 24.sp,
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = stringResource(id = R.string.msg_gran_permission),
            onClick = onClick
        )
    }
}

@Composable
fun CameraPermissionNotAvailableContent(explanationStringId: Int, onClick: OnClick) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(explanationStringId),
            color = Color.White,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = stringResource(id = R.string.msg_gran_permission),
            onClick = onClick
        )
    }
}