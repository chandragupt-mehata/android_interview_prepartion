package com.example.myapplication.domain.repo

import com.example.myapplication.common.Resource
import com.example.myapplication.domain.model.DomainModel
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun getPageResult(): Flow<Resource<List<DomainModel>>>

}