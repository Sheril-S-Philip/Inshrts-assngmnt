package com.example.inshortsassignment.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.inshortsassignment.data.remote.api.TMDBApi
import com.example.inshortsassignment.data.mapper.toDomain
import com.example.inshortsassignment.domain.model.Movie

class SearchPagingSource(
    private val api: TMDBApi,
    private val query: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.searchMovies(query = query, page = page)
            val movies = response.results.map { it.toDomain() }

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
