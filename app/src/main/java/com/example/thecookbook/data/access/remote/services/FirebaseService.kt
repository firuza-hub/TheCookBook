package com.example.thecookbook.data.access.remote.services

import com.example.thecookbook.data.models.RecipeDataItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FirebaseService {
    private val db = Firebase.firestore
    private var recipes = listOf<RecipeDataItem>()
    private val recipesCollection = db.collection("recipes")

    public fun getRecipes(): List<RecipeDataItem> {
        readAllRecipesData()
        return recipes
    }

    private fun readAllRecipesData() {
     runBlocking {
            recipes = recipesCollection.get().await().toObjects(RecipeDataItem::class.java)
        }
    }

    public fun getRecipesByNameFirestore(searchText: String): List<RecipeDataItem> {
        readAllRecipesData()
        return recipes.filter { r -> searchText.isNullOrEmpty() || searchText in r.name.lowercase() }


    }
}