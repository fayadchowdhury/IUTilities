package com.example.iutilities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_marketplace.*

class Marketplace : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)



        sell_button_sell.setOnClickListener {
            val intent = Intent(this, Sell::class.java)
            startActivity(intent)
        }
    }
}

