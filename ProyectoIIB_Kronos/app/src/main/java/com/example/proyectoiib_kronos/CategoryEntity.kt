package com.example.proyectoiib_kronos

import android.os.Parcel
import android.os.Parcelable

class CategoryEntity(
    var id: Int,
    var name: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
    ) {
    }

    override fun toString():String {
        return "$id - $name"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryEntity> {
        override fun createFromParcel(parcel: Parcel): CategoryEntity {
            return CategoryEntity(parcel)
        }

        override fun newArray(size: Int): Array<CategoryEntity?> {
            return arrayOfNulls(size)
        }
    }
}