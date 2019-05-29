package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.food_court.*
import kotlinx.android.synthetic.main.food_court.B_marketPlace
import kotlinx.android.synthetic.main.food_court.B_ride
import kotlinx.android.synthetic.main.food_court.B_tutions
import kotlin.jvm.java

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

        Refresh__.setOnClickListener {
            val intent = Intent (this, FoodCourt::class.java)
            startActivity(intent)
        }

        order_button.setOnClickListener {
            val intent = Intent (this, OrderFood::class.java)
            startActivity(intent)
        }



        fetchfoods()
    }

    private fun fetchfoods()
    {
        val adapter = GroupAdapter<ViewHolder>()
        val ref = FirebaseDatabase.getInstance().getReference("/foods")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val food_tmp = it.getValue(FoodObj::class.java)
                    if ( food_tmp != null )
                    {
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
        })
    }
}
