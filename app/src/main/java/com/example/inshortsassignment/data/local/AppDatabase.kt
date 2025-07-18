package com.example.inshortsassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inshortsassignment.data.local.dao.MovieDao
import com.example.inshortsassignment.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
