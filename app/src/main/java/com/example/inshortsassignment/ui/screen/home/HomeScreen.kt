package com.example.inshortsassignment.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.inshortsassignment.domain.model.Movie
import com.example.inshortsassignment.ui.navigation.Routes
import com.example.inshortsassignment.ui.components.ShimmerMovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val trending by viewModel.trending.collectAsState()
    val nowPlaying by viewModel.nowPlaying.collectAsState()
    val isTrendingLoadingMore by viewModel.isTrendingLoadingMore.collectAsState()
    val isNowPlayingLoadingMore by viewModel.isNowPlayingLoadingMore.collectAsState()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Inshorts Movies") },
                    actions = {
                        TextButton(onClick = { navController.navigate(Routes.BOOKMARKS) }) {
                            Icon(Icons.Default.FavoriteBorder, contentDescription = "Bookmarks")
                            Spacer(Modifier.width(4.dp))
                            Text("Bookmarks")
                        }
                    }
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Search movies...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { navController.navigate(Routes.SEARCH) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search Icon")
                    },
                    enabled = false
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            // Trending Section
            Text("Trending", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
            if (trending.isEmpty()) {
                ShimmerMovieList()
            } else {
                MovieList(
                    movies = trending,
                    onClick = { movieId -> navController.navigate("${Routes.DETAILS}/$movieId") },
                    onLoadMore = { viewModel.loadNextTrendingPage() },
                    isLoadingMore = isTrendingLoadingMore
                )
            }

            // Now Playing Section
            Text("Now Playing", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
            if (nowPlaying.isEmpty()) {
                ShimmerMovieList()
            } else {
                MovieList(
                    movies = nowPlaying,
                    onClick = { movieId -> navController.navigate("${Routes.DETAILS}/$movieId") },
                    onLoadMore = { viewModel.loadNextNowPlayingPage() },
                    isLoadingMore = isNowPlayingLoadingMore
                )
            }
        }
    }
}


@Composable
fun MovieList(
    movies: List<Movie>,
    onClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    isLoadingMore: Boolean = false
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                if (visibleItems.isNotEmpty() && visibleItems.last().index >= movies.size - 3) {
                    onLoadMore()
                }
            }
    }

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie, onClick = onClick)
        }

        if (isLoadingMore) {
            item {
                ShimmerMovieItem()
            }
        }
    }
}


@Composable
fun MovieCard(movie: Movie, onClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .padding(end = 12.dp)
            .clickable { onClick(movie.id) }
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = movie.title ?: "",
            maxLines = 2,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis
        )
    }
}




@Composable
fun ShimmerMovieList() {
    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
        items(8) {
            ShimmerMovieItem()
        }
    }
}
