package com.example.myhiltapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun TypeLaunchedEffect(navController: NavHostController) {

    val isLoaderDisplayed = remember { mutableStateOf(false) }
    val data = remember { mutableStateOf(listOf<String>()) }

    println("Root composable is composed")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        println("Column composable is composed isLoaderDisplayed.value: ${isLoaderDisplayed.value}")


        // This side effect is launched only when the boolean value is true(Initially its false)
        LaunchedEffect(Unit){

            println("LaunchedEffect is invoked :--> Before fetching the data")
            //isLoaderDisplayed.value = true
            // Perform a long running operation that takes time
            data.value = fetchData()

            println("LaunchedEffect is invoked :--> After fetching the data")

            // Reset to false
            isLoaderDisplayed.value = false;
        }


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            println("Column composable (parent of button) is composed")

            Button(onClick = { /*TODO*/ }) {
                Text(text = "\"Fetch Data\"")
                isLoaderDisplayed.value = true
                println("Button click invoked")
            }

            /*AppButton(text = "Fetch Data"){
                isLoaderDisplayed.value = true
                println("Button click invoked")
            }*/
            if (isLoaderDisplayed.value) {
                // Show a loading indicator
                CircularProgressIndicator()

                println("Loader is shown")
            } else {
                // Show the data
                LazyColumn {
                    items(data.value.size) { index ->
                        Text(text = data.value[index])
                    }
                }
                println("List is displayed")
            }
        }

    }

}

// Simulate a network call by suspending the coroutine for 2 seconds
private suspend fun fetchData(): List<String> {
    // Simulate a network delay
    delay(10000)
    return listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5",)
}