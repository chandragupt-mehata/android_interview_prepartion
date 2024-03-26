package com.example.myapplication.data.mapper

interface Mapper<F, T> {

    fun from(from: F): T
    fun fromToList(from: F): List<T>

}