package com.example.iutilities

import java.sql.Time

class RideObj (val from: String, val to: String, val available_seats: String, val time: String)
{
    constructor():this("", "", "", "")
}