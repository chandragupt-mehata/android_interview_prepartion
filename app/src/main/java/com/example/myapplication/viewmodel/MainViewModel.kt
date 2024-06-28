package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.multithreading.TaskRunnableFutureWait
import com.example.myapplication.coroutine.await
import com.example.myapplication.data.repo.AppRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.system.measureTimeMillis

/**
 * We get the view model object from view model provider object. We pass view model store owner reference as activity/fragment to view model provider.
 * view model store owner is an interface which is having method to retrieve view model store object and internally view model store object holds all related
 * view model object as a key/value pair.
 * So for us view model store object is vital. In interface  val viewModelStore: ViewModelStore method which is imlemented by component activity and it's
 * having ensure view model () method. Internally based upon view model'name it creates view model object and store it into view model store object.
 *
 * 1. on orientation change onRetainNonConfigurationInstance/ComponentActivity will get called and it saves view model store object which it get
 * from ViewModelStore viewModelStore = mViewModelStore; to NonConfigurationInstances
 * NonConfigurationInstances nci = new NonConfigurationInstances();
 *         nci.custom = custom;
 *         nci.viewModelStore = viewModelStore;
 * And it's static NonConfigurationInstances class.
 *
 * 2. onStateChanged will get called for onDestroy but since it's rotation change it wont create view model store object.
 * 3. Since orientation has been changed so all object will become reset i.e. view model store object will be null. But NonConfigurationInstances
 * instance would be there because that's static one. Here ensureViewModelStore will get called which will check NonConfigurationInstances static
 * object in case of null view model store object. And it will find it then it will reset view model store object with this NonConfigurationInstances's
 * store object.
 *
 * *************
 * https://medium.com/@milindamrutkar/the-internals-of-viewmodel-and-surviving-configuration-changes-d62f0c871c30#:~:text='ViewModel'%20survives%20configuration%20changes%20due,the%20new%20'Activity'%20instance.
 * When we create a ‘ViewModel’ instance, we usually use ‘ViewModelProvider’.This ‘ViewModelProvider’ uses a ‘ViewModelStore’ to retain ‘ViewModel’ instances.
 * val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
 * this is ViewModelStoreOwnerInterface's implementation -> ViewmodelStoreOwner(one method getViewModel) -> ComponentActivity
 *
 * Once we have instance of viewmodel provider, Now let’s get our viewmodel object. As we can see, we simply just called the get(arg) method with
 * the desired viewmodel class reference and our viewmodel object was created. This method gets the canonical name of the viewmodel class, creates
 * a key by appending DEFAULT_KEY then it calls the another get function which takes the key and viewmodel class reference. This method checks
 * the viewmodel instance in the viewmodel store first. If the viewmodel instance is there in the viewmodelstore then it simply returns that instance.
 * If viewmodel instance is not there then it uses the factory to create the new instance and saves that instance in viewmodel store and then it
 * return the viewmodel instance.
 * So as we saw viewmodelprovider internally uses viewmodelstore container to get the view model object. And view model store object will get retrieved
 * through lifecycle component who implements that interface.
 *
 * In short --
 * In the ‘get’ method, ‘ViewModelProvider’ first tries to get an existing ‘ViewModel’ from the ‘ViewModelStore’ using the class name as the key.
 * If a ‘ViewModel’ with this key doesn’t exist, it creates a new one using the provided ‘Factory’, then stores it in the ‘ViewModelStore’.
 *
 *
 * Now configuration change management -
 * The ‘ViewModelStore’ is a simple container that holds ‘ViewModels’. It’s associated with the lifecycle of an ‘Activity’ or ‘Fragment’ and is retained
 * during configuration changes.
 *
 * In details: (Now configuration change management)
 * 1. ViewModelProvider(this).get(MainViewModel::class.java)
 * public constructor(
 *         owner: ViewModelStoreOwner
 *     ) : this(owner.viewModelStore, defaultFactory(owner), defaultCreationExtras(owner))
 *
 * owner.viewModelStore -> public ViewModelStore getViewModelStore() {
 *         if (getApplication() == null) {
 *             throw new IllegalStateException("Your activity is not yet attached to the "
 *                     + "Application instance. You can't request ViewModel before onCreate call.");
 *         }
 *         ensureViewModelStore();
 *         return mViewModelStore;
 *     }
 * this interface -> ViewModelStoreOwener which will have method to provide viewmodelstore object
 * get method of that interface -> calls ensureviewModelStore method over component activity all logic related to nci.
 *
 * 2. Then get method of viewModelprovider. Once it will receive viewModelstore object it will fetch viewmodel object from viewmodelstore map properties. if not
 * found then it will create new and save there.
 *
 * *****************************
 * https://medium.com/@paritasampa95/difference-among-viewmodel-androidviewmodel-and-hiltviewmodel-8321719c1f4c
 *
 *
 *
 *
 * ***********
 * sunflower app
 * https://github.com/android/sunflower/blob/master/app/src/main/java/com/google/samples/apps/sunflower/data/GardenPlantingRepository.kt
 *
 * ***********************
 * MVVM
 * Model-View-ViewModel (MVVM) is a software architectural pattern commonly used in application development to separate the concerns of the
 * user interface (UI) from the business logic and data. It facilitates a clean separation of concerns, making the
 * code more maintainable and testable. Here’s a breakdown of the three main components:
 *
 * Model:
 * Represents the data and business logic of the application.
 * Encapsulates the data and defines the business rules for how the data can be changed and manipulated.
 * Typically consists of data classes, business logic, and data access components (like repositories or data sources).
 * View:
 * Represents the UI of the application.
 * Displays the data and sends user actions to the ViewModel.
 * Should contain minimal logic, mainly for presentation purposes.
 * In Android development, this could be Activities, Fragments, or XML layouts.
 * ViewModel:
 * Acts as a mediator between the View and the Model.
 * Handles the presentation logic and prepares data for the View.
 * Keeps track of the UI state and exposes it to the View.
 * Listens to changes in the Model and updates the View accordingly.
 * In Android, ViewModels are lifecycle-aware, which means they survive configuration changes like screen rotations.
 *
 * Combining MVVM with Clean Architecture involves structuring the code to include the principles of both patterns. This combination leverages the
 * strengths of MVVM for UI-related concerns and Clean Architecture for a broader separation of concerns across the entire application.
 * If we keep on putting logic inside view model it will be blasted at one time. So clean architecture makes view model as light weight by introducing use
 * case along with other classes in domain layer.
 *
 * Layered Approach:
 *
 * Presentation Layer:
 * View: Activities, Fragments.
 * ViewModel: Fetches data from Use Cases.
 *
 * Domain Layer:
 * Entities: Core business models.
 * Use Cases: Business logic.
 *
 * Data Layer:
 * Repositories: Data access abstraction.
 * Data Sources: Actual implementations (e.g., network, database).
 */
