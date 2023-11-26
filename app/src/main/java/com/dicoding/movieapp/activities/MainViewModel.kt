package com.dicoding.movieapp.activities

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.movieapp.remote.ApiClient
import com.dicoding.movieapp.local.DatabaseHelper
import com.dicoding.movieapp.responses.FindResponse
import com.dicoding.movieapp.local.MovieLocalDataSource
import com.dicoding.movieapp.remote.MovieRepository
import com.dicoding.movieapp.responses.ResultsItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val applicationContext = application.applicationContext

    private val _movies = MutableLiveData<List<ResultsItem>?>()
    val movies: MutableLiveData<List<ResultsItem>?> get() = _movies

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    // Initialize the MovieRepository with the local data source
    private val dbHelper = DatabaseHelper(applicationContext)
    private val localDataSource = MovieLocalDataSource(dbHelper)
    private val movieRepository = MovieRepository(ApiClient.api, localDataSource)

    var searchQuery = MutableLiveData("")

    fun searchMovies() {
        val query = searchQuery.value
        if (!query.isNullOrEmpty()) {
            findMovies(query)
        }
    }

    fun fetchMostPopularMovies() {
        // Fetch the most popular movies when the button is clicked
        viewModelScope.launch {
            _isLoading.value = true
            val popularMovies = movieRepository.getMostPopularMovies()
            if (popularMovies.isNotEmpty()) {
                _movies.value = popularMovies.map { movie ->
                    ResultsItem(
                        id = movie.id,
                        image = movie.image,
                        title = movie.title,
                        year = movie.year
                    )
                }
            } else {
                showToast("No movies found.")
            }
            _isLoading.value = false
        }
    }

    fun findMovies(query: String) {
        _isLoading.value = true
        movieRepository.findMovies(query).enqueue(object : Callback<FindResponse> {
            override fun onResponse(call: Call<FindResponse>, response: Response<FindResponse>) {
                if (response.isSuccessful) {
                    val filteredResults = response.body()?.results?.filterNotNull()?.filter { movie ->
                        // Filter out items that don't have a release year
                        movie.year != null
                    }
                    // Check if filteredResults is not null and not empty before assigning it to movies
                    if (!filteredResults.isNullOrEmpty()) {
                        movies.value = filteredResults
                        Log.d("API_RESPONSE", "Received ${filteredResults.size} movies")
                    } else {
                        showToast("No movies found.")
                    }
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<FindResponse>, t: Throwable) {
                // Log the error for debugging purposes
                Log.e("API_ERROR", "Failed to fetch movies: ${t.message}")

                // Show an error message to the user
                showToast("Failed to fetch movies. Please try again.")

                _isLoading.value = false
            }
        })
    }


    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}






