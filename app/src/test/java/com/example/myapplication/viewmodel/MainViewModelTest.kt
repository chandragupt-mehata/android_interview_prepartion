package com.example.myapplication.viewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Test


class MainViewModelTest {

    private val mainViewModel = MainViewModel(savedStateHandle = SavedStateHandle())

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testTestParallelDecomposition() {
        runTest{
            Dispatchers.setMain(StandardTestDispatcher())
            mainViewModel.testParallelDecomposition()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDummy() {
        runTest(StandardTestDispatcher()){
            var testValue = "default"
            launch {
                delay(1000)
                testValue = "launch 1"
            }
            launch {
                delay(500)
                testValue = "launch 2"
            }
            advanceUntilIdle()
            assertEquals(testValue, "launch 1")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testTestDispatchers() {
        runTest{
            Dispatchers.setMain(StandardTestDispatcher(testScheduler))
            val res = mainViewModel.testDispatcher()
            advanceUntilIdle()
            assertEquals(mainViewModel.value, "updated")
            assertEquals(res, "updated")
        }
    }

    private val flow1 = flow<Int> {
        emit(9)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testYourFlow() {
        runTest{
            Dispatchers.setMain(StandardTestDispatcher(testScheduler))
            mainViewModel.yourFlow(CoroutineScope(StandardTestDispatcher(testScheduler)))
            val newValues = mainViewModel.stateFlow2.take(3).toList()
            assertEquals(newValues.size, 3)
        }
    }
}