package com.example.inshortsassignment.domain.usecase

import androidx.paging.PagingData
import com.example.inshortsassignment.domain.model.Movie
import com.example.inshortsassignment.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase(
    private val repository: MovieRepository
) {
    fun search(query: String): Flow<PagingData<Movie>> {
        return repository.getSearchResultStream(query)
    }
}

