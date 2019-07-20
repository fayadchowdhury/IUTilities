package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_tuitions.*
import kotlinx.android.synthetic.main.activity_tuitions.B_foods
import kotlinx.android.synthetic.main.activity_tuitions.B_marketPlace
import kotlinx.android.synthetic.main.activity_tuitions.B_ride
import kotlinx.android.synthetic.main.activity_tuitions.B_sign_out
import kotlinx.android.synthetic.main.activity_tuitions.B_tutions

class Tuitions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tuitions)

        tuition_offer_button.setOnClickListener {
            val intent = Intent(this, Tuition_Offer::class.java)
            startActivity(intent)
        }

        B_marketPlace.setOnClickListener {
            val intent = Intent (this, Marketplace::class.java)
            startActivity(intent)
        }

        B_ride.setOnClickListener {
            val intent = Intent (this, Rides::class.java)
            startActivity(intent)
        }

        B_tutions.setOnClickListener {
            val intent = Intent (this, Tuitions::class.java)
            startActivity(intent)
            finish()
        }

        B_foods.setOnClickListener {
            val intent = Intent (this, FoodCourt::class.java)
            startActivity(intent)
        }

        B_sign_out.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }

        B_my_pro.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        }

        fetchtuitions()
    }


//    private fun fetchtuitions(){
//        val tuition_adapter=GroupAdapter<ViewHolder>()
//        val tuition_ref=FirebaseDatabase.getInstance(). getReference("/Tuitions")
//        tuition_ref.addListenerForSingleValueEvent(object: ValueEventListener
//        {
//            override fun onCancelled(p0: DatabaseError) {
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                p0.children.forEach {
//                    val offer_temp = it.getValue(TuitionObj::class.java)
//                    if (offer_temp != null) {
//                        tuition_adapter.add(TuitionsPlaced(offer_temp))
//                        d("IUTils", "fetched offer item and added into adapter")
//                        //Toast.makeText(this, "fetched ride item and added into adapter", Toast.LENGTH_SHORT).show()
//                    }
//                    recycler_tuition.adapter = tuition_adapter
//                    d("IUTils", "Adapter set")
//                    //Toast.makeText(this, "Adapter set", Toast.LENGTH_SHORT).show()
//
//                    tuition_adapter.setOnItemClickListener { offer, view ->
//                        val offer_temp2 = offer as TuitionsPlaced
//                        val intent = Intent(view.context, TuitionsDetailed::class.java)
//                        intent.putExtra("Offer", offer_temp2.offer)
//                        startActivity(intent)
//                    }
//
//                }
//            }
//        })
//    }

    private fun fetchtuitions() {
        val ref = FirebaseFirestore.getInstance().collection("tuitions")
        ref.addSnapshotListener { value, err ->
            val adapter = GroupAdapter<ViewHolder>()
            if (err != null) {
                Log.d("IUTils", "Error receiving snapshot")
            }
            for (doc in value!!) {
                val tuition_tmp = doc.toObject(TuitionObj::class.java)
                //Log.d("IUTils", "${food_tmp.restname} FS object created successfully")
                if (tuition_tmp != null) {
                    adapter.add(TuitionsPlaced(tuition_tmp))
                }
            }
            recycler_tuition.adapter = adapter

            adapter.setOnItemClickListener { offer, view ->
                val offer_temp2 = offer as TuitionsPlaced
                val intent = Intent(view.context, TuitionsDetailed::class.java)
                intent.putExtra("Offer", offer_temp2.offer)
                startActivity(intent)
            }
        }
    }
}

