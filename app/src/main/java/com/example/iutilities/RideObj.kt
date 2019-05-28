package com.example.iutilities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Time

@Parcelize
class RideObj (val from: String, val to: String, val available_Seats: String, val time: String, val name: String, val phone_Number: String): Parcelable
{
    constructor():this("", "", "", "", "", "")
}