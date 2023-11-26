package com.dicoding.movieapp.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://imdb8.p.rapidapi.com/"
    private const val RAPID_API_KEY = "481031d4eemsh25ac7a0e2f04c8cp1d13ebjsncf2e6cde3649"
    private const val RAPID_API_HOST = "imdb8.p.rapidapi.com"

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", RAPID_API_KEY)
                .addHeader("X-RapidAPI-Host", RAPID_API_HOST)
                .build()
            chain.proceed(request)
        }
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}