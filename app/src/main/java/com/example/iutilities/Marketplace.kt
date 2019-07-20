package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_marketplace.*

class Marketplace : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        sell_button_sell.setOnClickListener {
            val intent = Intent(this, Sell::class.java)
            startActivity(intent)
        }

        B_marketPlace.setOnClickListener {
            val intent = Intent (this, Marketplace::class.java)
            startActivity(intent)
            finish()
        }

        B_ride.setOnClickListener {
            val intent = Intent (this, Rides::class.java)
            startActivity(intent)
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
            finish()
        }

        B_my_pro.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        }

        fetchitems()
    }

    private fun fetchitems()
    {

        val ref = FirebaseFirestore.getInstance().collection("sell")
        ref.addSnapshotListener { value, err ->
            val adapter = GroupAdapter<ViewHolder>()
            if (err != null) {
                Log.d("IUTils", "Error receiving snapshot")
            }
            for (doc in value!!) {
                val item_tmp = doc.toObject(ItemObj::class.java)
                if (item_tmp != null) {
                    adapter.add(itemholder(item_tmp))
                }
            }
            item_recyclerview.adapter = adapter

            adapter.setOnItemClickListener { item, view ->
                val item_tmptmp = item as itemholder
                val intent = Intent(view.context, BuyItem::class.java)
                intent.putExtra("ITEM", item_tmptmp.item)
                startActivity(intent)
            }
        }
    }
}