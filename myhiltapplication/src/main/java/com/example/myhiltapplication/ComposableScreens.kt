package com.example.myhiltapplication

import android.annotation.SuppressLint
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.example.myhiltapplication.navhost.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FirstComposable(navController: NavHostController) {
    println("#TestStateVariable, FirstComposable, start")

    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        while (true) {
            delay(5000)
            println("#scope.inside.composable: rememberCoroutineScope outside launchedEffect:")
        }
    }

    CoroutineScope(Dispatchers.IO).launch {
        while (true) {
            delay(5000)
            println("#scope.inside.composable: independent scope outside launchedEffect:")
        }
    }


    // This side effect is launched only when the boolean value is true(Initially its false)
    LaunchedEffect(Unit){
        println("#TestStateVariable, FirstComposable, inside LaunchedEffect")
        coroutineScope.launch {
            while (true) {
                delay(5000)
                println("#scope.inside.composable: rememberCoroutineScope inside launchedEffect:")
            }
        }
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