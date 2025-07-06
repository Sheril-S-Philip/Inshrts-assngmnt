package com.example.inshortsassignment.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inshortsassignment.domain.model.Movie
import com.example.inshortsassignment.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.inshortsassignment.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trendingPageUseCase: GetTrendingMoviesUseCase,
    private val nowPlayingUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    private val _trending = MutableStateFlow<List<Movie>>(emptyList())
    val trending: StateFlow<List<Movie>> = _trending.asStateFlow()

    private val _nowPlaying = nowPlayingUseCase.observe()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val nowPlaying: StateFlow<List<Movie>> = _nowPlaying

    // Trending pagination state
    private var trendingPage = 1
    private var isTrendingLoading = false
    private var isTrendingLastPage = false
    private val _isTrendingLoadingMore = MutableStateFlow(false)
    val isTrendingLoadingMore: StateFlow<Boolean> = _isTrendingLoadingMore

    // Now Playing pagination state
    private var nowPlayingPage = 1
    private var isNowPlayingLoading = false
    private var isNowPlayingLastPage = false
    private val _isNowPlayingLoadingMore = MutableStateFlow(false)
    val isNowPlayingLoadingMore: StateFlow<Boolean> = _isNowPlayingLoadingMore

    init {
        loadNextTrendingPage()
        loadNextNowPlayingPage()
    }

    fun loadNextTrendingPage() {
        if (isTrendingLoading || isTrendingLastPage) return

        isTrendingLoading = true
        _isTrendingLoadingMore.value = true

        viewModelScope.launch {
            val newMovies = trendingPageUseCase.execute(trendingPage)
            if (newMovies.isNotEmpty()) {
                _trending.update { it + newMovies }
                trendingPage++
            } else {
                isTrendingLastPage = true
            }
            isTrendingLoading = false
            _isTrendingLoadingMore.value = false
        }
    }

    fun loadNextNowPlayingPage() {
        if (isNowPlayingLoading || isNowPlayingLastPage) return

        isNowPlayingLoading = true
        _isNowPlayingLoadingMore.value = true

        viewModelScope.launch {
            val newMovies = nowPlayingUseCase.execute(nowPlayingPage)
            if (newMovies.isNotEmpty()) {
                // Append to DB, flow will update UI
                nowPlayingPage++
            } else {
                isNowPlayingLastPage = true
            }
            isNowPlayingLoading = false
            _isNowPlayingLoadingMore.value = false
        }
    }
}

