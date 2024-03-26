package com.example.myapplication.mvvmone.data.mapper

import com.example.myapplication.mvvmone.data.model.ResultDtoOne
import com.example.myapplication.mvvmone.domain.model.ResultOne

class ResultMapperOne: Mapper<ResultDtoOne, ResultOne> {

    override fun mapTo(f: ResultDtoOne): ResultOne {
        return ResultOne(f.status, f.message.affenpinscher.first().toString())
    }
}