package com.example.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun ErrorView(controller: NavController) {
    LaunchedEffect(Unit) {
        delay(3000) // Delay for 3 seconds
        controller.navigate(Screen.Home.route) // Navigate back to home page
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
                imageVector = Icons.Rounded.Clear,
                contentDescription = "Error",
                tint = Color(0xFFD32F2F),
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = "تم تسجيل دخولك من قبل",
                style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
            )


        }
    }
}