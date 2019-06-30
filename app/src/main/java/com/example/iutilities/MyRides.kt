package com.example.iutilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_my_rides.*

class MyRides : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_rides)

        var postername_tmp: String = ""

        val ride_temp2 = intent.getParcelableExtra<RideObj>("RIDE")
        if (ride_temp2 == null)
        {
            Log.d("IUTils", "Error retrieving parcelable object")
        }
        else
        {
            detailsfrom.setText("From ${ride_temp2.from.toString()}")
            detailsto.setText("To ${ride_temp2.to.toString()}")
            detailsseat.setText("${ride_temp2.available_Seats.toString()} seat(s)")
            detailstime.setText("${ride_temp2.time.toString()}")
            detailsname.setText("Courtesy of ${ride_temp2.name.toString()}")
            postername_tmp = ride_temp2.name.toString()

        }

        delete_button.setOnClickListener {
            val deleteref = FirebaseDatabase.getInstance().getReference("Rides").child("${ride_temp2.from.toString()} ${ride_temp2.to.toString()}")
            deleteref.removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                }
            finish()
        }
    }
}
