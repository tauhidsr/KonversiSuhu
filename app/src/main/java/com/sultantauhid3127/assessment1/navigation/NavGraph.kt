package com.sultantauhid3127.assessment1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sultantauhid3127.assessment1.ui.screen.AboutScreen
import com.sultantauhid3127.assessment1.ui.screen.ConvertScreen
import com.sultantauhid3127.assessment1.ui.screen.HomeScreen


@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    onChangeTheme: () -> Unit = {}
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, onChangeTheme = onChangeTheme)
        }
        composable(Screen.Convert.route) {
            ConvertScreen(navController = navController)
        }
        composable(Screen.About.route) {
            AboutScreen(navController = navController)
        }
    }
}