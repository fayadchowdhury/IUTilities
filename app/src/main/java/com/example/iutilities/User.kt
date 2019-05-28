package com.example.iutilities

class User(val username: String, val email: String, val password: String, val contact: String)
{
    constructor():this("", "", "", "")
}