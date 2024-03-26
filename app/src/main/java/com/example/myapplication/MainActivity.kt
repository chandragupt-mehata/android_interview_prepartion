package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragment.FragmentA
import com.example.myapplication.test.BuilderParentClass
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.MainViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("#lifecycle", "onViewStateRestored activity")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("#lifecycle", "onRestoreInstanceState activity")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("#lifecycle", "onRestart activity")
    }

    override fun onStart() {
        super.onStart()
        Log.d("#lifecycle", "onStart activity")
    }

    override fun onResume() {
        super.onResume()
        Log.d("#lifecycle", "onResume activity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("#lifecycle", "onCreate activity")
        //registerReceiver(AeroplaneModeReceiver(), IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        Log.d("#viewmodel", "#viewmodel onCreate activity: $viewModel")
        testStateFlowLiveDataInBackground(viewModel, this)
        binding.name = "Increase Counter"
        binding.lastName = "Navigate to other fragment"
        binding.className = BuilderParentClass("Increase Counter", "Navigate to other fragment")
        //supportFragmentManager.beginTransaction().add(R.id.fragment_container, FragmentA()).commit()
        viewModel.testWithViewModelScope(lifecycleScope)
        binding.activityText.setOnClickListener {
            Log.d("hello", "Increase counter")
            Log.d(MainActivity::class.java.toString(), "Increase counter")
            viewModel.increaseCounter()
        }
        binding.activityLastNameText.setOnClickListener {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, FragmentA()).commit()
        }
        setUpRecyclerViewAdapter()
        testSuspendNetworkOperation(viewModel)
        testParallelDecomposition(viewModel)
        testThreadExecuters(viewModel)

        /*setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<MyWorkManager>().build()
                            val result =
                                WorkManager
                                    .getInstance(this)
                                    .enqueueUniqueWork(
                                        "ac",
                                        ExistingWorkPolicy.KEEP,
                                        oneTimeWorkRequest
                                    )
                            println("result is: $result")
                            WorkManager
                                .getInstance(this)
                                .getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
                                .observe(this) {
                                    println("result is inside observer: ${it.outputData}")
                                }
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }*/
    }

    private fun testThreadExecuters(viewModel: MainViewModel) {
         lifecycleScope.launch {
             viewModel.verifyAsyncWait()
         }
    }

    private fun testParallelDecomposition(viewModel: MainViewModel) {
        viewModel.testParallelDecomposition()
    }

    private fun testSuspendNetworkOperation(viewModel: MainViewModel) {
        viewModel.getResult()
    }

    private fun testStateFlowLiveDataInBackground(
        viewModel: MainViewModel,
        mainActivity: MainActivity
    ) {
        viewModel.liveData.observe(mainActivity) {
            println("#testStateFlowLiveDataInBackground, liveData : $it")
        }
        lifecycleScope.launch() {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // If the app goes to the background (state transitions to STOPPED), the coroutine will be canceled.
                // If the app comes back to the foreground (state transitions to STARTED), a new coroutine will be launched, and the loop will start again.
                for (i in 1..15) {
                    // Simulating some work with delays
                    delay(1000)
                    println("#testStateFlowLiveDataInBackground Repeating Task is running: $i")
                }
            }
        }

        lifecycleScope.launch() {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect {
                    println("#testStateFlowLiveDataInBackground, stateFlow : $it")
                }
            }
        }

        lifecycleScope.launch() {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.yourFlow(this)
                viewModel.stateFlow.collect() {
                    println("new test simulation yourFlow $it")
                }
            }
        }
        lifecycleScope.launch {
            viewModel.sharedFlow.collect {
                println("#testStateFlowLiveDataInBackground, sharedFlow : $it")
            }
        }
        lifecycleScope.launch {
            viewModel.channel.collect {
                println("#testStateFlowLiveDataInBackground, channel : $it")
            }
        }
        viewModel.stateFlowNonMutable.onEach { value ->
            println("#testStateFlowLiveDataInBackground, stateFlowNonMutable : $value")
        }.launchIn(lifecycleScope)
    }

    private fun setUpRecyclerViewAdapter() {
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ItemsViewModel>()
        for (i in 1..2000) {
            data.add(ItemsViewModel(R.drawable.ic_launcher_background, "Item $i"))
        }
        val adapter = MyRecyclerViewAdapter(data)
        binding.recyclerview.adapter = adapter
    }

    companion object {
        const val abs: String = ""
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}

@Composable
fun ShowSnackBar() {
    val (snackbarVisibleState, setSnackBarState) = remember {
        mutableStateOf(false)
    }
    Snackbar(action = {
        Button(onClick = { setSnackBarState(false)}) {

        }
    }, modifier = Modifier.padding(8.dp)) {
       Text(text = "My snackbar")
    }
}

fun <T> StateFlow<T>.asLiveData(): LiveData<T> = this.asLiveData()
