package com.sultantauhid3127.assessment1.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConvertScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Konversi Suhu")
                }
            )
        }
    ) { innerPadding ->
        Text(
            text = "Ini Convert Screen",
            modifier = Modifier.padding(innerPadding)
        )
    }
}