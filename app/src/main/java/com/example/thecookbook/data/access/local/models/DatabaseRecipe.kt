package com.example.thecookbook.data.access.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thecookbook.data.models.RecipeDataItem


@Entity(tableName = "DatabaseRecipes")
data class DatabaseRecipe(
    @PrimaryKey @ColumnInfo(name = "entry_id") val id: String,
    val name: String,
    val description: String,
    val imageUrl: String?,
    val cookTimeMinutes: Int,
    val servings: Int,
)

fun List<DatabaseRecipe>.asDomainModel(): List<RecipeDataItem> {
    return map {
        RecipeDataItem(
            id = it.id,
            name = it.name,
            description = it.description,
            imageUrl = it.imageUrl,
            cookTimeMinutes = it.cookTimeMinutes,
            servings = it.servings)
    }
}

fun List<RecipeDataItem>.asDatabaseModel(): List<DatabaseRecipe> {
    return map {
        DatabaseRecipe(
            id = it.id,
            name = it.name,
            description = it.description,
            imageUrl = it.imageUrl,
            cookTimeMinutes = it.cookTimeMinutes,
            servings = it.servings
        )
    }
}
