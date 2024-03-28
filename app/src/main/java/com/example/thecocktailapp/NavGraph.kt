package com.example.thecocktailapp

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.thecocktailapp.ui.screens.DetailsScreen
import com.example.thecocktailapp.ui.screens.HomeScreen
import com.example.thecocktailapp.ui.screens.Screen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {

            HomeScreen(onNavigateToDetailsScreen = { drink ->

                navController.navigate(
                    Screen.Details.route.replace(
                        oldValue = "{drinkObject}",
                        newValue = drink.idDrink
                    )
                )
            })
        }
        composable(Screen.Details.route) { navBackStackEntry ->
            val drinkId = navBackStackEntry.arguments?.getString("drinkObject")
            drinkId?.let {
                DetailsScreen(drinkId = it)
            }
        }
    }
}