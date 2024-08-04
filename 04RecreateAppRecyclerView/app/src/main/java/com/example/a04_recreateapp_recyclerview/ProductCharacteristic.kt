package com.example.a04_recreateapp_recyclerview

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

class ProductCharacteristic(
    var id: Int,
    @DrawableRes var chararImage: Int,
    var characName: String,
    var characValue: String
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(chararImage)
        parcel.writeString(characName)
        parcel.writeString(characValue)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductCharacteristic> {
        override fun createFromParcel(parcel: Parcel): ProductCharacteristic {
            return ProductCharacteristic(parcel)
        }

        override fun newArray(size: Int): Array<ProductCharacteristic?> {
            return arrayOfNulls(size)
        }
    }
}