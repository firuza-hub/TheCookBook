package com.example.thecookbook.data.models

data class RecipeDataItem(
    val id: String,
    val name: String,
    val description: String,
    val cookTimeInMinutes: Int
){
    constructor() : this("","","",1)
}