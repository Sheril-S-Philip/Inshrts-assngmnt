package com.example.inshortsassignment.data.remote.api

import com.example.inshortsassignment.data.remote.dto.MovieDto
import com.example.inshortsassignment.data.remote.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(@Query("page") page: Int = 1): MovieResponse


    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): MovieResponse


    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String, @Query("page") page: Int): MovieResponse



    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDto

}
