package com.example.iutilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_buy_item.*
import kotlinx.android.synthetic.main.activity_ride_details.*

class RideDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_details)

        val ride_temp2 = intent.getParcelableExtra<RideObj>("RIDE")
        if (ride_temp2 == null) {
            Log.d("IUTils", "Error retrieving parcelable object")
            Toast.makeText(this, "ERROR RECEIVING PARCELABLE OBJECT", Toast.LENGTH_SHORT).show()
        } else {
            detailsfrom.setText("From: ${ride_temp2.from.toString()}")
            detailsto.setText("To:  ${ride_temp2.to.toString()}")
            detailsseat.setText("Available Seats: ${ride_temp2.available_Seats.toString()}")
            detailstime.setText("Time Range: ${ride_temp2.time.toString()}")
            detailsphone.setText("Phone Number:  ${ride_temp2.phone_Number.toString()}")
            detailsname.setText("Name: ${ride_temp2.name.toString()}")

        }
    }
}
