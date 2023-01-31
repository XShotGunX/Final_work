package com.example.finalwork.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostRetrofit {
    fun getRetrofit() =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApi::class.java)
}