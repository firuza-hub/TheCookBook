package com.example.thecookbook.data.access

import com.example.thecookbook.data.models.Result
import com.example.thecookbook.data.models.RecipeDTO

class RecipesRepository {
    val mockItems = listOf<RecipeDTO>(RecipeDTO(123, "test recipe from remote"))
    fun getRecipes(): Result<List<RecipeDTO>> {
        return  mockItems.let { Result.Success(it) }

    }
}