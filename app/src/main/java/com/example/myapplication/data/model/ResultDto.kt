package com.example.myapplication.data.model

data class ResultDto(
    val `data`: List<Data>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)