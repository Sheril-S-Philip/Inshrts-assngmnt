package com.example.inshortsassignment.ui.screen.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inshortsassignment.domain.model.Movie
import com.example.inshortsassignment.domain.usecase.GetBookmarkedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    getBookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase
) : ViewModel() {

    val bookmarkedMovies: StateFlow<List<Movie>> =
        getBookmarkedMoviesUseCase.observe()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
