package com.example.thecookbook.worker

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.thecookbook.data.RecipeRepository
import com.example.thecookbook.data.access.local.db.getDatabase
import com.example.thecookbook.data.access.remote.services.FirebaseService
import com.example.thecookbook.ui.base.BaseViewModel
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(
    appContext,
    params
) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val firebaseService = FirebaseService()

        val asteroidRepository = RecipeRepository(database, firebaseService)

        return try {
            asteroidRepository.refreshRecipes()
            Result.success()
        }catch (exception:HttpException){
            Result.failure()
        }
    }
}