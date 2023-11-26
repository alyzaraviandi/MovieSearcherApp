package com.dicoding.movieapp.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.movieapp.remote.MovieRepository
import com.dicoding.movieapp.responses.OverviewDetailResponses
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movie = MutableLiveData<OverviewDetailResponses>()
    val movie: LiveData<OverviewDetailResponses> get() = _movie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val movie = repository.getOverviewDetails(movieId)
            _movie.value = movie
            _isLoading.value = false
        }
    }
}



