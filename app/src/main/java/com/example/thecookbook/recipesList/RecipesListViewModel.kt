package com.example.thecookbook.recipesList

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.thecookbook.base.BaseViewModel
import com.example.thecookbook.data.RecipeRepository
import com.example.thecookbook.data.access.local.db.getDatabase
import com.example.thecookbook.data.access.remote.services.FirebaseService
import com.example.thecookbook.data.models.RecipeDataItem
import kotlinx.coroutines.runBlocking

class RecipesListViewModel(application: Application) : BaseViewModel(application) {
    val recipes = MutableLiveData<List<RecipeDataItem>>()
    private val firebaseService = FirebaseService()
    private val db = getDatabase(application.applicationContext)
    private val repo = RecipeRepository(db, firebaseService)

    init {
        runBlocking {
            repo.refreshRecipes()
        }

        recipes.value = repo.recipes.value ?: listOf<RecipeDataItem>()
        //TODO: when online get from firebase

    }

    fun getRecipesByName(s: String) {
        //TODO: when offline get from local db
        recipes.value = firebaseService.getRecipesByNameFirestore(s)
    }


}