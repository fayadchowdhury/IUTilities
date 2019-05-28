package com.example.iutilities

import java.sql.Time

class RideObj (val From: String, val To: String, val Available_Seats: String, val Time: String, val Name: String)
{
    constructor():this("", "", "", "", "")
}