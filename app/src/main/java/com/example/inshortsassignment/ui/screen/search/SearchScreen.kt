package com.example.inshortsassignment.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import coil.compose.AsyncImage
import com.example.inshortsassignment.ui.navigation.Routes
import com.example.inshortsassignment.ui.components.ShimmerSearchItem
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.inshortsassignment.ui.utils.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    val pagedResults = viewModel.pagedResults.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Search Movies") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    viewModel.onQueryChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Type movie name...") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                when {
                    pagedResults.loadState.refresh is LoadState.Loading -> {
                        items(6) {
                            ShimmerSearchItem()
                        }
                    }

                    pagedResults.loadState.refresh is LoadState.Error -> {
                        item {
                            val e = pagedResults.loadState.refresh as LoadState.Error
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Error: ${e.error.message}", color = MaterialTheme.colorScheme.error)
                                Spacer(Modifier.height(8.dp))
                                Button(onClick = { pagedResults.retry() }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }

                    else -> {
                        items(pagedResults) { movie ->
                            movie?.let {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navController.navigate("${Routes.DETAILS}/${movie.id}")
                                        }
                                        .padding(8.dp)
                                ) {
                                    AsyncImage(
                                        model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                                        contentDescription = movie.title,
                                        modifier = Modifier
                                            .width(80.dp)
                                            .height(120.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            movie.title ?: "",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            "⭐ ${movie.voteAverage}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }

                        // Loader at the end for next pages
                        if (pagedResults.loadState.append is LoadState.Loading) {
                            items(3) {
                                ShimmerSearchItem()
                            }
                        }
                    }
                }
            }
        }
    }
}
