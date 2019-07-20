package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.food_court.*
import kotlinx.android.synthetic.main.food_court.B_marketPlace
import kotlinx.android.synthetic.main.food_court.B_ride
import kotlinx.android.synthetic.main.food_court.B_tutions
import kotlin.jvm.java
import kotlin.math.log

class FoodCourt : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_court)

        B_marketPlace.setOnClickListener {
            val intent = Intent(this, Marketplace::class.java)
            startActivity(intent)
        }

        B_ride.setOnClickListener {
            val intent = Intent(this, Rides::class.java)
            startActivity(intent)
        }

        B_tutions.setOnClickListener {
            val intent = Intent(this, Tuitions::class.java)
            startActivity(intent)
        }

        B_foods.setOnClickListener {
            val intent = Intent (this, FoodCourt::class.java)
            startActivity(intent)
            finish()
        }

        order_button.setOnClickListener {
            val intent = Intent (this, OrderFood::class.java)
            startActivity(intent)
        }

        B_sign_out.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        }

        B_my_pro.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        }

        fetchfoods()
    }

    private fun fetchfoods() {
        val ref = FirebaseFirestore.getInstance().collection("foods")
        ref.addSnapshotListener { value, err ->
            val adapter = GroupAdapter<ViewHolder>()
            if (err != null) {
                Log.d("IUTils", "Error receiving snapshot")
            }
            for (doc in value!!) {
                val food_tmp = doc.toObject(FoodObj::class.java)
                //Log.d("IUTils", "${food_tmp.restname} FS object created successfully")
                if (food_tmp != null) {
                    adapter.add(FoodAdapter(food_tmp))
                }
            }
            recyclerView_food.adapter = adapter

            adapter.setOnItemClickListener { food, view ->
                    val food_tmptmp = food as FoodAdapter
                    val intent = Intent(view.context, BuyFood::class.java)
                    intent.putExtra("FOOD", food_tmptmp.food)
                    startActivity(intent)
            }
        }
    }
}
