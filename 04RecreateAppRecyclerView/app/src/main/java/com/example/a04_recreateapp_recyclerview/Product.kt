package com.example.a04_recreateapp_recyclerview

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

class Product (
    var id:Int,
    @DrawableRes val coverImage: Int,
    var productName: String,
    var productPrice:String,
    var productOriginalPrice: String,
    var discount: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(coverImage)
        parcel.writeString(productName)
        parcel.writeString(productPrice)
        parcel.writeString(productOriginalPrice)
        parcel.writeString(discount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

}