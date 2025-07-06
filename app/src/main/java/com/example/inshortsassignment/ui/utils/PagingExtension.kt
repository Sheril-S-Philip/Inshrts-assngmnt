package com.example.inshortsassignment.ui.utils

// PagingExtensions.kt
import androidx.compose.foundation.lazy.LazyListScope
import androidx.paging.compose.LazyPagingItems
import androidx.compose.runtime.Composable

fun <T : Any> LazyListScope.items(
    items: LazyPagingItems<T>,
    itemContent: @Composable (T?) -> Unit
) {
    items(count = items.itemCount) { index ->
        itemContent(items[index])
    }
}
