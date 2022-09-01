package com.example.thecookbook.ui.camera

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.thecookbook.data.access.remote.models.UserMealImage
import com.example.thecookbook.data.access.remote.services.FirebaseService
import com.example.thecookbook.ui.base.BaseViewModel
import com.example.thecookbook.utils.checkForInternet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*


class CameraViewModel(application: Application,private  val recipeId: String) : BaseViewModel(application) {
    private val firebaseService = FirebaseService()
    private var storage = FirebaseStorage.getInstance()
    var pics = MutableLiveData<List<UserMealImage>>()


    init {

        if(checkForInternet(application)) {
            pics =
                liveData { emit(firebaseService.getUserMealImagesByRecipeId(recipeId)) } as MutableLiveData<List<UserMealImage>>
        }
        else{
            showNoConnection.value = true
        }
    }

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

                    val currentUser = FirebaseAuth.getInstance().currentUser ?: throw Exception("not logged in")
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
                    Toast.makeText(
                        getApplication(),
                        "File has been saved successfully!",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
    }


    class Factory(private val application: Application, private val recipeId: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CameraViewModel(application, recipeId) as T
        }
    }
}