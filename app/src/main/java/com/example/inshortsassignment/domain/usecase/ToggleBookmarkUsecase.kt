package com.example.inshortsassignment.domain.usecase

import com.example.inshortsassignment.domain.model.Movie
import com.example.inshortsassignment.domain.repository.MovieRepository

class ToggleBookmarkUseCase(
    private val repository: MovieRepository
) {
    suspend fun execute(movie: Movie) = repository.toggleBookmark(movie)
}
