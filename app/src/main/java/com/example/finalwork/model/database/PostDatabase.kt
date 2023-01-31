package com.example.finalwork.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 1)
abstract class PostDatabase: RoomDatabase() {
    abstract fun getDao(): PostDao
}