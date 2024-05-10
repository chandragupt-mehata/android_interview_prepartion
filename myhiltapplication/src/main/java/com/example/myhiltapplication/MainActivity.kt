package com.example.myhiltapplication

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.ListPopupWindow.MATCH_PARENT
import androidx.appcompat.widget.ListPopupWindow.WRAP_CONTENT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.myaidlserver.IMyAidlColorInterface
import com.example.myhiltapplication.foregroundservice.MyForegroundService
import com.example.myhiltapplication.model.ResponseDTO
import com.example.myhiltapplication.navhost.AppNavHost
import com.example.myhiltapplication.test.TestDiHere
import com.example.myhiltapplication.test.TestDiIndependentHilt
import com.example.myhiltapplication.test.TestDiIndependentTwoHilt
import com.example.myhiltapplication.ui.theme.MyApplicationTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Named

/**
 * for hilt
 * TestDiIndependentTwoHilt this is independent dependency. If you will give a scope of ActivityScoped then it will always have a single instance
 * within single activity life cycle.
 * But activity changes then it will have new instance
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @Named("one")
    lateinit var testDiHere: TestDiHere

    @Inject
    lateinit var testDiIndependentHilt: TestDiIndependentHilt

    @Inject
    lateinit var testDiIndependentTwoHilt: TestDiIndependentTwoHilt
    private var iaidlColorInterface: IMyAidlColorInterface? = null

    override fun onDestroy() {
        super.onDestroy()
        println("view model: activity onDestroy")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //finish()
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("testDiHere: $testDiHere and testDiIndependentHilt: $testDiIndependentHilt")
        println("testDiHere: testDiIndependentTwoHilt inside MainActivity: $testDiIndependentTwoHilt")
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        bindService()
        throwCoroutineException()
        viewModel.checkCancellation()
        setComposableNavHost()
        //setComposableComponent(viewModel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setComposableComponent(viewModel: MainViewModel) {
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                    Column {
                        Text(text = "start foreground",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(top = 45.dp)
                                .clickable {
                                    val color: Int = iaidlColorInterface!!.color
                                    println("fetched color is: $color")
                                    val myForegroundService =
                                        Intent(this@MainActivity, MyForegroundService::class.java)
                                    myForegroundService.action = "foreground_action"
                                    startForegroundService(myForegroundService)
                                    CoroutineScope(Dispatchers.IO).launch {
                                        delay(5000)
                                        //stopService(myForegroundService)
                                    }
                                    //connectNetwork()
                                })

                        Text(text = "start new Activity",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(top = 45.dp)
                                .clickable {
                                    val intent =
                                        Intent(this@MainActivity, MainActivity2::class.java)
                                    startActivity(intent)
                                })

                        Text(text = "Print test di independent",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(top = 45.dp)
                                .clickable {
                                    testDiIndependentHilt.printIt()
                                })

                        /*GreetingPreview(viewModel)
                        TestStateVariable()*/
                        /*StateFullComposable()
                        SnackbarExample()
                        EditTextTypingListener(viewModel)*/
                        LocalComposableFunction(viewModel = viewModel)
                    }
                }
            }
        }
    }

    private fun setComposableNavHost() {
        setContent {
            AppNavHost(navController = rememberNavController())
        }
    }

    private fun bindService() {/*Log.d("iaidlColorInterface-> before", "$iaidlColorInterface")
        val intent = Intent("AIDLColorService")
        intent.setPackage("com.example.myaidlserver")
        bindService(intent, mConnection, BIND_AUTO_CREATE)*/
    }

    companion object {
        const val str: String = ""
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            //iaidlColorInterface = IMyAidlColorInterface.Stub.asInterface(iBinder)
            Log.d("iaidlColorInterface-> after ", "$iaidlColorInterface")
            println("testDiHere:after  $testDiHere")
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.d("iaidlColorInterface-> after ", "error appeared")
            println("testDiHere: after error $testDiHere")
        }
    }

    private fun connectNetwork() {
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            val requestBuilder =
                HttpUrl.parse("https://reqres.in/api/users/2")?.newBuilder()?.also {
                    //it.addQueryParameter()
                }
            val request = Request.Builder().url("https://reqres.in/api/users/2").build()
            val response = client.newCall(request).execute()
            val string = response.body()?.string()
            println("responseDTO: string $string")
            val responseDTO = Gson().fromJson(string, ResponseDTO::class.java)
            //val json = JSONObject(response.body().toString()) // to string returns object where as string return value
            println("responseDTO:  ${responseDTO.toString()}")
        }
    }

    private fun throwCoroutineException() {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("exception caught is: $exception")
        }

        runBlocking {
            launch(exceptionHandler) {
                launch {
                    supervisorScope() {
                        launch {
                            repeat(10) {
                                delay(100)
                                println("child 1: $it")
                            }
                        }
                        launch {
                            repeat(10) {
                                delay(100)
                                println("child 2: $it")
                            }
                        }
                        launch {
                            delay(500)
                            println("child 3: ")
                            throw RuntimeException()
                        }
                    }
                }
                repeat(10) {
                    delay(100)
                    println("parent: $it")
                }
            }
        }
        println("parent: end")
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun Greeting(name: String) {
    var state by mutableStateOf(false)
    Log.d("recomposition, ", "here inside Greeting")

    Text(text = "Hello $state", modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .clickable {
            Log.d("recomposition, ", "before click here inside Greeting: $state")
            state = !state
            Log.d("recomposition, ", "after click here inside Greeting: $state")
        })
}

