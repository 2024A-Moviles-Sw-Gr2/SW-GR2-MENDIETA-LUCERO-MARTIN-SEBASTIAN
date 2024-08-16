package com.example.proyectoiib_kronos

import android.os.Parcel
import android.os.Parcelable

class EventEntity(
    var id: Int,
    var title: String,
    var date: String,
    var hour: String,
    var categoryId: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(hour)
        parcel.writeInt(categoryId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventEntity> {
        override fun createFromParcel(parcel: Parcel): EventEntity {
            return EventEntity(parcel)
        }

        override fun newArray(size: Int): Array<EventEntity?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString():String {
        return "$hour    $title"
    }
}