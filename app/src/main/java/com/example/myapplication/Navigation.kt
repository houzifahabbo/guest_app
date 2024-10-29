package com.example.myapplication

import QRScannerScreen
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun Navigation(
    controller: NavController,
    modifier: Modifier,
) {
    NavHost(
        navController = controller as NavHostController,
        startDestination = Screen.Home.route,
        modifier = modifier.background(MaterialTheme.colorScheme.background)

    ) {
        composable(Screen.Home.route) {
            GuestsView()
        }
        composable(Screen.QrCode.route) {
            QRScannerScreen(controller)
        }

        composable(Screen.Success.route + "/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
                nullable = false
            }
        ))
        { entry ->
            val id = if (entry.arguments != null) entry.arguments!!.getString("id")!! else ""
            Log.d("QRScannerScreen", "id: $id")
            SuccessView(id = id, controller = controller)
        }
        composable(Screen.Error.route) {
            ErrorView(controller)
        }
    }
}


