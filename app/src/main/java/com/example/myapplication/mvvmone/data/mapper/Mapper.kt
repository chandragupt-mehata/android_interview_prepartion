package com.example.myapplication.mvvmone.data.mapper

interface Mapper<F, T> {
    fun mapTo(f: F): T
}