package com.example.myhiltapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.myhiltapplication.test.TestDiHere
import com.example.myhiltapplication.test.TestDiIndependentHilt
import com.example.myhiltapplication.test.TestDiIndependentTwoHilt
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity2 : ComponentActivity() {

    @Inject
    @Named("one")
    lateinit var testDiHere: TestDiHere
    @Inject
    lateinit var testDiIndependentHilt: TestDiIndependentHilt
    @Inject
    lateinit var testDiIndependentTwoHilt: TestDiIndependentTwoHilt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("testDiHere: $testDiHere and testDiIndependentHilt: $testDiIndependentHilt")
        println("testDiHere: testDiIndependentTwoHilt inside MainActivity2: $testDiIndependentTwoHilt")
    }
}