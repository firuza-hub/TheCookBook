package com.example.thecookbook.recipesList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thecookbook.data.models.RecipeDataItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecipesListViewModel : ViewModel() {
    val recipes = MutableLiveData<List<RecipeDataItem>>()
    private val db = Firebase.firestore

    init {
        //TODO: Move to data access class
        readAllRecipesData()

    }

    private fun readAllRecipesData() {
        //TODO: Add loader
        db.collection("recipes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("FIRESTORE", "${document.id} => ${document.data}")

                }
                val recipesFromFire =  result.toObjects(RecipeDataItem::class.java)
                recipes.value = recipesFromFire
                //TODO: Add error handling for empty collection, for parsing error
            }
            .addOnFailureListener { exception ->
                Log.w("FIRESTORE", "Error getting documents.", exception)
                //TODO: Add error toaster
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