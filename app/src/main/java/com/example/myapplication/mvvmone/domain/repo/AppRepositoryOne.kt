package com.example.myapplication.mvvmone.domain.repo

import com.example.myapplication.mvvmone.common.ResourceOne
import com.example.myapplication.mvvmone.domain.model.ResultOne
import kotlinx.coroutines.flow.Flow

interface AppRepositoryOne {

    suspend fun getResultList(): Flow<ResourceOne<ResultOne>>

}