package com.dicoding.movieapp.remote

import com.dicoding.movieapp.local.MovieLocalDataSource
import com.dicoding.movieapp.responses.DetailResponse
import com.dicoding.movieapp.responses.FindResponse
import com.dicoding.movieapp.responses.OverviewDetailResponses
import retrofit2.Call
import java.util.concurrent.TimeUnit

class MovieRepository(private val apiService: ApiService, private val localDataSource: MovieLocalDataSource) {

    suspend fun getMostPopularMovies(): List<DetailResponse> {
        val currentTime = System.currentTimeMillis()
        val lastUpdatedTime = localDataSource.getLastUpdatedTime()

        // Check if it has been more than 3 days since the last update
        if (currentTime - lastUpdatedTime > TimeUnit.DAYS.toMillis(3)) {
            // Fetch the list of movie IDs
            val movieIds = apiService.getMostPopularMovies().take(14)

            // Fetch the details for each movie
            val movies = movieIds.map { id ->
                // Extract the movie ID from the string
                val movieId = id.removePrefix("/title/").removeSuffix("/")

                // Fetch the movie details
                apiService.getMovieDetails(movieId)
            }

            // Save the movies to the local database
            localDataSource.insertMovies(movies)

            return movies
        } else {
            // Return the movies from the local database
            return localDataSource.getMovies()
        }
    }
    fun findMovies(query: String): Call<FindResponse> {
        return apiService.findMovies(query)
    }

    suspend fun getOverviewDetails(movieId: String): OverviewDetailResponses {
        return apiService.getOverviewDetails(movieId)
    }
}

