package com.example.inshortsassignment.domain.usecase

import com.example.inshortsassignment.domain.model.Movie
import com.example.inshortsassignment.domain.repository.MovieRepository

class GetTrendingMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend fun execute(page: Int): List<Movie> {
        return repository.fetchTrendingMovies(page)
    }

    fun observe() = repository.getMoviesByCategory("trending")
}
