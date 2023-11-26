package com.dicoding.movieapp.remote

import com.dicoding.movieapp.responses.DetailResponse
import com.dicoding.movieapp.responses.FindResponse
import com.dicoding.movieapp.responses.OverviewDetailResponses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("title/find")
    fun findMovies(@Query("q") query: String): Call<FindResponse>

    @GET("title/get-most-popular-movies")
    suspend fun getMostPopularMovies(): List<String>

    @GET("title/get-details")
    suspend fun getMovieDetails(@Query("tconst") movieId: String): DetailResponse

    @GET("title/get-overview-details")
    suspend fun getOverviewDetails(@Query("tconst") movieId: String): OverviewDetailResponses
}