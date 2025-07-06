package com.example.inshortsassignment.domain.usecase

import com.example.inshortsassignment.domain.repository.MovieRepository

class GetBookmarkedMoviesUseCase(
    private val repository: MovieRepository
) {
    fun observe() = repository.getBookmarkedMovies()
}
