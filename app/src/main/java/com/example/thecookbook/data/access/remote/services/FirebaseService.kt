package com.example.thecookbook.data.access.remote.services

import android.util.Log
import com.example.thecookbook.data.access.remote.models.Recipe
import com.example.thecookbook.data.access.remote.models.UserMealImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseService() {

    private val db = Firebase.firestore
    private var recipes = listOf<Recipe>()
    private var images = listOf<UserMealImage>()
    private val recipesCollection = db.collection("recipes")

    fun getRecipes(): List<Recipe> {
       runBlocking {   readAllRecipesData()}
        return recipes
    }

    suspend fun getUserMealImagesByRecipeId(recipeId: String): List<UserMealImage> {
        withContext(Dispatchers.IO) {
            images = recipesCollection.document(recipeId).collection("userMealImages").get().await()
                .toObjects(UserMealImage::class.java)
        }
        return images
    }

    fun addUserMealImageByRecipeId(recipeId: String, image: UserMealImage) {
        recipesCollection.document(recipeId)
            .collection("userMealImages")
            .add(image)
            .addOnFailureListener { Log.i("MYCAMERA", it.message.toString()) }
            .addOnSuccessListener { r ->
                Log.i(
                    "MYCAMERA",
                    "image added to the recipe with id : ${r.id} "
                )
            }
    }

    private suspend fun readAllRecipesData() {
        withContext(Dispatchers.IO) {

            recipesCollection.get()
                .addOnFailureListener { Log.i("RECIPES_LIST", it.message.toString()) }
                .addOnSuccessListener {
                    Log.i("RECIPES_LIST", "SUCCESS!")
                    recipes = it.toObjects(Recipe::class.java)
                    Log.i("RECIPES_LIST",recipes.toString())

                }.await()
        }
    }

    suspend fun getRecipesByNameFirestore(searchText: String): List<Recipe> {
        readAllRecipesData()
        return recipes.filter { r -> searchText.isEmpty() || searchText in r.name.lowercase() }
    }

}