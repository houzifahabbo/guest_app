import android.Manifest
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.GuestsViewModel
import com.example.myapplication.Screen


@Composable
fun RequestCameraPermission(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }
}


@Composable
fun CameraPreview(
    onQRCodeScanned: (String) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(
                                ContextCompat.getMainExecutor(ctx),
                                QRCodeAnalyzer(onQRCodeScanned)
                            )
                        }

                    val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )

                }, ContextCompat.getMainExecutor(ctx))

                previewView
            },
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier

                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .size(250.dp)
                    .background(color = Color.Transparent, shape = RoundedCornerShape(16.dp))
                    .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp))
            )
            Text(
                text = "امسح رمز ال QR",
                color = Color.White,
                fontSize = 20.sp,
                style = TextStyle(
                    textDirection = TextDirection.Rtl
                ),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun QRScannerScreen(controller: NavController, viewModel: GuestsViewModel = viewModel()) {
    var scannedQRCode by remember { mutableStateOf<String?>(null) }
    var hasCameraPermission by remember { mutableStateOf(false) }

    RequestCameraPermission {
        hasCameraPermission = true
    }
    // Check if the permission is granted
    if (hasCameraPermission) {
        // Show the camera preview if permission is granted
        CameraPreview() { qrCode ->
            scannedQRCode = qrCode
        }
    } else {
        // Display a message to inform the user
        Text(
            text = "Camera permission required to scan QR codes",
            modifier = Modifier.padding(16.dp)
        )
    }

    // Display the scanned QR code, if available
    val parsedQRCode = scannedQRCode?.trim()
    if (parsedQRCode != null) {
        val guestState =
            viewModel.getAGuestById(parsedQRCode).collectAsState(initial = null)
        val guest = guestState.value
        LaunchedEffect(guest) {
            if (guest != null) {
                scannedQRCode = null
                if (guest._id.isNotBlank() && !guest.isAttending) {
                    viewModel.updateGuest(guest.copy(isAttending = true))
                    controller.navigate(Screen.Success.route + "/${guest._id}")
                } else {
                    controller.navigate(Screen.Error.route)
                }
            }
        }
    }
}




