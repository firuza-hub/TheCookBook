package com.example.thecookbook.data.access.remote.models

import com.google.firebase.firestore.DocumentId

data class UserMealImage(
    @DocumentId
    val id:String?,
    val userId: String,
    val downloadUrl: String,
    val date: java.util.Date
)