package com.example.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.data.Guest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SuccessView(id: String, controller: NavController, viewModel: GuestsViewModel = viewModel()) {
    val guest by viewModel.getAGuestById(id).collectAsState(initial = Guest())

    LaunchedEffect(guest) {
        delay(3000)
        withContext(Dispatchers.Main) {
            controller.navigate(Screen.Home.route)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(
                    Alignment.Center
                ),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = "Success",
                tint = Color(0xFF007905),
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = "اهلا بك ${guest.name}",
                style =
                TextStyle(
                    textDirection = TextDirection.Rtl,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center

                )
            )
            Text(text = "تم تسجيل حضورك بنجاح", style = TextStyle(fontSize = 20.sp))

        }
    }

}

