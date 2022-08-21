package com.example.thecookbook.data.access.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.thecookbook.data.access.local.models.DatabaseRecipe

@Dao
interface RecipeDao {
    @Query("select * from DatabaseRecipes")
    fun getRecipes(): LiveData<List<DatabaseRecipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(Recipes: List<DatabaseRecipe>)

    @Query("delete from DatabaseRecipes")
    fun deleteAllRecipes()
}