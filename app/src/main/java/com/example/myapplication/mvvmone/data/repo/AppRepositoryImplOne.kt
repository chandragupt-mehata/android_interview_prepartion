package com.example.myapplication.mvvmone.data.repo

import com.example.myapplication.mvvmone.common.ResourceOne
import com.example.myapplication.mvvmone.common.RetrofitHelperOne
import com.example.myapplication.mvvmone.data.mapper.ResultMapperOne
import com.example.myapplication.mvvmone.data.network.ApIInterfaceOne
import com.example.myapplication.mvvmone.domain.model.ResultOne
import com.example.myapplication.mvvmone.domain.repo.AppRepositoryOne
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.util.ArrayList

class AppRepositoryImplOne: AppRepositoryOne {

    private var apIInterfaceOne: ApIInterfaceOne = RetrofitHelperOne.getRetrofitInstance()
    private val mapperOne = ResultMapperOne()

    override suspend fun getResultList(): Flow<ResourceOne<ResultOne>> {
       return flow {
           val list = ArrayList<String>()
           emit(ResourceOne.Loading(true))
           try {
               val result = apIInterfaceOne.getResultList()
               emit(ResourceOne.Success(result = mapperOne.mapTo(result)))
           } catch (exception: Exception) {
               emit(ResourceOne.Error(error = "${exception.message}"))
           }
       }
    }

}