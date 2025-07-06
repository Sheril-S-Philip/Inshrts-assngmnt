package com.example.inshortsassignment.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.inshortsassignment.data.local.dao.MovieDao
import com.example.inshortsassignment.data.remote.api.TMDBApi
import com.example.inshortsassignment.data.mapper.toDomain
import com.example.inshortsassignment.data.mapper.toEntity
import com.example.inshortsassignment.data.paging.SearchPagingSource
import com.example.inshortsassignment.domain.model.Movie
import com.example.inshortsassignment.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TMDBApi,
    private val dao: MovieDao
) : MovieRepository {

    override suspend fun fetchTrendingMovies(page: Int): List<Movie> {
        val response = api.getTrendingMovies(page)
        val entities = response.results.map { it.toEntity("trending") }

        if (page == 1) {
            dao.clearCategory("trending")
        }

        dao.insertMovies(entities)
        return entities.map { it.toDomain() }
    }


    override suspend fun fetchNowPlayingMovies(page: Int): List<Movie> {
        val response = api.getNowPlayingMovies(page)
        val entities = response.results.map { it.toEntity("now_playing") }

        if (page == 1) {
            dao.clearCategory("now_playing")
        }

        dao.insertMovies(entities)
        return entities.map { it.toDomain() }
    }



    override fun getSearchResultStream(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SearchPagingSource(api, query) }
        ).flow
    }


    override fun getMoviesByCategory(category: String): Flow<List<Movie>> {
        return dao.getMoviesByCategory(category).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getBookmarkedMovies(): Flow<List<Movie>> {
        return dao.getBookmarkedMovies().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getMovieDetails(id: Int): Movie {
        val local = dao.getMovieById(id)
        return local?.toDomain() ?: api.getMovieDetails(id).toEntity("detail").toDomain()
    }

    override suspend fun toggleBookmark(movie: Movie) {
        val updated = movie.copy(isBookmarked = !movie.isBookmarked)
        dao.updateMovie(updated.toEntity())
    }
}

