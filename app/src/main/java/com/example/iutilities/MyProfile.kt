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
import kotlinx.android.synthetic.main.activity_tuitions.*
import kotlinx.android.synthetic.main.food_court.*

class MyProfile : AppCompatActivity() {

    //Allow user to view and delete his/her own items, foods, rides, tuitions as required
    //Work In Progress
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)


        fetchmine(FirebaseAuth.getInstance().currentUser?.displayName.toString())
    }

    private fun fetchmine(username: String)
    {
        //Sells
        val sell_adapter = GroupAdapter<ViewHolder>()
        val sell_ref = FirebaseDatabase.getInstance().getReference("/sell")
        sell_ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val item_tmp = it.getValue(ItemObj::class.java)
                    if ( item_tmp != null )
                    {
                        if ( item_tmp.postername.toString() == username )
                        {
                            sell_adapter.add(itemholder(item_tmp))
                        }
                    }
                }
                sell_recycler.adapter = sell_adapter

                sell_adapter.setOnItemClickListener { item, view ->
                    val item_tmptmp = item as itemholder
                    // Make personal
                    val intent = Intent(view.context, MyItem::class.java)
                    intent.putExtra("ITEM", item_tmptmp.item)
                    startActivity(intent)
                }
            }
        })

        //Rides
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
                            ride_adapter.add(rideHolder(ride_temp))
                        }
                    }
                    rides_recycler.adapter = ride_adapter

                    ride_adapter.setOnItemClickListener { ride, view->

                        val ride_temp2 = ride as rideHolder
                        val intent = Intent(view.context, MyRides::class.java)
                        intent.putExtra("RIDE", ride_temp2.ride)
                        startActivity(intent)
                    }
                }
            }
        })

        //Tuitions
        val tuition_adapter=GroupAdapter<ViewHolder>()
        val tuition_ref=FirebaseDatabase.getInstance(). getReference("/Tuitions")
        tuition_ref.addListenerForSingleValueEvent(object: ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val offer_temp = it.getValue(TuitionObj::class.java)
                    if ( offer_temp != null ) {
                        if (offer_temp.the_person_offering.toString() == username) {
                            tuition_adapter.add(TuitionsPlaced(offer_temp))
                        }
                    }
                    tuition_recycler.adapter = tuition_adapter

                    tuition_adapter.setOnItemClickListener { offer, view->
                        val offer_temp2 = offer as TuitionsPlaced
                        val intent = Intent(view.context, MyTuitions::class.java)
                        intent.putExtra("Offer", offer_temp2.offer)
                        startActivity(intent)
                    }
                }
            }
        })

        //Foods
        val food_adapter = GroupAdapter<ViewHolder>()
        val food_ref = FirebaseDatabase.getInstance().getReference("/foods")
        food_ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val food_tmp = it.getValue(FoodObj::class.java)
                    if ( food_tmp != null )
                    {
                        if ( food_tmp.orderername.toString() == username )
                            food_adapter.add(FoodAdapter(food_tmp))
                    }
                }
                foods_recycler.adapter = food_adapter

                food_adapter.setOnItemClickListener { food, view ->
                    val food_tmptmp = food as FoodAdapter
                    val intent = Intent(view.context, MyFoods::class.java)
                    intent.putExtra("FOOD", food_tmptmp.food)
                    startActivity(intent)
                }
            }
        })

    }
}
