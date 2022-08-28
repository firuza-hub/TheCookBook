package com.example.thecookbook.data.access.remote.services

import android.net.Uri
import android.util.Log
import com.example.thecookbook.data.access.remote.models.Recipe
import com.example.thecookbook.data.access.remote.models.UserMealImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FirebaseService {
    private val db = Firebase.firestore
    private var recipes = listOf<Recipe>()
    private val recipesCollection = db.collection("recipes")

    fun getRecipes(): List<Recipe> {
        readAllRecipesData()
        return recipes
    }

    fun getUserMealImagesByRecipeId(recipeId: String): List<UserMealImage> {
        return runBlocking {
            recipesCollection.document(recipeId).collection("userMealImages").get().await()
                .toObjects(UserMealImage::class.java)
        }
    }

    fun addUserMealImageByRecipeId(recipeId: String,image: UserMealImage ) {
        recipesCollection.document(recipeId)
            .collection("userMealImages")
            .add(image)
            .addOnFailureListener { Log.i("MYCAMERA", it.message.toString()) }
            .addOnSuccessListener {r -> Log.i("MYCAMERA", "image added to the recipe with id : ${r.id} ") }
    }

    private fun readAllRecipesData() {
        runBlocking {
            recipes = recipesCollection.get().await().toObjects(Recipe::class.java)
        }
    }

    fun getRecipesByNameFirestore(searchText: String): List<Recipe> {
        readAllRecipesData()
        return recipes.filter { r -> searchText.isEmpty() || searchText in r.name.lowercase() }


    }

}