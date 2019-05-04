package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_rides.*
import kotlinx.android.synthetic.main.activity_sell.*
import kotlinx.android.synthetic.main.activity_share_ride.*
import kotlin.concurrent.timer

class share_ride : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_ride)

        share_button.setOnClickListener {
            if(sharefrom.text == null || shareto.text == null || shareseat.text == null || sharetime.text == null)
            {
                Toast.makeText(this, "Please enter the required text field", Toast.LENGTH_SHORT).show()
            }

            else
            {
                val name = FirebaseAuth.getInstance(). currentUser?.displayName.toString()
                val ride = RideObj(sharefrom.text.toString(), shareto.text.toString(), shareseat.text.toString(), sharetime.text.toString())
                val rideref = FirebaseDatabase.getInstance().getReference("Rides")
                rideref.child("${sharefrom.text.toString()} ${shareto.text.toString()}").setValue(ride)
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Pushing failed.", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Pushed item successfully", Toast.LENGTH_SHORT).show()
                                        finish()
                                    }

                    }


            }

        Back_Button.setOnClickListener {
            val intent = Intent(this, Rides::class.java)
            startActivity(intent)
            finish()
        }
        }
    }


