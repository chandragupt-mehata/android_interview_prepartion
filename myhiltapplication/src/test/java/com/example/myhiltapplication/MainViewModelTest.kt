package com.example.myhiltapplication

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
        mainViewModel = MainViewModel()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun dummyKotlinFlowSample() {
        runTest {
            launch(UnconfinedTestDispatcher()) {
                val list = mutableListOf<Int>()
                val flow = flowOf(1, 2, 3, 4, 5)
                flow.collect {
                    list.add(it)
                    delay(5000)
                }
                println(list)
            }
        }
    }

    @Test
    fun dummyKotlinFlowSampleTwo() {
        runTest {
            val list = mutableListOf<Int>()
            val flow = mainViewModel.emitter()
            //mainViewModel.dummyKotlinFlow(flow)
            flow.collect {
                println("value inside collector: $it")
                list.add(it)
                //delay(5000)
            }
            println("$list and viewmodel value: ${mainViewModel.resultFlow}")
            assertEquals(list, mutableListOf(0, 1, 2, 3, 4))
        }
    }

    @Test
    fun dummyKotlinFlowWithoutViewModelScopeTest() {
        runTest {
            mainViewModel.dummyKotlinFlowWithoutViewModelScope()
            println("viewmodel value: ${mainViewModel.resultFlow}")
            assertEquals(10, mainViewModel.resultFlow)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun dummyKotlinFlowTest() = runTest {
        mainViewModel.dummyKotlinFlow(emitter = mainViewModel.emitter())
        advanceUntilIdle()
        println("value inside test is: ${mainViewModel.resultFlow}")
        assertEquals(10, mainViewModel.resultFlow)
    }

    @Test
    fun emitterTest() = runBlocking {
        val list = mainViewModel.emitter().toList()
        println("list is: $list")
    }

    @Test
    fun emitterTestTwo() {
        val flow = mainViewModel.emitter()
        val emittedValues = mutableListOf<Int>()
        runBlocking {
            flow.collect {
                emittedValues.add(it)
            }
        }
        assertEquals(listOf(0, 1, 2, 3, 4), emittedValues)
    }
}