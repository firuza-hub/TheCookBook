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
        readAllRecipesData()
        //addTestData()
        recipes.value = listOf(
            RecipeDataItem("1", "Breakfast recipe", "test",20),
            RecipeDataItem("2", "This is lunch","test",20)
        )
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

        db.collection("recipes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("FIRESTORE", "${document.id} => ${document.data}")

                }
                val recipesFromFire =  result.toObjects(RecipeDataItem::class.java)
                recipes.value = recipesFromFire
            }
            .addOnFailureListener { exception ->
                Log.w("FIRESTORE", "Error getting documents.", exception)
            }

    }


}