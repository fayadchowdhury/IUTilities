package com.example.iutilities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FoodObj(val restname: String, val orderername: String, val description: String, val cost: String): Parcelable
{
    constructor():this("", "", "", "")
}