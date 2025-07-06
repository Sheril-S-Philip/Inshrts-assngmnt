package com.example.inshortsassignment.domain.usecase

import com.example.inshortsassignment.domain.model.Movie
import com.example.inshortsassignment.domain.repository.MovieRepository

class GetNowPlayingMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend fun execute(page: Int): List<Movie> {
        return repository.fetchNowPlayingMovies(page)
    }

    fun observe() = repository.getMoviesByCategory("now_playing")
}
