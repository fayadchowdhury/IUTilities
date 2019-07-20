package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_rides.*
import kotlinx.android.synthetic.main.activity_rides.B_foods
import kotlinx.android.synthetic.main.activity_rides.B_marketPlace
import kotlinx.android.synthetic.main.activity_rides.B_ride
import kotlinx.android.synthetic.main.activity_rides.B_sign_out
import kotlinx.android.synthetic.main.activity_rides.B_tutions

class Rides : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rides)

        ride_offer_button.setOnClickListener {
            val intent = Intent(this, share_ride::class.java)
            startActivity(intent)
        }

        B_marketPlace.setOnClickListener {
            val intent = Intent (this, Marketplace::class.java)
            startActivity(intent)
        }

        B_ride.setOnClickListener {
            val intent = Intent (this, Rides::class.java)
            startActivity(intent)
            finish()
        }

        B_tutions.setOnClickListener {
            val intent = Intent (this, Tuitions::class.java)
            startActivity(intent)
        }

        B_foods.setOnClickListener {
            val intent = Intent (this, FoodCourt::class.java)
            startActivity(intent)
        }

        B_sign_out.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }

        B_my_pro.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        }
        fetchrides()
    }


    private fun fetchrides() {
        val ref = FirebaseFirestore.getInstance().collection("rides")
        ref.addSnapshotListener { value, err ->
            val adapter = GroupAdapter<ViewHolder>()
            if (err != null) {
                Log.d("IUTils", "Error receiving snapshot")
            }
            for (doc in value!!) {
                val ride_tmp = doc.toObject(RideObj::class.java)
                //Log.d("IUTils", "${food_tmp.restname} FS object created successfully")
                if (ride_tmp != null) {
                    adapter.add(rideHolder(ride_tmp))
                }
            }
            recycler_ride.adapter = adapter

            adapter.setOnItemClickListener { ride, view ->
                val ride_tmptmp = ride as rideHolder
                val intent = Intent(view.context, RideDetails::class.java)
                intent.putExtra("RIDE", ride_tmptmp.ride)
                startActivity(intent)
            }
        }
    }
}
