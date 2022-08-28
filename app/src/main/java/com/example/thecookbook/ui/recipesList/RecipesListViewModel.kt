package com.example.thecookbook.ui.recipesList

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.thecookbook.ui.base.BaseViewModel
import com.example.thecookbook.data.RecipeRepository
import com.example.thecookbook.data.access.local.db.getDatabase
import com.example.thecookbook.data.access.remote.services.FirebaseService
import com.example.thecookbook.data.access.remote.models.Recipe
import com.example.thecookbook.utils.checkForInternet

class RecipesListViewModel(application: Application) : BaseViewModel(application) {
    var recipes = MutableLiveData<List<Recipe>>()
    private val firebaseService = FirebaseService()
    private val db = getDatabase(application.applicationContext)
    private val repo = RecipeRepository(db, firebaseService)

    init {

        if(checkForInternet(application)){
            recipes.value =  firebaseService.getRecipes()
        }
        else {
            recipes = repo.recipes as MutableLiveData<List<Recipe>>
        }
    }

    fun getRecipesByName(s: String) {
        //TODO: when offline get from local db
        recipes.value = firebaseService.getRecipesByNameFirestore(s)
    }


}