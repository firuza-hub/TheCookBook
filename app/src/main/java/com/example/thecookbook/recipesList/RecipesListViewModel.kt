package com.example.thecookbook.recipesList

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecookbook.data.access.RecipesRepository
import com.example.thecookbook.data.models.RecipeDTO
import com.example.thecookbook.data.models.RecipeDataItem
import com.example.thecookbook.data.models.Result
import kotlinx.coroutines.launch

class RecipesListViewModel(val app: Application,
                           private val repository: RecipesRepository): ViewModel() {
    private val recipes = MutableLiveData<List<RecipeDataItem>>()

    init {
        loadRecipes()
    }


    private fun loadRecipes() {
        viewModelScope.launch {
            //interacting with the dataSource has to be through a coroutine
            when (val result = repository.getRecipes()) {
                is Result.Success<*> -> {
                    val dataList = ArrayList<RecipeDataItem>()
                    dataList.addAll((result.data as List<RecipeDTO>).map { recipe ->
                        //map the reminder data from the DB to the be ready to be displayed on the UI
                        RecipeDataItem(
                            recipe.id,
                            recipe.title
                        )
                    })
                    recipes.value = dataList
                }
                is Result.Error -> Toast.makeText(app.applicationContext, result.message, Toast.LENGTH_SHORT).show()
                   // showSnackBar.value = result.message
            }

            //check if no data has to be shown
           // invalidateShowNoData()
        }
    }

}