class MainViewModel constructor(private val savedStateHandle: SavedStateHandle): ViewModel() {

    val appRepository = AppRepositoryImpl()
    var count = 0
    var state = mutableStateOf("state")
    var stateFlow = MutableStateFlow("mutable state flow")
    var stateFlowNonMutable: StateFlow<String> = stateFlow
    var sharedFlow = MutableSharedFlow<String>()
    var liveData = MutableLiveData("live data")
    val executors = Executors.newFixedThreadPool(1)
    var _channel = Channel<String>()
    val channel = _channel.receiveAsFlow()

    //verify suspend function should call internally inbuilt kotlin suspend
    /**
     * https://hmkcode.com/android/android-network-connection-httpurlconnection-coroutine/
     */
    fun getResult() {
        //state.value = ""
        viewModelScope.launch(executors.asCoroutineDispatcher()) {
            launch {
                println("#getResult before network operation: ${Thread.currentThread().name}")
                /*RetrofitHelper.getApiInterface().getPlainResult("", 1).enqueue(object :
                    Callback<ResultDto> {
                    // Handle onResponse and onFailure as before
                    override fun onResponse(call: Call<ResultDto>, response: Response<ResultDto>) {
                        println("#getResult onResponse: ${Thread.currentThread().name}")
                    }

                    override fun onFailure(call: Call<ResultDto>, t: Throwable) {
                        println("#getResult onFailure: ${Thread.currentThread().name}")
                    }
                })*/
                //makeHttpRequest("https://example.com")
                val time = measureTimeMillis {
                    makeHttpRequest("https://google.com")
                }
                println("#getResult after network operation: ${Thread.currentThread().name} and time: $time")
            }
            launch {
                repeat(1000) {
                    println("#getResult in between of network operation counter: $it: on thread ${Thread.currentThread().name}")
                    delay(1000)
                }
            }
        }
    }

