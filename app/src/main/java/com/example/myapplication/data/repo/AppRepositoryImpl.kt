package com.example.myapplication.data.repo

import com.example.myapplication.common.Resource
import com.example.myapplication.common.RetrofitHelper
import com.example.myapplication.data.mapper.DomainMapper
import com.example.myapplication.domain.model.DomainModel
import com.example.myapplication.domain.repo.AppRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.lang.RuntimeException
import kotlin.Exception

class AppRepositoryImpl: AppRepository {

    override suspend fun getPageResult(): Flow<Resource<List<DomainModel>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            try {
                val data = RetrofitHelper.getApiInterface().getResult("", 1)
                val mapper = DomainMapper()
                emit(Resource.Success(mapper.fromToList(data)))
            } catch (exceptin: Exception) {
                emit(Resource.Failure(errorMsg = exceptin.message))
            } finally {
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("inside performParallelTask at top caught exception: $throwable")
    }

    /*suspend fun performParallelTask() {
        supervisorScope {
            val defered1 = launch {
               repeat(10) {
                   delay(1000)
                   println("inside performParallelTask one: $it")
               }
            }
            val defered2 = launch(exceptionHandler) {
                repeat(10) {
                    delay(1000)
                    println("inside performParallelTask two: $it")
                    throw RuntimeException("throw exception")
                }
            }
        }
    }*/

    suspend fun performParallelTask() {
        supervisorScope {
            try {
                val user = async(Dispatchers.IO) {
                    repeat(10) {
                        delay(1000)
                        println("inside performParallelTask one: $it")
                    }
                }
                val weather = async(Dispatchers.IO) {
                    repeat(10) {
                        delay(1000)
                        println("inside performParallelTask two: $it")
                        throw RuntimeException("throw exception")
                    }
                }
                user.await()
                weather.await()
            } catch (e: Exception) {
                println("inside performParallelTask at below exception: $e")
            }
        }
    }
}