@Preview(showBackground = true)
@Composable
fun abc() {

}

@Preview(showBackground = true)
@Composable
fun abc1() {

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GreetingPreview(viewModel: MainViewModel) {
    Log.d("recomposition, ", "here inside GreetingPreview")
    val state1Change = viewModel.state1.collectAsState()
    val state2Change = viewModel.state2
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    //for life cycle event
    PerformLifeCycleEvent(lifecycleOwner = lifecycleOwner, onPause = {}, onStart = {})

    Column {
        Text(text = "how is it: cvcvcv ${state1Change.value} and ${state2Change.value}",
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(top = 45.dp)
                .clickable {
                    viewModel.updateCounter()
                })

        CounterWithDerivedState()
        AndroidView(factory = { CustomTextView(context = context, "Custom View") })
        AndroidView(factory = {
            View.inflate(it, R.layout.compose_interperability, null)
        }, update = {
            //get your views and set click listener
        })
    }
}

@Composable
private fun Counter(viewModel: MainViewModel) {
    val counterState = remember { mutableStateOf(0) }
    println("recomposition inside Counter")
    Button(onClick = { counterState.value = counterState.value + 1 }) {
        println("recomposition inside Counter 2")
        Text(counterState.value.toString())
    }

    //if (counterState.value >= 10) Text("Hurray!")
}

/**
 * https://stefma.medium.com/jetpack-compose-remember-mutablestateof-derivedstateof-and-remembersaveable-explained-270dbaa61b8
 * https://www.valueof.io/blog/jetpack-compose-remember-vs-mutablestateof
 */
@Composable
private fun CounterWithDerivedState() {
    var counterState by remember { mutableStateOf(0) }

    val showHurrayState = remember {
        derivedStateOf { if (counterState > 10) "" else "hello" }
    }
    Log.d("CounterWithDerivedState", "recomposition inside CounterWithDerivedState")

    Button(onClick = {
        counterState += 1
    }) {
        Text("Press me to increase counter: $counterState")
        Log.d("CounterWithDerivedState", "recomposition inside Button: $counterState")
    }

    if (showHurrayState.value.isEmpty()) Text("Hurray!")
}

@SuppressLint("ViewConstructor")
class CustomTextView constructor(
    context: Context, txt: String, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        text = txt
        textSize = 20f
        gravity = Gravity.CENTER
    }

}

/**
 * https://www.valueof.io/blog/jetpack-compose-remember-vs-mutablestateof
 */
@Composable
fun TestStateVariable() {
    val state = remember {
        mutableStateOf("hello")
    }
    var normalValue = "cool"
    var directState = remember {
        ""
    }
    SideEffect() {
        println("#TestStateVariable SideEffect")
    }
    LaunchedEffect(true) {
        println("#TestStateVariable LaunchedEffect")
    }
    println("#TestStateVariable, 1: ${state.value}")
    println("#StateHoisting, 1: ${state.value}")

    //verify state hoisting
    ChildTestStateVariable(onNameChange = {
        normalValue = it
        println("#StateHoisting, inside callback: ${state.value} and: normalValue: $normalValue")
    }, name = normalValue)


    Button(onClick = { state.value = state.value + 1 }) {
        println("#TestStateVariable, 2: ")
        Text("cool: ${state.value}")
    }
    DisposableEffect(Unit) {
        onDispose {
            println("#TestStateVariable, DisposableEffect, onDispose}")
        }
    }
}

// if name variable will be normal object i.e. non state object, recomposition wont trigger
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildTestStateVariable(onNameChange: (String) -> Unit, name: String) {
    println("#StateHoisting, ChildTestStateVariable: name $name")
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello StateHoisting ChildTestStateVariable, $name",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Name") })
    }
}

