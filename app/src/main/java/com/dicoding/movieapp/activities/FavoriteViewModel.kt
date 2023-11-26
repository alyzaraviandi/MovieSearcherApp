package com.dicoding.movieapp.activities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.movieapp.local.DatabaseHelper
import com.dicoding.movieapp.local.MovieLocalDataSource
import com.dicoding.movieapp.responses.OverviewDetailResponses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)
    private val localDataSource = MovieLocalDataSource(dbHelper)
    private val _favoriteMovies = MutableLiveData<List<OverviewDetailResponses>>()
    val favoriteMovies: LiveData<List<OverviewDetailResponses>> get() = _favoriteMovies

    init {
        loadFavoriteMovies()
    }

    fun loadFavoriteMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val movies = localDataSource.getFavoriteMovies()
                _favoriteMovies.postValue(movies)
            }
        }
    }
}

