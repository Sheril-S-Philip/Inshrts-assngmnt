package com.example.inshortsassignment.ui.screen.bookmarks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.inshortsassignment.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    navController: NavController,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    val movies by viewModel.bookmarkedMovies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Bookmarked Movies") })
        }
    ) { padding ->
        if (movies.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No bookmarks yet!")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(movies.size) { index ->
                    val movie = movies[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("${Routes.DETAILS}/${movie.id}") }
                            .padding(vertical = 8.dp)
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
                            Text(movie.title ?: "", style = MaterialTheme.typography.titleMedium)
                            Text("⭐ ${movie.voteAverage}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
