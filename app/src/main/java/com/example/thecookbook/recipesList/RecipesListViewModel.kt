package com.example.thecookbook.recipesList

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.thecookbook.base.BaseViewModel
import com.example.thecookbook.data.models.RecipeDataItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecipesListViewModel(application: Application) : BaseViewModel(application) {
    val recipes = MutableLiveData<List<RecipeDataItem>>()
    private val db = Firebase.firestore

    init {
        //TODO: Move to data access class
        readAllRecipesData()

    }

    private fun readAllRecipesData() {
        showLoading.value = true
        db.collection("recipes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("FIRESTORE", "${document.id} => ${document.data}")

                }
                val recipesFromFire =  result.toObjects(RecipeDataItem::class.java)
                recipes.value = recipesFromFire
                //TODO: Add error handling for empty collection, for parsing error
                showLoading.value = false
            }
            .addOnFailureListener { exception ->
                Log.w("FIRESTORE", "Error getting documents.", exception)
                //TODO: Add error toaster
                showLoading.value = false
            }

    }

    private fun getRecipesByNameFirestore(searchText:String){
        db.collection("recipes")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    Log.d("FIRESTORE", "${document.id} => ${document.data}")

                }
                val recipesFromFire =  result.toObjects(RecipeDataItem::class.java).filter { r -> searchText.isNullOrEmpty() || searchText in r.name.lowercase() }
                recipes.value = recipesFromFire
                //TODO: Add error handling for empty collection, for parsing error
            }
            .addOnFailureListener { exception ->
                Log.w("FIRESTORE", "Error getting documents.", exception)
                //TODO: Add error toaster
            }

    }

    fun getRecipesByName(s: String) {
        getRecipesByNameFirestore(s)
    }


}