package com.example.a03_android_app

import android.os.Parcel
import android.os.Parcelable

class AuthorEntity(
    var id: Int,
    var name: String,
    var age: Int,
    var literary_genre: String,
    var latitude: String,
    var longitude: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeString(literary_genre)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthorEntity> {
        override fun createFromParcel(parcel: Parcel): AuthorEntity {
            return AuthorEntity(parcel)
        }

        override fun newArray(size: Int): Array<AuthorEntity?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString():String {
        return "$id - $name"
    }
}