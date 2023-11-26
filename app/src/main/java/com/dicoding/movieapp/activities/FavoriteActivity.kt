package com.dicoding.movieapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.dicoding.movieapp.ui.theme.MovieAppTheme

class FavoriteActivity : ComponentActivity() {
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeFavoriteMovies()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavoriteMovies()
    }

    private fun observeFavoriteMovies() {
        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Observe the LiveData of favorite movies
                    val movies = viewModel.favoriteMovies.observeAsState(initial = emptyList())

                    // Show the FavoriteMovieList
                    FavoriteMovieList(movies = movies.value) { movieId, imageUrl ->
                        // Navigate to DetailActivity when a movie item is clicked
                        val intent = Intent(this, DetailActivity::class.java).apply {
                            putExtra("MOVIE_ID", movieId)
                            putExtra("MOVIE_URL", imageUrl)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }
}





