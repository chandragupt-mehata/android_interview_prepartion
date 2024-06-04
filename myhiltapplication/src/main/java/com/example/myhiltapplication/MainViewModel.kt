package com.example.myhiltapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import javax.inject.Singleton

@FragmentScoped
class MainViewModel : ViewModel() {

    private val _state: MutableStateFlow<String> = MutableStateFlow("")
    val state1: StateFlow<String> = _state
    val state2 = mutableStateOf("hello")
    private val exception = CoroutineExceptionHandler { _, exception ->
        println("view model: launchBgTask exception caught, ${exception.message}")
    }

    private var counter = 0

    val typingTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    fun updateCounter() {
        /*viewModelScope.launch {
            counter ++
            _state.emit((counter).toString())
        }*/
        counter++
        //_state.value = counter.toString()
        state2.value = counter.toString()
    }

    suspend fun stateIn(): StateFlow<String> {
        return state1.map { it + 2 }.stateIn(viewModelScope)
    }

    /**
     * on back press if activity get destroyed (we are calling finish on back key) then
     * viewmodel oncleared will get called which will cancel all coroutine which is inside
     * that scope but other coroutine which will be running with diff scope rather than
     * view model scope, will keep on running.
     */
    fun launchBgTask() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                while (true) {
                    delay(1000)
                    println("view model: launchBgTask independent, ${bgCounter++}")
                }
            }
            while (true) {
                delay(1000)
                println("view model: launchBgTask inside viewModelScope, ${bgCounter++}")
            }
        }
    }

    /**
     * here after exception occurs since we are not catching exception app is being terminated so coroutine
     * within view model scope also being terminated even if we create any other coroutine with diff scope
     * that will get terminated.
     */
    fun handleException() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                println("view model: handleException inside viewModelScope, ${bgCounter++}")
                if (bgCounter == 5) {
                    throw RuntimeException("")
                }
            }
        }
    }

    //on back pressed cancelled exception does not get triggered
    //but when delay will be uncommented, cancellation exception will get caught
    //it meant on back pressed coroutine cancellation will lead to delay to throw cancellation excetion
    //because it's suspend function
    fun checkCancellation() {
        viewModelScope.launch(Dispatchers.Default) {
            launch {
                try {
                    while (true) {
                        println("view model: checkCancellation: $bgCounter and isActive: $isActive")
                        Thread.sleep(1000)
                        //delay(1000)
                        bgCounter++
                    }
                } catch (exception: CancellationException) {
                    println("view model: checkCancellation: caught cancellation exception")
                }
            }
            delay(2000)
            //this.cancel()
        }
    }

    fun checkWithContextBehaviourOnBackPressed() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                println("view model: checkWithContextBehaviourOnBackPressed: viewModelScope $bgCounter and isActive: $isActive")
                delay(1000)
                bgCounter++
            }
        }
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                while (true) {
                    println("view model: checkWithContextBehaviourOnBackPressed: independent coroutine $bgCounter and isActive: $isActive")
                    delay(1000)
                    bgCounter++
                }
            }
            withContext(Dispatchers.IO) {
                while (true) {
                    println("view model: checkWithContextBehaviourOnBackPressed: withContext $bgCounter and isActive: $isActive")
                    delay(1000)
                    bgCounter++
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("view model: onCleared")
    }

    var resultFlow = 0

    fun dummyKotlinFlow(emitter: Flow<Int> = emitter()) {
        viewModelScope.launch() {
            emitter.collect {
                resultFlow += it
            }
            println("result is: $resultFlow")
        }
    }

    suspend fun dummyKotlinFlowWithoutViewModelScope() {
        emitter().collect {
            resultFlow += it
        }
        println("result is: $resultFlow and thread: ${Thread.currentThread().name}")
    }

    fun emitter(): Flow<Int> = flow {
        repeat(5) {
            println("inside emitter: $it")
            delay(5000)
            emit(it)
        }
    }

    private var bgCounter = 0

}