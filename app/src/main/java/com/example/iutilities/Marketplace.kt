package com.example.iutilities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_marketplace.*
import kotlinx.android.synthetic.main.activity_rides.*

class Marketplace : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        buy_button.setOnClickListener {
            val intent = Intent(this, Buy::class.java)
            startActivity(intent)
        }

        sell_button.setOnClickListener {
            val intent = Intent(this, Sell::class.java)
            startActivity(intent)
        }
    }
}

