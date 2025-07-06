package com.example.inshortsassignment.domain.model

data class Movie(
    val id: Int,
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val isBookmarked: Boolean = false,
    val category: String
)