package com.example.inshortsassignment.domain.repository

import androidx.paging.PagingData
import com.example.inshortsassignment.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchTrendingMovies(page: Int): List<Movie>
    suspend fun fetchNowPlayingMovies(page: Int): List<Movie>
    fun getSearchResultStream(query: String): Flow<PagingData<Movie>>

    fun getMoviesByCategory(category: String): Flow<List<Movie>>
    suspend fun getMovieDetails(id: Int): Movie
    suspend fun toggleBookmark(movie: Movie)
    fun getBookmarkedMovies(): Flow<List<Movie>>
}
