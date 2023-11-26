package com.dicoding.movieapp.activities

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dicoding.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        val searchQuery by viewModel.searchQuery.observeAsState("")
                        SearchBar(
                            searchQuery = searchQuery,
                            onSearchQueryChanged = { newValue ->
                                viewModel.searchQuery.value = newValue
                            },
                            onSearchRequested = {
                                // Call the searchMovies function when the search button is pressed
                                viewModel.searchMovies()
                                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
                            }
                        )

                        val isLoading by viewModel.isLoading.observeAsState(false)

                        if (isLoading) {
                            // Show a CircularProgressIndicator in the middle of the screen
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else {
                            // Show the MovieList
                            val movies by viewModel.movies.observeAsState(emptyList())

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Button(
                                    onClick = { viewModel.fetchMostPopularMovies() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .padding(horizontal = 16.dp)
                                ) {
                                    Text("View Top Ten Movies Right Now")
                                }
                                if (movies!!.isEmpty()) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("No movies to display")
                                    }
                                } else {
                                    MovieList(movies!!)
                                }
                            }

                        }
                    }
                    Fab()
                }
            }
        }
        viewModel.fetchMostPopularMovies()
    }
}




