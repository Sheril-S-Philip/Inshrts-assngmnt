package com.example.inshortsassignment.domain.usecase

import com.example.inshortsassignment.domain.repository.MovieRepository

class GetMovieDetailsUseCase(
    private val repository: MovieRepository
) {
    suspend fun execute(movieId: Int) = repository.getMovieDetails(movieId)
}
