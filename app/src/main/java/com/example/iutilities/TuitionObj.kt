package com.example.iutilities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Time

@Parcelize
class TuitionObj (val  the_person_offering : String ,
                  val  student_name : String,
                  val  students_address : String,
                  val  students_class : String,
                  val  institution_name : String,
                  val  curriculum : String,
                  val  description : String): Parcelable
{
    constructor():this ("", "","","","","","")
}