package com.example.inshortsassignment.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inshortsassignment.domain.model.Movie
import com.example.inshortsassignment.domain.usecase.GetMovieDetailsUseCase
import com.example.inshortsassignment.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetailsUseCase,
    private val toggleBookmark: ToggleBookmarkUseCase
) : ViewModel() {

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _movie.value = getMovieDetails.execute(movieId)
        }
    }

    fun onBookmarkToggle() {
        _movie.value?.let { movie ->
            viewModelScope.launch {
                toggleBookmark.execute(movie)
                _movie.value = movie.copy(isBookmarked = !movie.isBookmarked)
            }
        }
    }
}
