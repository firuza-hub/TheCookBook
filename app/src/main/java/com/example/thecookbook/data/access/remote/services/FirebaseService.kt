package com.example.thecookbook.data.access.remote.services

import com.example.thecookbook.data.access.remote.models.Recipe
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FirebaseService {
    private val db = Firebase.firestore
    private var recipes = listOf<Recipe>()
    private val recipesCollection = db.collection("recipes")

    public fun getRecipes(): List<Recipe> {
        readAllRecipesData()
        return recipes
    }

    private fun readAllRecipesData() {
     runBlocking {
            recipes = recipesCollection.get().await().toObjects(Recipe::class.java)
        }
    }

    public fun getRecipesByNameFirestore(searchText: String): List<Recipe> {
        readAllRecipesData()
        return recipes.filter { r -> searchText.isNullOrEmpty() || searchText in r.name.lowercase() }


    }
}