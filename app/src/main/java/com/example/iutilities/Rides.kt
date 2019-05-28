package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_rides.*

class Rides : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rides)

        Navigation_Button.setOnClickListener {
            val intent = Intent(this, share_ride::class.java)
            startActivity(intent)
        }
    }

}
