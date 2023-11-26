package com.dicoding.movieapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.dicoding.movieapp.local.DatabaseHelper
import com.dicoding.movieapp.local.MovieLocalDataSource
import com.dicoding.movieapp.remote.ApiClient
import com.dicoding.movieapp.remote.MovieRepository
import com.dicoding.movieapp.ui.theme.MovieAppTheme

class DetailActivity : ComponentActivity() {
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movieId = intent.getStringExtra("MOVIE_ID")?.removePrefix("/title/")
        val movieUrl = intent.getStringExtra("MOVIE_URL")

        val dbHelper = DatabaseHelper(applicationContext)
        val localDataSource = MovieLocalDataSource(dbHelper)
        val movieRepository = MovieRepository(ApiClient.api, localDataSource)
        val viewModelFactory = ViewModelFactory(movieRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

        setContent {
            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val movie by viewModel.movie.observeAsState()
                    val isLoading by viewModel.isLoading.observeAsState(true)

                    if (isLoading) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else {
                        if (movie != null) {
                            DetailScreen(movie = movie!!, imageUrl = movieUrl.orEmpty())
                            FavoriteButton(movie = movie!!, imageUrl = movieUrl.orEmpty(), dbHelper = dbHelper)
                        }
                    }
                }
            }
        }
        // Fetch the movie details when the activity is created
        movieId?.let { viewModel.getMovieDetails(it) }
    }
}




