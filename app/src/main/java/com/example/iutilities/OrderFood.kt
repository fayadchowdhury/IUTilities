package com.example.iutilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_share_ride.*
import kotlinx.android.synthetic.main.order_food.*

class OrderFood : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_food)

        sell_button_sell.setOnClickListener {
            if(name_text.text == null || desc_text.text == null || price_text.text == null) {
                Toast.makeText(this, "Please enter the required text field", Toast.LENGTH_SHORT).show()
            } else {
                val name = FirebaseAuth.getInstance().currentUser?.displayName.toString()
                d("IUTils", "Username = $name")
                val food = FoodObj(name_text.text.toString(), name, desc_text.text.toString(), price_text.text.toString())
                val foodref = FirebaseDatabase.getInstance().getReference("foods")
                foodref.child("${name_text.text.toString()} $name").setValue(food)
                    .addOnFailureListener {
                        Toast.makeText(this, "Pushing failed.", Toast.LENGTH_SHORT).show()
                    }
                    .addOnSuccessListener {
                        Toast.makeText(this, "Pushed item successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }

            }


        }
    }

}
