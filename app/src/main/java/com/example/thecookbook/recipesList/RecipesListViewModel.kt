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
        //addTestData()

    }


    private fun addTestData() {
        // Create a new user with a first and last name
        val recipe = hashMapOf(
            "name" to "Test meal",
            "description" to "description for test meal",
            "cookTimeMinutes" to 35
        )

        db.collection("recipes")
            .add(recipe)
            .addOnSuccessListener { documentReference ->
                Log.d("FIRESTORE", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FIRESTORE", "Error adding document", e)
            }
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


}