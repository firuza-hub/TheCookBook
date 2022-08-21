package com.example.thecookbook.data.access.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thecookbook.data.access.local.dao.RecipeDao
import com.example.thecookbook.data.access.local.models.DatabaseRecipe

@Database(entities = [DatabaseRecipe::class], version = 1)
abstract class RecipeDB : RoomDatabase() {
    abstract val recipeDao: RecipeDao
}

private lateinit var INSTANCE: RecipeDB

fun getDatabase(context: Context): RecipeDB {
    synchronized(RecipeDB::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                RecipeDB::class.java,
                "recipes"
            )
                .build()
        }
    }
    return INSTANCE
}
