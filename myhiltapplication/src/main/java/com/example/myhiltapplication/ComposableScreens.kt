package com.example.myhiltapplication

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.myhiltapplication.navhost.Screen

@Composable
fun FirstComposable(navController: NavHostController) {
    println("#TestStateVariable, FirstComposable, start")
    LaunchedEffect(Unit) {
        println("#TestStateVariable, FirstComposable, inside LaunchedEffect")
    }

    Button(onClick = {
        navController.navigate(Screen.SECOND.name)
    }) {
        Text(text = "Navigate to second composable using nav controller")
    }
    DisposableEffect(Unit) {
        onDispose {
            println("#TestStateVariable, FirstComposable, DisposableEffect, onDispose}")
        }
    }
}

@Composable
fun SecondComposable(navController: NavHostController) {
    println("#TestStateVariable, SecondComposable, start")
    LaunchedEffect(Unit) {
        println("#TestStateVariable, SecondComposable, inside LaunchedEffect")
    }

    Button(onClick = {
        navController.navigate(Screen.Third.name)
    }) {
        Text(text = "Navigate to third composable using nav controller")
    }
    DisposableEffect(Unit) {
        onDispose {
            println("#TestStateVariable, SecondComposable, DisposableEffect, onDispose}")
        }
    }
}

@Composable
fun ThirdComposable(navController: NavHostController) {
    println("#TestStateVariable, ThirdComposable, start")
    LaunchedEffect(Unit) {
        println("#TestStateVariable, ThirdComposable, inside LaunchedEffect")
    }
    Button(onClick = {
        //navController.navigate(Screen.Third.name)
    }) {
        Text(text = "Inside third one")
    }
    DisposableEffect(Unit) {
        onDispose {
            println("#TestStateVariable, ThirdComposable, DisposableEffect, onDispose}")
        }
    }
}