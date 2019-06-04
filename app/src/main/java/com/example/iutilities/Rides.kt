package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_rides.*
import kotlinx.android.synthetic.main.activity_rides.Navigation_Button
import kotlinx.android.synthetic.main.activity_rides.button
import kotlinx.android.synthetic.main.activity_rides.button2
import kotlinx.android.synthetic.main.activity_rides.button3
import kotlinx.android.synthetic.main.activity_rides.Refresh__

class Rides : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rides)

        Navigation_Button.setOnClickListener {
            val intent = Intent(this, share_ride::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val intent = Intent (this, Marketplace::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent (this, Rides::class.java)
            startActivity(intent)
            finish()
        }

        button3.setOnClickListener {
            val intent = Intent (this, Tuitions::class.java)
            startActivity(intent)
        }

        Refresh__.setOnClickListener {
            val intent = Intent (this, FoodCourt::class.java)
            startActivity(intent)
        }

        fetchrides()
    }


    fun fetchrides(){

        val ride_adapter = GroupAdapter<ViewHolder>()
        val ride_ref = FirebaseDatabase.getInstance(). getReference("/Rides")
        ride_ref.addListenerForSingleValueEvent(object: ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val ride_temp = it.getValue(RideObj::class.java)
                    if ( ride_temp != null )
                    {
                        ride_adapter.add(rideHolder(ride_temp))
                        d("IUTils", "fetched ride item and added into adapter")
                        //Toast.makeText(this, "fetched ride item and added into adapter", Toast.LENGTH_SHORT).show()
                    }
                    recycler_ride.adapter = ride_adapter
                    d("IUTils", "Adapter set")
                    //Toast.makeText(this, "Adapter set", Toast.LENGTH_SHORT).show()

                    ride_adapter.setOnItemClickListener { ride, view->

                        val ride_temp2 = ride as rideHolder
                        val intent = Intent(view.context, RideDetails::class.java)
                        intent.putExtra("RIDE", ride_temp2.ride)
                        startActivity(intent)
                    }
                    }
                }
        })
    }
}
