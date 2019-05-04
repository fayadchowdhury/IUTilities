package com.example.iutilities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ItemObj(val category: String, val name: String, val price: String, val description: String, val photourl: String, val postername: String): Parcelable
{
    constructor():this("", "", "", "", "", "")
}