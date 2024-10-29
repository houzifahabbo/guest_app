package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val controller: NavController = rememberNavController()
                val currentBackStackEntry = controller.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry.value?.destination?.route
                val viewModel = GuestsViewModel()
                Scaffold(
                    floatingActionButton = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 32.dp),
                            contentAlignment = Alignment.BottomStart,
                        ) {
                            if (currentRoute == Screen.Home.route) {
                                FloatingActionButton(
                                    onClick = {
                                        viewModel.fetchGuests()
                                        controller.navigate(Screen.QrCode.route)
                                    },
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.rounded_scan_24),
                                        contentDescription = "Add"

                                    )
                                }
                            } else if (currentRoute == Screen.QrCode.route) {
                                FloatingActionButton(onClick = {
                                    controller.navigate(Screen.Home.route)
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Home,
                                        contentDescription = "Home"
                                    )
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Navigation(controller, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

