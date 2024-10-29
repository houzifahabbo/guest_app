package com.example.myapplication

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object QrCode : Screen("qr_code")
    object Success : Screen("success")
    object Error : Screen("error")
}

val screenList = listOf(Screen.Home, Screen.QrCode, Screen.Success, Screen.Error)