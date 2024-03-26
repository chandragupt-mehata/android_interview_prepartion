package com.example.myhiltapplication.test

import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class TestDiIndependentHilt @Inject constructor() {

    @Inject
    lateinit var testDiIndependentTwoHilt: TestDiIndependentTwoHilt

    fun printIt() {
        println("testDiHere: testDiIndependentTwoHilt inside testDiIndependentHilt: $testDiIndependentTwoHilt")
    }

}