@Composable
fun PerformLifeCycleEvent(
    lifecycleOwner: LifecycleOwner, onStart: () -> Unit = {}, onPause: () -> Unit = {}
) {
    DisposableEffect(key1 = lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event: Lifecycle.Event -> println("#lifeCycle: changed event : ${event.name}") }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun SnackbarExample() {
    val (snackBarVisibleState, setSnackBarState) = remember {
        mutableStateOf(false)
    }
    if (snackBarVisibleState) {
        /*ShowSnackBar(dismissSnackBar = {
            setSnackBarState(false)
        })*/
        ShowSnackBarExampleTwo("I m snackBar")
    }
    Text(text = "Click me to see snack bar",
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 45.dp)
            .clickable {
                setSnackBarState(true)
            })
}

@Composable
fun ShowSnackBar(dismissSnackBar : () -> Unit) {
    Snackbar(action = {
        Button(onClick = { dismissSnackBar()}) {
            Text(text = "Dismiss snack bar")
        }
    }, modifier = Modifier.padding(8.dp)) {
        Text(text = "My snackbar")
    }
}

//not working.. But above one is working..
@Composable
fun ShowSnackBarExampleTwo(message: String) {
    val scope = rememberCoroutineScope()
    val snackState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(Unit) {
        snackState.showSnackbar(message = message, duration = SnackbarDuration.Short)
    }
    /*scope.launch {
        snackState.showSnackbar(message = message, duration = SnackbarDuration.Short)
    }*/
}

var withoutRememberState = false

/**
 * Stateless: Stateless composable functions do not have any mutable state.
 * They are purely based on the input parameters provided to them.
 *
 * Statefull: Stateful composable functions, on the other hand, can hold and manage mutable state.
 * They use the remember and mutableStateOf functions to store and observe changes in state.
 * When the state of a stateful composable changes, it triggers a recomposition, allowing the UI to reflect the updated state.
 *
 * *******
 * while recomposition if any input parameter gets changed in dependent/child composable then those composable function will also get recomposed
 * no matter those inputs are state variable or not. Only thing is thot - those composable function should use that input parameter.
 * But to trigger recomposition in first composable state variable value should get changed. (so far observation)
 */
@SuppressLint("UnrememberedMutableState")
@Composable
fun StateFullComposable() {
    var state by remember {
        mutableStateOf(false)
    }
    println("#statefull: New State in StateFullComposable: $state")
    Column {
        Text(text = "#statefull: New State in StateFullComposable: $state and withoutRememberState: $withoutRememberState",
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(top = 45.dp)
                .clickable {
                    println("#statefull: I am clicked")
                    state = !state
                    withoutRememberState = !withoutRememberState
                })
        StateLessComposable(state = withoutRememberState)
    }
}

@Composable
fun StateLessComposable(state: Boolean, onClick: () -> Unit = {}) {
    println("#statefull: New State in StateLessComposable: $state")
    Text(text = "#statefull: New State in StateLessComposable: $state",
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 45.dp, bottom = 45.dp)
            .clickable {

            })
}

@OptIn(FlowPreview::class, ExperimentalMaterial3Api::class)
@Composable
fun EditTextTypingListener(viewModel: MainViewModel) {
    val editeTextValue by viewModel.typingTextStateFlow.collectAsState()
    val typingTextFlow = viewModel.typingTextStateFlow
    LaunchedEffect(Unit) {
        typingTextFlow.debounce(3000).collectLatest {
            println("#EditTextTypingListener: inside LaunchedEffect: $it")
        }
    }
    TextField(value = editeTextValue, onValueChange = { newValue ->
        viewModel.typingTextStateFlow.value = newValue
    })
}
var abc  = ""
var localStringValue = compositionLocalOf<String> { error("") }

@Composable
fun LocalComposableFunction(viewModel: MainViewModel) {
    val xyz = "I am local"
    CompositionLocalProvider(localStringValue provides xyz) {
        DescendentComposable()
    }
    //NonDescendentComposable()
}

@Composable
fun DescendentComposable(localValue: String = localStringValue.current) {
    println("#LocalComposableFunction: inside DescendentComposable: localValue: $localValue")
    Text(text = "I am inside DescendentComposable", modifier = Modifier.clickable {
        //localStringValue. = ""
    })
    DescendentComposableTwo()
}

@Composable
fun DescendentComposableTwo(localValue: String = localStringValue.current) {
    println("#LocalComposableFunction: inside DescendentComposableTwo: ")
    Text(text = "I am inside DescendentComposableTwo")
}



@Composable
fun NonDescendentComposable(localValue: String = localStringValue.current) {
    println("#LocalComposableFunction: inside NonDescendentComposable: localValue: $localValue")
}


