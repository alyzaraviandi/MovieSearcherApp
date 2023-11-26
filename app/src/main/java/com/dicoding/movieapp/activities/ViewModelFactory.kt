package com.dicoding.movieapp.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.movieapp.remote.MovieRepository

class ViewModelFactory(private val movieRepository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(movieRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
