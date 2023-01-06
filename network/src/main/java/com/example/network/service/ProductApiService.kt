package com.example.network.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class ProductApiService {

    object ProductApi {
        private val BASE_URL = "http://10.0.0.109:8080"

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()

        val productApiService: ProductService by lazy { retrofit.create(ProductService::class.java) }
    }
}