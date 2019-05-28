package com.example.iutilities

import android.util.Log.d
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.cardview_rides.view.*

class rideHolder(val ride: RideObj): Item<ViewHolder>()
{
    override fun bind(viewHolder: ViewHolder, position: Int) {
        d("IUTils", "${ride?.from.toString()} ${ride?.to.toString()}")
        viewHolder.itemView.places_name.setText("From: ${ride?.from.toString()}\r\nTo: ${ride?.to.toString()}")
        viewHolder.itemView.nseats.setText("Available Seats: ${ride?.available_Seats.toString()}")
    }

    override fun getLayout(): Int {
        return R.layout.cardview_rides
    }
}