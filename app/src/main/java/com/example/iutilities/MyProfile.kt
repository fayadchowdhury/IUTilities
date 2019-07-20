package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_marketplace.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_rides.*
import kotlinx.android.synthetic.main.activity_tuitions.*
import kotlinx.android.synthetic.main.food_court.*

class MyProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)


        fetchmine(FirebaseAuth.getInstance().currentUser?.displayName.toString())
    }

    private fun fetchmine(username: String)
    {
        //Sells
        val sell_ref = FirebaseFirestore.getInstance().collection("sell")
        sell_ref.addSnapshotListener { value, err ->
            val adapter = GroupAdapter<ViewHolder>()
            if (err != null) {
                Log.d("IUTils", "Error receiving snapshot")
            }
            for (doc in value!!) {
                val item_tmp = doc.toObject(ItemObj::class.java)
                if (item_tmp != null && item_tmp.postername.toString() == username) {
                    adapter.add(itemholder(item_tmp))
                }
            }
            sell_recycler.adapter = adapter

            adapter.setOnItemClickListener { item, view ->
                val item_tmptmp = item as itemholder
                val intent = Intent(view.context, MyItem::class.java)
                intent.putExtra("ITEM", item_tmptmp.item)
                startActivity(intent)
            }
        }

        //Rides
        val ride_ref = FirebaseFirestore.getInstance().collection("rides")
        ride_ref.addSnapshotListener { value, err ->
            val adapter = GroupAdapter<ViewHolder>()
            if (err != null) {
                Log.d("IUTils", "Error receiving snapshot")
            }
            for (doc in value!!) {
                val ride_tmp = doc.toObject(RideObj::class.java)
                //Log.d("IUTils", "${food_tmp.restname} FS object created successfully")
                if (ride_tmp != null && ride_tmp.name.toString() == username) {
                    adapter.add(rideHolder(ride_tmp))
                }
            }
            rides_recycler.adapter = adapter

            adapter.setOnItemClickListener { ride, view ->
                val ride_tmptmp = ride as rideHolder
                val intent = Intent(view.context, MyRides::class.java)
                intent.putExtra("RIDE", ride_tmptmp.ride)
                startActivity(intent)
            }
        }

        //Tuitions
        val tut_ref = FirebaseFirestore.getInstance().collection("tuitions")
        tut_ref.addSnapshotListener { value, err ->
            val adapter = GroupAdapter<ViewHolder>()
            if (err != null) {
                Log.d("IUTils", "Error receiving snapshot")
            }
            for (doc in value!!) {
                val tuition_tmp = doc.toObject(TuitionObj::class.java)
                //Log.d("IUTils", "${food_tmp.restname} FS object created successfully")
                if (tuition_tmp != null && tuition_tmp.the_person_offering.toString() == username) {
                    adapter.add(TuitionsPlaced(tuition_tmp))
                }
            }
            tuition_recycler.adapter = adapter

            adapter.setOnItemClickListener { offer, view ->
                val offer_temp2 = offer as TuitionsPlaced
                val intent = Intent(view.context, MyTuitions::class.java)
                intent.putExtra("Offer", offer_temp2.offer)
                startActivity(intent)
            }
        }

        //Foods
        val food_ref = FirebaseFirestore.getInstance().collection("foods")
        food_ref.addSnapshotListener { value, err ->
            val adapter = GroupAdapter<ViewHolder>()
            if (err != null) {
                Log.d("IUTils", "Error receiving snapshot")
            }
            for (doc in value!!) {
                val food_tmp = doc.toObject(FoodObj::class.java)
                //Log.d("IUTils", "${food_tmp.restname} FS object created successfully")
                if (food_tmp != null && food_tmp.orderername.toString() == username) {
                    adapter.add(FoodAdapter(food_tmp))
                }
            }
            foods_recycler.adapter = adapter

            adapter.setOnItemClickListener { food, view ->
                val food_tmptmp = food as FoodAdapter
                val intent = Intent(view.context, MyFoods::class.java)
                intent.putExtra("FOOD", food_tmptmp.food)
                startActivity(intent)
            }
        }
    }
}
