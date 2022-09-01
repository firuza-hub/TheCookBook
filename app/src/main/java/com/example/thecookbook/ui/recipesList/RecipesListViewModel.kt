package com.example.thecookbook.ui.recipesList

import android.app.Application
import androidx.lifecycle.*
import com.example.thecookbook.ui.base.BaseViewModel
import com.example.thecookbook.data.RecipeRepository
import com.example.thecookbook.data.access.local.db.getDatabase
import com.example.thecookbook.data.access.remote.services.FirebaseService
import com.example.thecookbook.data.access.remote.models.Recipe
import com.example.thecookbook.utils.SingleLiveEvent
import com.example.thecookbook.utils.checkForInternet

class RecipesListViewModel(application: Application) : BaseViewModel(application) {
    var recipes = MutableLiveData<List<Recipe>>()
    private val firebaseService = FirebaseService()
    private val db = getDatabase(application.applicationContext)
    private val repo = RecipeRepository(db, firebaseService)
    init {
        refreshList()
    }

    fun getRecipesByName(s: String) {
        recipes = if(checkForInternet(getApplication())){
            liveData { emit(firebaseService.getRecipesByNameFirestore(s))} as MutableLiveData<List<Recipe>>
        } else {

            repo.recipes as MutableLiveData<List<Recipe>>
        }

    }

    fun refreshList() {
        if(checkForInternet(getApplication())){
            recipes.value = firebaseService.getRecipes()
        } else {
            recipes.value = if(repo.recipes.value == null) emptyList<Recipe>() else repo.recipes.value
        }
    }


}