package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_marketplace.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_rides.*

class MyProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        fetchsells()
        fetchrides()
    }

    private fun fetchrides()
    {
        val username = FirebaseAuth.getInstance().currentUser?.displayName.toString()
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
                        if ( ride_temp.name.toString() == username )
                        {
                            d("IUTils", "Adding to ride recycler personal")
                            ride_adapter.add(rideHolder(ride_temp))
                        }
                    }
                    rides_recycler.adapter = ride_adapter
                    d("IUTils", "Set adapter for personal ride recycler")

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

    private fun fetchsells()
    {
        val username = FirebaseAuth.getInstance().currentUser?.displayName.toString()
        val adapter = GroupAdapter<ViewHolder>()
        val ref = FirebaseDatabase.getInstance().getReference("/sell")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val item_tmp = it.getValue(ItemObj::class.java)
                    if ( item_tmp != null )
                    {
                        if ( item_tmp.postername.toString() == username )
                        {
                            adapter.add(itemholder(item_tmp))
                        }
                    }
                }
                sell_recycler.adapter = adapter

                adapter.setOnItemClickListener { item, view ->
                    val item_tmptmp = item as itemholder
                    // Make personal
                    // val intent = Intent(view.context, BuyItem::class.java)
                    intent.putExtra("ITEM", item_tmptmp.item)
                    startActivity(intent)
                }
            }
        })
    }
}
