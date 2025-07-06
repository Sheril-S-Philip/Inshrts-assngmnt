package com.example.inshortsassignment.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.inshortsassignment.domain.model.Movie
import com.example.inshortsassignment.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val pagedResults: Flow<PagingData<Movie>> = queryFlow
        .debounce(400)
        .distinctUntilChanged()
        .filter { it.length > 1 }
        .flatMapLatest { query ->
            searchMoviesUseCase.search(query)
        }
        .cachedIn(viewModelScope)

    fun onQueryChange(query: String) {
        queryFlow.value = query
    }
}


