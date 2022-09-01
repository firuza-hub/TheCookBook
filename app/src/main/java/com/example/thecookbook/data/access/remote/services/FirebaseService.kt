package com.example.thecookbook.data.access.remote.services

import android.util.Log
import com.example.thecookbook.data.access.remote.models.Recipe
import com.example.thecookbook.data.access.remote.models.UserMealImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
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


    suspend fun getRecipes(): List<Recipe> {
        return withContext(Dispatchers.IO) {
            return@withContext recipesCollection.get().await().toObjects(Recipe::class.java)
        }
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

    suspend fun getRecipesByNameFirestore(searchText: String): List<Recipe> {
        return getRecipes().filter { r -> searchText.isEmpty() || searchText in r.name.lowercase() }
    }

}