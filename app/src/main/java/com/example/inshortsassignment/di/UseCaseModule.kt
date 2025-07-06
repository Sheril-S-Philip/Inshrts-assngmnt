package com.example.inshortsassignment.di

import com.example.inshortsassignment.domain.repository.MovieRepository
import com.example.inshortsassignment.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetTrendingMoviesUseCase(repository: MovieRepository): GetTrendingMoviesUseCase {
        return GetTrendingMoviesUseCase(repository)
    }

    @Provides
    fun provideGetNowPlayingMoviesUseCase(repository: MovieRepository): GetNowPlayingMoviesUseCase {
        return GetNowPlayingMoviesUseCase(repository)
    }

    @Provides
    fun provideSearchMoviesUseCase(repository: MovieRepository): SearchMoviesUseCase {
        return SearchMoviesUseCase(repository)
    }

    @Provides
    fun provideGetMovieDetailsUseCase(repository: MovieRepository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(repository)
    }

    @Provides
    fun provideToggleBookmarkUseCase(repository: MovieRepository): ToggleBookmarkUseCase {
        return ToggleBookmarkUseCase(repository)
    }

    @Provides
    fun provideGetBookmarkedMoviesUseCase(repository: MovieRepository): GetBookmarkedMoviesUseCase {
        return GetBookmarkedMoviesUseCase(repository)
    }
}
