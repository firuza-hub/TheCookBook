package com.example.thecookbook.data.access.remote.models

import com.google.firebase.firestore.DocumentId
import java.util.*

data class UserMealImage(
    @DocumentId
    val id:String?,
    val userId: String,
    val downloadUrl: String,
    val date: Date
){
    constructor():this("","","", Date())
}