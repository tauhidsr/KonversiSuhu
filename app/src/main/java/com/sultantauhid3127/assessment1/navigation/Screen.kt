package com.sultantauhid3127.assessment1.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Convert : Screen("convert")
    data object About : Screen("about")
}