    private suspend fun makeCustomsuspendedHttpCall(url: String) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).await()  // Use the custom suspend function
        //val responseBody = response.body!!.string()
        response.close()
    }

    /**
     * its blocking call
     * The code I provided is still blocking the underlying thread during the synchronous HTTP call.
     * The use of suspendCoroutine does not magically transform a blocking operation into a
     * non-blocking one.
     *
     * If you want true non-blocking behavior, especially for network operations, consider using asynchronous libraries designed for coroutine support,
     * such as ktor for HTTP requests. Alternatively, you might explore other approaches like using async with await or reactive programming libraries.
     */
    suspend fun fetchDataUsingCustomSuspendable(url: String): String {
        // Use suspendCoroutine to create a custom suspending function
        return suspendCoroutine { continuation: Continuation<String> ->
            // Synchronous HTTP call
            val connection = URL(url).openConnection() as HttpURLConnection
            try {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line).append('\n')
                }
                response.toString()
            } finally {
                connection.disconnect()
            }
            println("#getResult after network operation: ${Thread.currentThread().name} and time:")

            // Resume the continuation with the result
            continuation.resume("response")
        }
    }


    private suspend fun httpGet(myURL: String?): String {

        val inputStream: InputStream
        val result:String

        // create URL
        val url:URL = URL(myURL)

        // create HttpURLConnection
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

        // make GET request to the given URL
        conn.connect()

        // receive response as inputStream
        inputStream = conn.inputStream

        // convert inputstream to string
        /*if(inputStream != null)
            result = convertInputStreamToString(inputStream)
        else
            result = "Did not work!"*/

        return inputStream.toString()
    }

    // need to use same thread to check behaviour //executors.asCoroutineDispatcher()
    suspend fun makeHttpRequest(url: String): String = withContext(executors.asCoroutineDispatcher()) {
        val connection = URL(url).openConnection() as HttpURLConnection
        try {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line).append('\n')
            }
            response.toString()
        } finally {
            connection.disconnect()
        }
    }

    fun increaseCounter() {
        println("counter is ${++count}")
    }

    /**
     * https://manuelvivo.dev/coroutines-addrepeatingjob
     */
    fun backgroundUpdate(lifecycleScope: LifecycleCoroutineScope) {
        viewModelScope.launch(Dispatchers.IO) {
            repeat(20) {
                delay(1000)
                println("#testStateFlowLiveDataInBackground hello i am in background : $it")
                stateFlow.emit(it.toString())
                liveData.postValue(it.toString())
                sharedFlow.emit(it.toString())
                state.value = it.toString()
            }
            /*stateFlow.emit("it.toString()")
            liveData.postValue("it.toString()")
            sharedFlow.emit("it.toString()")
            _channel.send("it.toString()")*/
        }
    }

    fun yourFlow(coroutineScope: CoroutineScope) {
        viewModelScope.launch(Dispatchers.IO) {
            repeat(20) {
                delay(1000)
                println("new test simulation : $it: $isActive")
                stateFlow.emit(it.toString())
            }
        }
    }


    fun testWithContext() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                println("inside testWithContext entered")
                delay(10000)
                println("inside testWithContext exit")
            }
        }
    }

    fun testWithViewModelScope(lifecycleScope: LifecycleCoroutineScope) {
        runBlocking {
            viewModelScope.launch {
                launch {
                    delay(100)
                    println("#viewModelScope inside viewmode scope 1")
                }
                launch {
                    delay(100)
                    println("#viewModelScope inside viewmode scope 2")
                }
            }
            println("#viewModelScope after view model scope")
        }
        println("#viewModelScope after run blocking")
    }

    fun testParallelDecomposition() {
        viewModelScope.launch {
            appRepository.performParallelTask()
        }
    }

    /**
     * blocks main thread because of get() method
     */
    fun verifyFutureWait() {
        // Number of tasks
        val numTasks = 100

        // Thread pool size
        val poolSize = 10

        // Create a thread pool with a fixed number of threads
        val executor = Executors.newFixedThreadPool(poolSize)

        // Submit tasks to the executor
        println("#verifyFutureWait: start")
        val result = executor.submit(TaskRunnableFutureWait(25))
        println("#verifyFutureWait: end of result: ${result.get()}") // future.get() blocks the thread calling it until the task is completed and the
        // result is available.

        // Shutdown the executor when all tasks are completed
        executor.shutdown()
    }

    suspend fun verifyAsyncWait() {
        val result = viewModelScope.async(Dispatchers.Main) {
            println("#verifyAsyncWait: start")
            delay(10000)
            "got result"
        }.await()
        println("#verifyAsyncWait: end of result: $result")
    }

    fun storeSavedInstanceFlow() {
        savedStateHandle["test"] = "test value"
        println("#savedInstance: storeSavedInstanceFlow saved")
    }

    fun retrieveSavedInstanceFlow() {
        val result = savedStateHandle.get<String>("test")
        println("#savedInstance: retrieveSavedInstanceFlow, saved value is: $result")
    }

}