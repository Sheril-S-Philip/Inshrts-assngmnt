package com.example.inshortsassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.inshortsassignment.ui.navigation.AppNavHost
import com.example.inshortsassignment.ui.theme.InshortsAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InshortsAssignmentTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}
