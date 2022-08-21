package com.example.thecookbook.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentId

class RecipeDataItem(
    @DocumentId
    val id: String,
    val name: String,
    val description: String,
    val imageUrl:String?,
    val cookTimeMinutes: Int,
    val servings: Int
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    )
    constructor() : this("","","",null,0,1)


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(imageUrl)
        parcel.writeInt(cookTimeMinutes)
        parcel.writeInt(servings)

    }

    companion object CREATOR : Parcelable.Creator<RecipeDataItem> {
        override fun createFromParcel(parcel: Parcel): RecipeDataItem {
            return RecipeDataItem(parcel)
        }

        override fun newArray(size: Int): Array<RecipeDataItem?> {
            return arrayOfNulls(size)
        }
    }
}