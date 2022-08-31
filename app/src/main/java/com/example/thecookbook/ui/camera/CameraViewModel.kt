package com.example.thecookbook.ui.camera

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import com.example.thecookbook.data.access.remote.models.UserMealImage
import com.example.thecookbook.ui.base.BaseViewModel
import com.example.thecookbook.data.access.remote.services.FirebaseService
import com.example.thecookbook.ui.authentication.FirebaseUserLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*


class CameraViewModel(app: Application) : BaseViewModel(app) {
    private val firebaseService = FirebaseService()
    private var storage = FirebaseStorage.getInstance()

    // Create a storage reference from our app
    private val storageRef = storage.getReferenceFromUrl("gs://thecookbook-by-firuza.appspot.com");

    fun savePic(bitmap: Bitmap, recipeId: String, imageName:String) {
        val mountainsRef = storageRef.child(imageName);

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data: ByteArray = baos.toByteArray()

        val uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.i("MYCAMERA", it.message.toString())
        }
            .addOnSuccessListener {
                Log.i("MYCAMERA", "Storage upload success")

                mountainsRef.downloadUrl.addOnSuccessListener {
                    Log.i("MYCAMERA", "download url: $it")

                    val currentUser =FirebaseAuth.getInstance().currentUser ?: throw Exception("not logged in")
                    firebaseService.addUserMealImageByRecipeId(
                        recipeId,
                        UserMealImage(
                            null,
                            currentUser.uid,
                            currentUser.displayName?:"",
                            it.toString(),
                            Date()
                        )
                    )

                }

            }
    }

    fun getPics(recipeId: String): List<UserMealImage> {
        return firebaseService.getUserMealImagesByRecipeId(recipeId)
    }

}