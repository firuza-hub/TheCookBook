package com.example.thecookbook.data.access.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.thecookbook.data.access.local.models.DatabaseRecipe

@Dao
interface RecipeDao {
    @Query("select * from DatabaseRecipes")
    fun getRecipes(): LiveData<List<DatabaseRecipe>>


    @Query("select * from DatabaseRecipes where DatabaseRecipes.name LIKE :name ")
    fun getRecipesByName(name: String): LiveData<List<DatabaseRecipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(Recipes: List<DatabaseRecipe>)

    @Query("delete from DatabaseRecipes")
    fun deleteAllRecipes()
}