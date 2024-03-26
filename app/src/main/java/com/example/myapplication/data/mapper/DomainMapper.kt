package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.ResultDto
import com.example.myapplication.domain.model.DomainModel

class DomainMapper: Mapper<ResultDto, DomainModel> {

    override fun from(from: ResultDto): DomainModel {
        return DomainModel("", "", "", "")
    }

    override fun fromToList(from: ResultDto): List<DomainModel> {
        return from.data.map {
            DomainModel(it.title, it.url, it.author, it.story_url)
        }.toList()
    }
}