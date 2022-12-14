package com.example.thecookbook.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.thecookbook.data.access.local.db.RecipeDB
import com.example.thecookbook.data.access.local.models.asDatabaseModel
import com.example.thecookbook.data.access.local.models.asDomainModel
import com.example.thecookbook.data.access.remote.services.FirebaseService
import com.example.thecookbook.data.access.remote.models.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository(private val db: RecipeDB, private val firebaseService: FirebaseService) {

    var recipes: LiveData<List<Recipe>> =
        Transformations.map(db.recipeDao.getRecipes()) { it.asDomainModel() }

    suspend fun refreshRecipes() {

            withContext(Dispatchers.IO) {
                deleteEarlierRecipes()
                fetchNewRecipes()
            }

    }

    private fun deleteEarlierRecipes() {
        db.recipeDao.deleteAllRecipes()
    }

    private suspend fun fetchNewRecipes() {
        val recipesFromRemote = firebaseService.getRecipes()
        db.recipeDao.insertRecipes(recipesFromRemote.asDatabaseModel())
    }
}