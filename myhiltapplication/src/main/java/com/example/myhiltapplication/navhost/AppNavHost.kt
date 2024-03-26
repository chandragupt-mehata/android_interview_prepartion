package com.example.myhiltapplication.navhost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myhiltapplication.SecondComposable
import com.example.myhiltapplication.ThirdComposable
import com.example.myhiltapplication.TypeLaunchedEffect

@Composable
fun AppNavHost(startDestination: String = Screen.FIRST.name, navController: NavHostController) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = startDestination) {
            TypeLaunchedEffect(navController)
        }
        composable(route = Screen.SECOND.name) {
           SecondComposable(navController)
        }
        composable(route = Screen.Third.name) {
            ThirdComposable(navController)
        }
    }
}