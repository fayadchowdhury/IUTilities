package com.example.iutilities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sell.*

class Sell : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

        sell_button_sell.setOnClickListener {
            if ( name_text.text == null || price_text.text == null || cat_text.text == null ) {
                d("IUTils", "Please enter the name, the price and the category and a brief description of the item you're selling")
                Toast.makeText(
                    this,
                    "Please enter at least the name, the price and the categor of the item you're selling",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else
            {
                val ref = FirebaseDatabase.getInstance().getReference("sell/${cat_text.text.toString()}")
                val item = Item(cat_text.text.toString(), name_text.text.toString(), price_text.text.toString(), desc_text.text.toString())
                ref.child("${name_text.text.toString()}").setValue(item)
                    .addOnFailureListener {
                        Toast.makeText(this, "Pushing failed. ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                    .addOnSuccessListener {
                        Toast.makeText(this, "Pushed item successfully", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}

class Item(val category: String, val name: String, val price: String, val description: String)
{
    constructor():this("", "", "", "")
}