package com.sultantauhid3127.assessment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sultantauhid3127.assessment1.navigation.NavGraph
import com.sultantauhid3127.assessment1.ui.theme.Assessment1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val isDarkTheme = remember { mutableStateOf(false) }
            val toggleTheme = {
                isDarkTheme.value = !isDarkTheme.value
            }

            Assessment1Theme(darkTheme = isDarkTheme.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(
                        navController = navController,
                        onChangeTheme = toggleTheme
                    )
                }
            }
        }
    }
}

