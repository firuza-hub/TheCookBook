package com.example.thecookbook.camera

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import com.example.thecookbook.base.BaseViewModel
import com.example.thecookbook.data.access.remote.services.FirebaseService
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream


class CameraViewModel(app: Application) : BaseViewModel(app) {
    private val firebaseService = FirebaseService()
    var storage = FirebaseStorage.getInstance()

    // Create a storage reference from our app
    val storageRef = storage.getReferenceFromUrl("gs://thecookbook-by-firuza.appspot.com");

    fun doSTUFF(bitmap: Bitmap) {
        val mountainsRef = storageRef.child("mountains.jpg");

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data: ByteArray = baos.toByteArray()

        val uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.i("MYCAMERA", it.message.toString())
        }
            .addOnSuccessListener { taskSnapshot -> // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Log.i("MYCAMERA", "Storage upload success")
                mountainsRef.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.path
                    Log.i("MYCAMERA", "download url: $it")

                   firebaseService.addPicToRecipe("gITyCbSzGbfnEQMCmhac", it)

                }

            }
    }


}