package com.example.thecookbook.data.models

import android.os.Parcel
import android.os.Parcelable

data class RecipeDataItem(
    val id: String,
    val name: String,
    val description: String,
    val cookTimeInMinutes: Int
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    constructor() : this("","","",1)

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
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