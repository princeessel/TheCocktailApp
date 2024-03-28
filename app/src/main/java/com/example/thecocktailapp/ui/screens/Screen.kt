package com.example.thecocktailapp.ui.screens

sealed class Screen(val route: String) {
    data object Home: Screen(route = "home/{drinkObject}")
    data object Details: Screen(route = "details/{drinkObject}")
}
