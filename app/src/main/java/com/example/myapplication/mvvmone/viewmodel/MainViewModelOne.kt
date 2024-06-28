package com.example.myapplication.mvvmone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.mvvmone.common.ResourceOne
import com.example.myapplication.mvvmone.data.repo.AppRepositoryImplOne
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModelOne: ViewModel() {

    fun getResult() {
        viewModelScope.launch {
            val repositoryOne = AppRepositoryImplOne()
            repositoryOne.getResultList().collectLatest {
                when (it) {
                    is ResourceOne.Success -> {

                    }
                    is ResourceOne.Error -> {

                    }
                    is ResourceOne.Loading -> {

                    }

                    else -> {}
                }
            }
        }
    }

}