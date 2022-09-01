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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RecipesListViewModel(application: Application) : BaseViewModel(application) {
    var recipes = MutableLiveData<List<Recipe>>()
    private val firebaseService = FirebaseService()
    private val db = getDatabase(application.applicationContext)
    private val repo = RecipeRepository(db, firebaseService)
    init {
        refreshList()
    }

    fun getRecipesByName(searchText: String) {
        if(checkForInternet(getApplication())){
            viewModelScope.launch {
                recipes.value =  firebaseService.getRecipesByNameFirestore(searchText)}
        } else {
            repo.recipes.observeForever(Observer { recipes.value = it.filter { r -> searchText.isEmpty() || searchText in r.name.lowercase() } })
        }

    }

    fun refreshList() {
        if(checkForInternet(getApplication())){
            viewModelScope.launch {
                recipes.value =  firebaseService.getRecipes()}
        } else {
            repo.recipes.observeForever(Observer { recipes.value = it })
        }
    }


}