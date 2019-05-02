package com.example.iutilities

class ItemObj(val category: String, val name: String, val price: String, val description: String, val photourl: String, val postername: String)
{
    constructor():this("", "", "", "", "", "")
}